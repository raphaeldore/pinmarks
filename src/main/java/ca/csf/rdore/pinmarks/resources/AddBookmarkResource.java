package ca.csf.rdore.pinmarks.resources;

import io.dropwizard.views.View;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.joda.time.DateTime;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.core.Tag;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.views.AddBookmarkView;
import ca.csf.rdore.pinmarks.views.PublicFreemarkerView;

@Path("/addBookmark")
public class AddBookmarkResource {

  BookmarkDAO bookmarkDao;
  TagDAO tagDao;

  // private final Hashids hashid = new Hashids("https://www.youtube.com/watch?v=dQw4w9WgXcQ", 10);

  public AddBookmarkResource(BookmarkDAO bookmarkDao, TagDAO tagDao) {
    this.bookmarkDao = bookmarkDao;
    this.tagDao = tagDao;
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public View addBookmark() {
    return new AddBookmarkView();
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response addBookmark(@FormParam("title") String title, @FormParam("url") String url,
      @FormParam("description") String description, @FormParam("tags") String tags) {
    if (title == null || title.isEmpty() || url == null || url.isEmpty()) {
      return Response.status(Status.BAD_REQUEST).entity(new PublicFreemarkerView("errors/400.ftl"))
          .build();
      // Response.status(400).entity(new PublicFreemarkerView("errors/400.ftl"));
    }

    UrlValidator urlValidator = new UrlValidator();

    if (!urlValidator.isValid(url)) {
      throw new WebApplicationException(Status.BAD_REQUEST);
    }

    DateTime dateTime = new DateTime();
    Bookmark bookmark =
        new Bookmark(title, url, description, new Timestamp(dateTime.getMillis()),
            new ArrayList<String>(), RandomStringUtils.random(15, true, true));
    int newBookmarkID = bookmarkDao.create(bookmark);

    if (tags != null && !tags.isEmpty()) {
      List<String> stringTagsList = Arrays.asList(tags.toLowerCase().split("\\s*(=>|,|\\s)\\s*"));
      List<Integer> tag_ids = new ArrayList<Integer>();

      for (String tag_name : stringTagsList) {
        tag_ids.add(bookmarkDao.createNewTag(new Tag(tag_name)));
      }
      bookmarkDao.batchInsertIDsIntoJunctionTable(newBookmarkID, tag_ids);
    }
    
    //return Response.status(Status.CREATED).entity(new PublicFreemarkerView("addBookmark.ftl")).build();
    return Response.status(Status.CREATED).build();
    
  }

  private List<Tag> parseTags(String tags, int bookmarkID) {
    List<String> stringTagsList = Arrays.asList(tags.split("\\s*(=>|,|\\s)\\s*")); // Separates by
                                                                                   // whitespace, by
                                                                                   // whitespace or
                                                                                   // comma (,) or
                                                                                   // arrow (=>), by
                                                                                   // zero or more
                                                                                   // whitespace.
    List<Tag> tagsList = new ArrayList<Tag>();

    // TODO
    /*
     * for (String stringTag : stringTagsList) { tagsList.add(new Tag(stringTag, bookmarkID)); }
     */

    return tagsList;
  }

}
