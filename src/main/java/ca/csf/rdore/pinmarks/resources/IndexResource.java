package ca.csf.rdore.pinmarks.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.views.IndexView;

import com.codahale.metrics.annotation.Timed;

@Path("/")
public class IndexResource {

  BookmarkDAO bookmarkDao;
  TagDAO tagDao;

  public IndexResource(BookmarkDAO bookmarkDao, TagDAO tagDao) {
    this.bookmarkDao = bookmarkDao;
    this.tagDao = tagDao;
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  @Timed
  public IndexView index(@QueryParam("search") String searchPattern, @DefaultValue("title") @QueryParam("searchBy") String searchBy) {
    
    // User is searching, only display the search results
    if (searchPattern != null && (searchBy.contentEquals("title") || searchBy.contentEquals("description"))) {
      return new IndexView(bookmarkDao.findBookmarksByPattern(searchBy , "%" + searchPattern + "%"));
    } else if (searchBy.contentEquals("tag")) {
      List<Bookmark> listOfBookmarks = bookmarkDao.searchByTags(searchPattern);
/*      List<Integer> bookmarkIDs = tagDao.findBookmarksByTagVersionTWO(getFirstWord(searchPattern));
      for (Integer integer : bookmarkIDs) {
        listOfBookmarks.add(bookmarkDao.findById(integer));
      }*/
      return new IndexView(listOfBookmarks);
    }
    
    // Just load the index with everything
    return new IndexView(bookmarkDao.getAllBookmarksEvolved());
  }

  private String getFirstWord(String text) {
    if (text.indexOf(' ') > -1) { // Check if there is more than one word.
      return text.substring(0, text.indexOf(' ')); // Extract first word.
    } else {
      return text; // Text is the first word itself.
    }
  }
  
}
