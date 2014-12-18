package ca.csf.rdore.pinmarks.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

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
  public IndexView index(@QueryParam("search") String searchPattern,
      @DefaultValue("title") @QueryParam("searchBy") String searchBy) {

    // User is searching, only display the search results
    if (searchPattern != null
        && (searchBy.toLowerCase().contentEquals("title") || searchBy.toLowerCase().contentEquals(
            "description"))) {
      List<Bookmark> listOfBookmarks = bookmarkDao.getAllBookmarksEvolved();
      List<Bookmark> listOfFilteredBookmarks = new ArrayList<Bookmark>();
      if (searchBy.contentEquals("title")) {
        for (Bookmark bookmark : listOfBookmarks) {
          if (bookmark.getTitle().toLowerCase().contains(searchPattern.toLowerCase())) {
            listOfFilteredBookmarks.add(bookmark);
          }
        }
      } else {
        for (Bookmark bookmark : listOfBookmarks) {
          if (bookmark.getDescription().toLowerCase().contains(searchPattern.toLowerCase())) {
            listOfFilteredBookmarks.add(bookmark);
          }
        }

        return new IndexView(listOfFilteredBookmarks, bookmarkDao.getTagStats());
      }

    } else if (searchBy.toLowerCase().contentEquals("tag")) {

      // Here we get all the bookmarks, and filter them by tag. If the user misspells a tag, the
      // search might still be successful, since we use the Levenshtein algorithm ("Fuzzy Search").
      // More info: http://www.let.rug.nl/kleiweg/lev/
      List<Bookmark> listOfBookmarks = bookmarkDao.getAllBookmarksEvolved();
      List<Bookmark> listOfFilteredBookmarks = new ArrayList<Bookmark>();

      for (Bookmark bookmark : listOfBookmarks) {
        List<String> tags = bookmark.getTags();
        for (String tag_text : tags) {
          int searchedTagLevenshteinDistance =
              StringUtils.getLevenshteinDistance(searchPattern.toLowerCase(),
                  tag_text.toLowerCase(), 3);
          if (searchedTagLevenshteinDistance >= 0 && searchedTagLevenshteinDistance <= 3) {
            // All bookmarks that satisfy the conditions are added to the listOfFilteredBookmarks 
            listOfFilteredBookmarks.add(bookmark);
          }
        }
      }

      return new IndexView(listOfFilteredBookmarks, bookmarkDao.getTagStats());
    }

    // If the user isn't searching, just show all the bookmarks
    return new IndexView(bookmarkDao.getAllBookmarksEvolved(), bookmarkDao.getTagStats());
  }

}
