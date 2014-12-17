package ca.csf.rdore.pinmarks.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.views.IndexView;

import com.codahale.metrics.annotation.Timed;

@Path("/")
public class IndexResource {

  BookmarkDAO bookmarkDao;
  TagDAO tagDao;

  public IndexResource(BookmarkDAO bookmarkDao, TagDAO tagDao) {
    this.bookmarkDao = bookmarkDao;
    this.tagDao = tagDao;
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  @Timed
  public IndexView index(@QueryParam("search") String searchPattern,
      @DefaultValue("title") @QueryParam("searchBy") String searchBy) {

    // User is searching, only display the search results
    if (searchPattern != null
        && (searchBy.contentEquals("title") || searchBy.contentEquals("description"))) {
      List<Bookmark> listOfBookmarks = bookmarkDao.getAllBookmarksEvolved();
      List<Bookmark> listOfFilteredBookmarks = new ArrayList<Bookmark>();
      if (searchBy.contentEquals("title")) {
        for (Bookmark bookmark : listOfBookmarks) {
          if (bookmark.getTitle().toLowerCase().contains(searchPattern.toLowerCase())) {
            listOfFilteredBookmarks.add(bookmark);
          }
        }
      } else {
        for (Bookmark bookmark : listOfBookmarks) {
          if (bookmark.getDescription().toLowerCase().contains(searchPattern.toLowerCase())) {
            listOfFilteredBookmarks.add(bookmark);
          }
        }

        return new IndexView(listOfFilteredBookmarks);
      }

    } else if (searchBy.contentEquals("tag")) {

      List<Bookmark> listOfBookmarks = bookmarkDao.getAllBookmarksEvolved();
      List<Bookmark> listOfFilteredBookmarks = new ArrayList<Bookmark>();

      for (Bookmark bookmark : listOfBookmarks) {
        System.out.println(bookmark.toString());
        List<String> tags = bookmark.getTags();
        System.out.println(tags.toString());
        for (String tag_text : tags) {
          int fuzzy = StringUtils.getLevenshteinDistance(searchPattern.toLowerCase(), tag_text.toLowerCase(), 3);
          if(fuzzy <= 3 && fuzzy >= 0)
          {
            listOfFilteredBookmarks.add(bookmark);
          }
        }
      }

      return new IndexView(listOfFilteredBookmarks);
    }

    // Just load the index with everything
    return new IndexView(bookmarkDao.getAllBookmarksEvolved());
  }

  @Path("delete")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @DELETE
  public Response deleteBookmark(@FormParam("bookmarkSlug") String bookmarkSlug,
      @Context HttpHeaders hh) {

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
