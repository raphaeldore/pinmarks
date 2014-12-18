package ca.csf.rdore.pinmarks.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.views.BookmarkView;

import com.codahale.metrics.annotation.Timed;

@Path("/bookmark")
@Produces(MediaType.TEXT_HTML)
public class BookmarkResource_old {

  BookmarkDAO bookmarkDao;
  TagDAO tagDao;

  public BookmarkResource_old(BookmarkDAO bookmarkDao, TagDAO tagDao) {
    this.bookmarkDao = bookmarkDao;
    this.tagDao = tagDao;
  }
  
  @Path("{bookmarkSlug}")
  @GET
  @Timed
  public BookmarkView getBookmark(@PathParam("bookmarkSlug") String bookmarkSlug) {
    Bookmark bookmark = bookmarkDao.getBookmarkBySlug(bookmarkSlug);
    if (bookmark == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }
    
    return new BookmarkView(bookmark);
  }
  
  
  @Path("{bookmarkSlug}/edit")
  @GET
  @Timed
  public BookmarkView editBookmark(@PathParam("bookmarkSlug") String bookmarkSlug) {
    Bookmark bookmark = bookmarkDao.getBookmarkBySlug(bookmarkSlug);
    if (bookmark == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }
    
    return new BookmarkView(bookmark);
  }


}
