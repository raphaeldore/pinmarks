package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.views.BookmarkView;

@Path("/bookmarks/{id}")
@Produces(MediaType.TEXT_HTML)
public class BookmarkResource {

  BookmarkDAO bookmarkDao;
  
  public BookmarkResource(BookmarkDAO bookmarkDao) {
    this.bookmarkDao = bookmarkDao;
  }
  
  @GET
  public BookmarkView getBookmark(@PathParam("id") int id) {
    return new BookmarkView(bookmarkDao.findById(id));
  }
  


}
