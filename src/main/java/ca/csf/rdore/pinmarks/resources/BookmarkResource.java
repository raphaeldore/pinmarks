package ca.csf.rdore.pinmarks.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.views.BookmarkView;

@Path("/bookmark/{id}")
@Produces(MediaType.TEXT_HTML)
// @Consumes("*/*")
public class BookmarkResource {

  BookmarkDAO bookmarkDao;
  TagDAO tagDao;

  public BookmarkResource(BookmarkDAO bookmarkDao, TagDAO tagDao) {
    this.bookmarkDao = bookmarkDao;
    this.tagDao = tagDao;
  }

  @GET
  @Timed
  public BookmarkView getBookmark(@PathParam("id") int id) {
    return new BookmarkView(bookmarkDao.findById(id));
  }



}
