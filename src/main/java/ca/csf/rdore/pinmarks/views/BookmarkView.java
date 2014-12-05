package ca.csf.rdore.pinmarks.views;

import io.dropwizard.views.View;
import ca.csf.rdore.pinmarks.core.Bookmark;

public class BookmarkView extends View {

  Bookmark bookmark;

  public BookmarkView(Bookmark bookmark) {
    super("/views/bookmark.ftl");
    this.bookmark = bookmark;
  }

  public Bookmark getBookmark() {
    return bookmark;
  }

}
