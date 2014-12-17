package ca.csf.rdore.pinmarks.views;

import io.dropwizard.views.View;

import java.util.List;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.core.Tag;

import com.google.common.base.Charsets;

public class IndexView extends View {

  List<Bookmark> bookmarks;
  List<Tag> tags;

  public IndexView(List<Bookmark> bookmarks, List<Tag> tags) {
    super("/views/index.ftl", Charsets.UTF_8);
    this.bookmarks = bookmarks;
    this.tags = tags;
  }
  
  public IndexView(List<Bookmark> bookmarks) {
    super("/views/index.ftl", Charsets.UTF_8);
    this.bookmarks = bookmarks;
  }
  
  public List<Bookmark> getBookmarks() {
    return bookmarks;
  }

}
