package ca.csf.rdore.pinmarks.resources;

import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.views.AddBookmarkView;

@Path("/addBookmark")

public class AddBookmarkResource {
  
  @GET
  @Produces(MediaType.TEXT_HTML)
  public View addBookmark() {
    return new AddBookmarkView();
  }

}
