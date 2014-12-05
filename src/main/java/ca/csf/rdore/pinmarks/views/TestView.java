package ca.csf.rdore.pinmarks.views;

import java.util.List;

import ca.csf.rdore.pinmarks.core.Bookmark;
import io.dropwizard.views.View;

public class TestView extends View {

  List<Bookmark> bookmarks;
  
  public TestView(List<Bookmark> bookmarks) {
    super("/views/test.ftl");
    this.bookmarks = bookmarks;
  }
  
  public List<Bookmark> getBookmarks()
  {
    return bookmarks;
  }

}
