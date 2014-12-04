package ca.csf.rdore.pinmarks.views;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Charsets;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;

import ca.csf.rdore.pinmarks.core.Bookmark;
import io.dropwizard.views.View;

public class BookmarksView extends View {
  
  private static final GenericType<List<Bookmark>> GT_LIST_BOOKMARK = new GenericType<List<Bookmark>>() {};

  
  List<Bookmark> bookmarks;

  public BookmarksView(List<Bookmark> bookmarks) {
    this("/views/bookmarks.ftl", Charsets.UTF_8);
    this.bookmarks = bookmarks;
  }

  protected BookmarksView(String templateName, Charset charset) {
    super(templateName);
  }

  public List<Bookmark> getAllBookmarks() {
   // List<Bookmark> bookmarks2 = Client.resource("/bookmark").get(GT_LIST_BOOKMARK);
    return bookmarks;
  }

}