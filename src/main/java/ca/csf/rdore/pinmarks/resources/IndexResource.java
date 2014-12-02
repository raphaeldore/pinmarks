package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.views.IndexView;

import com.codahale.metrics.annotation.Timed;

import io.dropwizard.views.*;

@Path("/")
public class IndexResource {

  BookmarkDAO bookmarkDAO;
  
  public IndexResource(BookmarkDAO bookmarkDAO) {
    this.bookmarkDAO = bookmarkDAO;
  }


  @GET
  @Produces(MediaType.TEXT_HTML)
  @Timed
  public View index() {
    return new IndexView();
  }

}
