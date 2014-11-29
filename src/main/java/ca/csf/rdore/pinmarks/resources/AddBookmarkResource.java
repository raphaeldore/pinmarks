package ca.csf.rdore.pinmarks.resources;

import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.csf.rdore.pinmarks.views.AddBookmarkView;

@Path("/addBookmark")
public class AddBookmarkResource {
  
  @GET
  public View addBookmark() {
    return new AddBookmarkView();
  }

}
