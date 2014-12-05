package ca.csf.rdore.pinmarks.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.views.BookmarkView;
import ca.csf.rdore.pinmarks.views.BookmarksView;
import ca.csf.rdore.pinmarks.views.TestView;

import com.codahale.metrics.annotation.Timed;

@Path("/bookmarks")
@Produces(MediaType.TEXT_HTML)
// @Consumes("*/*")
public class BookmarksResource {
  BookmarkDAO bookmarkDao;
  TagDAO tagDao;

  public BookmarksResource(BookmarkDAO bookmarkDao, TagDAO tagDao) {
    this.bookmarkDao = bookmarkDao;
    this.tagDao = tagDao;
  }

  // /bookmarks/id
  @Path("/{id}")
  @GET
  @Timed
  public BookmarkView getBookmark(@PathParam("id") int id) {
    return new BookmarkView(bookmarkDao.findById(id));
  }

  // @Path("/all")
  @GET
  @Timed
  public TestView getAllBookmarks() {
    
    List<Bookmark> listOfBookmarks;

    listOfBookmarks = bookmarkDao.getAllBookmarks();
    
    if (listOfBookmarks == null) {
      System.out.println("List Of Bookmarks is NULL");
    } else {
      System.out.println("List Of Bookmarks is NOT NOT NOT NOT NOT NULL");
      int number = 1;
      for (Bookmark bookmark : listOfBookmarks) {
        System.out.println("Bookmark #" + number);
        System.out.println(bookmark.toString());
        number++;
      }
    }
    
    return new TestView(listOfBookmarks);
  }

}
