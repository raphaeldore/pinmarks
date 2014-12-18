package ca.csf.rdore.pinmarks.views;

import io.dropwizard.views.View;
import ca.csf.rdore.pinmarks.core.Bookmark;

import com.google.common.base.Charsets;

public class EditBookmarkView extends View {

  Bookmark bookmark;

  public EditBookmarkView(Bookmark bookmark) {
    super("/views/editBookmark.ftl", Charsets.UTF_8);
    this.bookmark = bookmark;
  }
  
  public Bookmark getBookmark() {
    return bookmark;
  }

}
