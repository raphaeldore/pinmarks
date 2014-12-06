package ca.csf.rdore.pinmarks.views;

import com.google.common.base.Charsets;

import io.dropwizard.views.View;
import ca.csf.rdore.pinmarks.core.Bookmark;

public class BookmarkView extends View {

  Bookmark bookmark;

  public BookmarkView(Bookmark bookmark) {
    super("/views/bookmark.ftl", Charsets.UTF_8);
    this.bookmark = bookmark;
  }

  public Bookmark getBookmark() {
    return bookmark;
  }

}
