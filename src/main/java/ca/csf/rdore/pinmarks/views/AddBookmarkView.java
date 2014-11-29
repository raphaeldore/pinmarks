package ca.csf.rdore.pinmarks.views;

import java.nio.charset.Charset;

import com.google.common.base.Charsets;

import io.dropwizard.views.View;

public class AddBookmarkView extends View {

  public AddBookmarkView() {
    this("/views/addBookmark.ftl", Charsets.UTF_8);
  }
  
  protected AddBookmarkView(String templateName, Charset charset) {
    super(templateName, charset);
  }
  
  
}
