package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.views.BookmarkView;
import ca.csf.rdore.pinmarks.views.BookmarksView;

import com.codahale.metrics.annotation.Timed;

@Path("/bookmarks")
@Produces(MediaType.TEXT_HTML)
// @Consumes("*/*")
public class BookmarksResource {
  BookmarkDAO bookmarkDao;
  TagDAO tagDao;

  public BookmarksResource(BookmarkDAO bookmarkDao, TagDAO tagDao) {
    this.bookmarkDao = bookmarkDao;
    this.tagDao = tagDao;
  }
  
  @GET
  public BookmarksView getBookmarks() {
    return new BookmarksView(bookmarkDao.getAllBookmarksEvolved());
  }

  // /bookmarks/slug
  @Path("/{slug}")
  @GET
  @Timed
  public BookmarkView getBookmark(@PathParam("slug") String slug) {
    return null;
    // TODO
    //return new BookmarkView(bookmarkDao.getBySlug(slug));
  }

}
