package ca.csf.rdore.pinmarks.resources;

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
    if (searchPattern != null) {
      return new IndexView(bookmarkDao.findBookmarksByPattern(searchBy , "%" + searchPattern + "%"));
    }
    
/*    List<Bookmark> listOfBookmarks = new ArrayList<Bookmark>();
    
    System.out.println(searchBy);

    if (searchBy == "title" || searchBy == "description") {
      //listOfBookmarks = bookmarkDao.findBookmarksByPattern(searchBy, "%" + searchPattern + "%");
      return new IndexView(bookmarkDao.findBookmarksByPattern(searchBy , "%" + searchPattern + "%"));
    } else if (searchBy == "tag") {
      List<String> tags = Lists.newArrayList(Splitter.on(" ").split(searchPattern));
      List<Integer> bookmarkIDs = tagDao.findBookmarksByTag(tags);
      for (Integer bookmarkID : bookmarkIDs) {
        listOfBookmarks.add(bookmarkDao.findById(bookmarkID));
      }
    } else {
      listOfBookmarks = bookmarkDao.getAllBookmarks();
    }*/
    
    // Just load the index with everything
    return new IndexView(bookmarkDao.getAllBookmarks());
  }

}
