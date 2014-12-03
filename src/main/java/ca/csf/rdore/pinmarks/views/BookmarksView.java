package ca.csf.rdore.pinmarks.views;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Charsets;

import ca.csf.rdore.pinmarks.core.Bookmark;
import io.dropwizard.views.View;

public class BookmarksView extends View {
  Collection<Bookmark> bookmarks;

  public BookmarksView(Collection<Bookmark> bookmarks) {
    this("/views/bookmarks.ftl", Charsets.UTF_8);
    this.bookmarks = bookmarks;
  }

  protected BookmarksView(String templateName, Charset charset) {
    super(templateName);
  }

  public Collection<Bookmark> getAllBookmarks() {
    return bookmarks;
  }

}
