package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
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
  @Timed
  public BookmarksView getAllBookmarks() {
    return new BookmarksView(bookmarkDao.getAllBookmarks());
  }

}
