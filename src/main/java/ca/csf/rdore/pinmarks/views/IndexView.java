package ca.csf.rdore.pinmarks.views;

import io.dropwizard.views.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.csf.rdore.pinmarks.core.Bookmark;

import com.google.common.base.Charsets;

public class IndexView extends View {

  List<Bookmark> bookmarks;
  List<HashMap<String,Integer>> tagStats = new ArrayList<HashMap<String,Integer>>();
  
  public IndexView(List<Bookmark> bookmarks, List<HashMap<String,Integer>> tagStats) {
    super("/views/index.ftl", Charsets.UTF_8);
    this.bookmarks = bookmarks;
    this.tagStats = tagStats;
  }
  
  public List<Bookmark> getBookmarks() {
    return bookmarks;
  }
  
  public List<HashMap<String,Integer>> getTagStats() {
    return tagStats;
  }

}
