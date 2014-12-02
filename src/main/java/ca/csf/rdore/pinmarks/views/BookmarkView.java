package ca.csf.rdore.pinmarks.views;

import java.nio.charset.Charset;

import ca.csf.rdore.pinmarks.core.Bookmark;

import com.google.common.base.Charsets;

import io.dropwizard.views.View;

public class BookmarkView extends View {

  Bookmark bookmark;
  
  public BookmarkView(Bookmark bookmark) {
    this("/views/bookmark.ftl", Charsets.UTF_8);
    this.bookmark = bookmark;
  }
  
  protected BookmarkView(String templateName, Charset charset) {
    super(templateName, charset);
  }
  
  public Bookmark getBookmark() {
    return bookmark;
  }

}
