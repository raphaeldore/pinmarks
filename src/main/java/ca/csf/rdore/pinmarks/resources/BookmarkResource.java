package ca.csf.rdore.pinmarks.resources;

import io.dropwizard.views.View;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.validator.routines.UrlValidator;
import org.joda.time.DateTime;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.core.Tag;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.exceptions.BadURLException;
import ca.csf.rdore.pinmarks.util.MiscUtils;
import ca.csf.rdore.pinmarks.views.AddBookmarkView;
import ca.csf.rdore.pinmarks.views.EditBookmarkView;

@Path("/bookmark")
public class BookmarkResource {

  BookmarkDAO bookmarkDao;

  public BookmarkResource(BookmarkDAO bookmarkDao) {
    this.bookmarkDao = bookmarkDao;
  }

  // Returns the html view
  @Path("add")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public View addBookmark() {
    return new AddBookmarkView();
  }

  // Client posts a form to /bookmark/add
  @Path("add")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // Only accepts APPLICATION_FORM_URLENCODED post requests 
  public Response addBookmark(@FormParam("title") String title, @FormParam("url") String url,
      @FormParam("description") String description, @FormParam("tags") String tags) {
    if (title == null || title.isEmpty() || url == null || url.isEmpty()) {
      return Response.status(Status.BAD_REQUEST).build();
    }

    String[] schemes = {"http", "https"};
    UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_2_SLASHES);

    if (!urlValidator.isValid(url)) {
      return Response.status(new BadURLException(Status.BAD_REQUEST)).build();
    }

    // We want to allow some basic html in the description.
    PolicyFactory descriptionPolicy =
        new HtmlPolicyBuilder().allowElements("a").allowElements("b").allowElements("i")
            .allowUrlProtocols("https").allowAttributes("href").onElements("a")
            .requireRelNofollowOnLinks().toFactory();


    DateTime dateTime = new DateTime();
    Bookmark bookmark =
        new Bookmark(MiscUtils.inputToPureText(title), url,
            descriptionPolicy.sanitize(description), new Timestamp(dateTime.getMillis()),
            new ArrayList<String>(), MiscUtils.GenerateRandomSlug());
    int newBookmarkID = bookmarkDao.create(bookmark);

    if (tags != null && !tags.isEmpty()) {

      /* @formatter:off */
      tags = MiscUtils.inputToPureText(tags);
      
      // We don't want any duplicated tags
      Set<String> stringTagsList =
          new HashSet<String>(Arrays.asList(tags.toLowerCase().split("\\s*(=>|,|\\s)\\s*"))); // Regex: Separates by whitespace, OR by whitespace or comma (,) OR arrow (=>), OR by zero or more whitespace:
      List<Integer> tagIds = new ArrayList<Integer>();
      /* @formatter:on */
      
      for (String tag_name : stringTagsList) {
        tagIds.add(bookmarkDao.createNewTag(new Tag(tag_name)));
      }
      
      bookmarkDao.batchInsertIDsIntoJunctionTable(newBookmarkID, tagIds);
    }

    return Response.status(Status.CREATED).build();

  }

  @Path("/{bookmarkSlug}/edit")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public View editBookmark(@PathParam("bookmarkSlug") String bookmarkSlug) {

    Bookmark bookmark = bookmarkDao.getBookmarkBySlug(bookmarkSlug);

    if (bookmark == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }

    return new EditBookmarkView(bookmark);
  }

  @Path("/{bookmarkSlug}/edit")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response editBookmark(@PathParam("bookmarkSlug") String bookmarkSlug,
      @FormParam("title") String title, @FormParam("url") String url,
      @FormParam("description") String description, @FormParam("tags") String tags) {

    if (title == null || title.isEmpty() || url == null || url.isEmpty()) {
      return Response.status(Status.BAD_REQUEST).build();
    }

    String[] schemes = {"http", "https"};
    UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_2_SLASHES);

    if (!urlValidator.isValid(url)) {
      return Response.status(new BadURLException(Status.BAD_REQUEST)).build();
    }

    Bookmark bookmark = bookmarkDao.getBookmarkBySlug(bookmarkSlug);

    if (bookmark == null) {
      return Response.status(Status.NOT_FOUND).build();
    }

    int updatedBookmarkId = bookmarkDao.getBookmarkIDfromSlug(bookmarkSlug);

    PolicyFactory descriptionPolicy =
        new HtmlPolicyBuilder().allowElements("a").allowElements("b").allowElements("i")
            .allowUrlProtocols("https").allowAttributes("href").onElements("a")
            .requireRelNofollowOnLinks().toFactory();


    bookmark.setTitle(MiscUtils.inputToPureText(title));
    bookmark.setUrl(url);

    if (description != null && !description.trim().isEmpty()) {
      bookmark.setDescription(descriptionPolicy.sanitize(description));
    }


    bookmarkDao.updateBookmark(bookmark);

    if (tags != null && !tags.isEmpty()) {
      Set<String> newTags =
          new HashSet<String>(Arrays.asList(tags.toLowerCase().split("\\s*(=>|,|\\s)\\s*")));
      List<String> oldTags = bookmark.getTags();

      List<Integer> tagIds = new ArrayList<Integer>();

      if (oldTags == null || oldTags.isEmpty()) {
        for (String tag_name : newTags) {
          tagIds.add(bookmarkDao.createNewTag(new Tag(tag_name)));
        }
      } else {
        bookmarkDao.deleteBookmarkTags(updatedBookmarkId);

        for (String tag_name : newTags) {
          tagIds.add(bookmarkDao.createNewTag(new Tag(tag_name)));
        }
      }

      bookmarkDao.batchInsertIDsIntoJunctionTable(updatedBookmarkId, tagIds);

    } else if (tags == null && !bookmark.getTags().isEmpty()) {
      bookmarkDao.deleteBookmarkTags(updatedBookmarkId);
    }

    return Response.status(Status.OK).build();

  }

  @Path("delete")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @DELETE
  public Response deleteBookmark(@FormParam("bookmarkSlug") String bookmarkSlug) {

    Response resp;

    if (bookmarkSlug == null || bookmarkSlug.isEmpty()) {
      return Response.status(Status.BAD_REQUEST).build();
    }

    Bookmark bookmark = bookmarkDao.getBookmarkBySlug(bookmarkSlug);

    if (bookmark == null) {
      resp = Response.status(Status.NOT_FOUND).build();
    } else {
      resp = Response.status(Status.ACCEPTED).build();
      try {
        bookmarkDao.deleteBookmarkBySlug(bookmarkSlug);
        resp = Response.status(Status.OK).build();
      } catch (Exception e) {
        throw new WebApplicationException(Status.GONE);
      }
    }

    return resp;
  }


  @Path("delete")
  @GET
  public WebApplicationException forbidGetRequestsToDeleteBookmarkPage() {
    throw new WebApplicationException(Status.FORBIDDEN);
  }


}
