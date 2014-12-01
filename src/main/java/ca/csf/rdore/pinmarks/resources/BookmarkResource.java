package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import ca.csf.rdore.pinmarks.daos.BookmarkDAO;

@Path("/bookmarks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookmarkResource {

  BookmarkDAO bookmarkDao;
  
  public BookmarkResource(BookmarkDAO bookmarkDao) {
    this.bookmarkDao = bookmarkDao;
  }
  


}
