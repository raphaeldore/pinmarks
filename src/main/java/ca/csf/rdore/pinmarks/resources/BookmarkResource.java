package ca.csf.rdore.pinmarks.resources;

import io.dropwizard.views.View;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.exceptions.BadURLException;
import ca.csf.rdore.pinmarks.util.MiscUtils;
import ca.csf.rdore.pinmarks.views.AddBookmarkView;
import ca.csf.rdore.pinmarks.views.EditBookmarkView;
import ca.csf.rdore.pinmarks.views.PublicFreemarkerView;

@Path("/bookmark")
public class BookmarkResource {

  BookmarkDAO bookmarkDao;
  TagDAO tagDao;

  public BookmarkResource(BookmarkDAO bookmarkDao, TagDAO tagDao) {
    this.bookmarkDao = bookmarkDao;
    this.tagDao = tagDao;
  }

  @Path("add")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public View addBookmark() {
    return new AddBookmarkView();
  }

  @Path("add")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response addBookmark(@FormParam("title") String title, @FormParam("url") String url,
      @FormParam("description") String description, @FormParam("tags") String tags) {
    if (title == null || title.isEmpty() || url == null || url.isEmpty()) {
      return Response.status(Status.BAD_REQUEST).entity(new PublicFreemarkerView("errors/400.ftl"))
          .build();
    }

    String[] schemes = {"http", "https"};
    UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_2_SLASHES);

    if (!urlValidator.isValid(url)) {
      // throw new WebApplicationException(Status.BAD_REQUEST);
      return Response.status(new BadURLException(Status.BAD_REQUEST)).build();
    }

    PolicyFactory descriptionPolicy =
        new HtmlPolicyBuilder().allowElements("a").allowElements("b").allowElements("i")
            .allowUrlProtocols("https").allowAttributes("href").onElements("a")
            .requireRelNofollowOnLinks().toFactory();


    DateTime dateTime = new DateTime();
    Bookmark bookmark =
        new Bookmark(MiscUtils.inputToPureText(title), url, descriptionPolicy.sanitize(description), new Timestamp(dateTime.getMillis()),
            new ArrayList<String>(), MiscUtils.GenerateRandomSlug());
    int newBookmarkID = bookmarkDao.create(bookmark);

    if (tags != null && !tags.isEmpty()) {

      tags = MiscUtils.inputToPureText(tags);
      // Separates by whitespace, by whitespace or comma (,) or arrow (=>), by zero or more
      // whitespace:
      List<String> stringTagsList = Arrays.asList(tags.toLowerCase().split("\\s*(=>|,|\\s)\\s*"));
      List<Integer> tag_ids = new ArrayList<Integer>();

      for (String tag_name : stringTagsList) {
        tag_ids.add(bookmarkDao.createNewTag(new Tag(tag_name)));
      }
      bookmarkDao.batchInsertIDsIntoJunctionTable(newBookmarkID, tag_ids);
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
      return Response.status(Status.BAD_REQUEST).entity(new PublicFreemarkerView("errors/400.ftl"))
          .build();
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
    System.out.println("UPDATED BOOKMARK ID: " + updatedBookmarkId);
    
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
      List<String> newTags = Arrays.asList(tags.toLowerCase().split("\\s*(=>|,|\\s)\\s*"));
      List<String> oldTags = bookmark.getTags();

      List<Integer> tag_ids = new ArrayList<Integer>();

      if (oldTags == null || oldTags.isEmpty()) {
        for (String tag_name : newTags) {
          tag_ids.add(bookmarkDao.createNewTag(new Tag(tag_name)));
        }
      } else {
        bookmarkDao.deleteBookmarkTags(updatedBookmarkId);

        for (String tag_name : newTags) {
          tag_ids.add(bookmarkDao.createNewTag(new Tag(tag_name)));
        }
      }

      bookmarkDao.batchInsertIDsIntoJunctionTable(updatedBookmarkId, tag_ids);

    } else if ((tags == null || tags.isEmpty()) && !bookmark.getTags().isEmpty()) {
      bookmarkDao.deleteBookmarkTags(updatedBookmarkId);
    }

    return Response.status(Status.OK).build();

  }
  
  @Path("delete")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @DELETE
  public Response deleteBookmark(@FormParam("bookmarkSlug") String bookmarkSlug) {

    Response resp;

    if (bookmarkSlug == null) {
      return Response.status(Status.BAD_REQUEST).build();
    }

    Bookmark bookmark = bookmarkDao.getBookmarkBySlug(bookmarkSlug);

    if (bookmark == null) {
      resp = Response.status(Status.NO_CONTENT).build();
    } else {
      resp = Response.status(Status.ACCEPTED).build();
      try {
        bookmarkDao.deleteBookmarkBySlug(bookmarkSlug);
        resp = Response.status(Status.OK).build();
      } catch (Exception e) {
        throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
      }
    }

    return resp;
  }


  @Path("delete")
  @GET
  public WebApplicationException forbidGetRequestsToDeleteBookmarkPage() { // return new
    throw new WebApplicationException(Status.FORBIDDEN);
  }
  

}
