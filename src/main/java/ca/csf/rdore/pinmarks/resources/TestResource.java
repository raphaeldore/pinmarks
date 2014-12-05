package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.views.TestView;

@Path("/test")
@Produces(MediaType.TEXT_HTML)
public class TestResource {
  BookmarkDAO bookmarkDao;
  
  public TestResource(BookmarkDAO bookmarkDao) {
    this.bookmarkDao = bookmarkDao;
  }
  
  @GET
  public TestView getBookmarks() {
    return new TestView(bookmarkDao.getAllBookmarks());
  }
  
  
}
