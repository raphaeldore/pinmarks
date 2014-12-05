package ca.csf.rdore.pinmarks.views;

import io.dropwizard.views.View;

import java.util.List;

import ca.csf.rdore.pinmarks.core.Bookmark;

public class IndexView extends View {

  List<Bookmark> bookmarks;

  public IndexView(List<Bookmark> bookmarks) {
    super("/views/index.ftl");
    this.bookmarks = bookmarks;
  }
  
  public List<Bookmark> getBookmarks() {
    return bookmarks;
  }

}
