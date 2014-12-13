package ca.csf.rdore.pinmarks.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.views.IndexView;
import ca.csf.rdore.pinmarks.views.PublicFreemarkerView;

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
        List<String> tags = bookmark.getTags();
        for (String tag_text : tags) {
          if (searchPattern.toLowerCase().contains(tag_text.toLowerCase())) {
            listOfFilteredBookmarks.add(bookmark);
          }
        }
      }

      return new IndexView(listOfFilteredBookmarks);
    }

    // Just load the index with everything
    return new IndexView(bookmarkDao.getAllBookmarksEvolved());
  }

  @Path("delete/{bookmarkSlug}")
  @POST
  public Response deleteBookmark(@PathParam("bookmarkSlug") String bookmarkSlug) {
    try {
      bookmarkDao.deleteBookmarkBySlug(bookmarkSlug);
      return Response.status(Status.OK).build();
    } catch (Exception e) {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @Path("delete/{bookmarkSlug}")
  @GET
  public Response forbidGetRequestsToDeleteBookmarkPage() {
    return Response.status(Status.FORBIDDEN).entity(new PublicFreemarkerView("/errors/403.ftl"))
        .build();
  }

}
