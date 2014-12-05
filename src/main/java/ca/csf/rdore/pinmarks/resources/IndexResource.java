package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.views.BookmarksView;
import ca.csf.rdore.pinmarks.views.IndexView;

import com.codahale.metrics.annotation.Timed;

import io.dropwizard.views.*;

@Path("/")
public class IndexResource {

  BookmarkDAO bookmarkDao;
  
  public IndexResource(BookmarkDAO bookmarkDao) {
    this.bookmarkDao = bookmarkDao;
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  @Timed
  public View index() {
    return new IndexView();
  }
  
/*  @GET
  public BookmarksView getBookmarks() {
    return new BookmarksView(bookmarkDao.getAllBookmarks());
  }  */

}
