package ca.csf.rdore.pinmarks.views;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Charsets;

import ca.csf.rdore.pinmarks.core.Bookmark;
import io.dropwizard.views.View;

public class BookmarksView extends View {
  List<Bookmark> bookmarks;

  public BookmarksView(List<Bookmark> bookmarks) {
    this("/views/bookmarks.ftl", Charsets.UTF_8);
    this.bookmarks = bookmarks;
  }

  protected BookmarksView(String templateName, Charset charset) {
    super(templateName);
  }

  public List<Bookmark> getAllBookmarks() {
    return bookmarks;
  }

}
