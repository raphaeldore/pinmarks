package ca.csf.rdore.pinmarks.views;

import java.util.List;

import ca.csf.rdore.pinmarks.core.Bookmark;
import io.dropwizard.views.View;

public class BookmarksView extends View {

  List<Bookmark> bookmarks;

  public BookmarksView(List<Bookmark> bookmarks) {
    super("/views/bookmarks.ftl");
    this.bookmarks = bookmarks;
  }

  public List<Bookmark> getBookmarks() {
    return bookmarks;
  }

}
