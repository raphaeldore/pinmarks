package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.views.BookmarksView;

@Path("/bookmarks")
@Produces(MediaType.TEXT_HTML)
public class BookmarksResource {
  BookmarkDAO bookmarkDao;

  public BookmarksResource(BookmarkDAO bookmarkDao) {
    this.bookmarkDao = bookmarkDao;
  }
  
  @GET
  public BookmarksView getBookmarks() {
    return new BookmarksView(bookmarkDao.getAllBookmarks());
  }
}
