package ca.csf.rdore.pinmarks.views;

import java.nio.charset.Charset;

import com.google.common.base.Charsets;

import io.dropwizard.views.View;

public class IndexView extends View {

  public IndexView() {
    this("/views/index.ftl", Charsets.UTF_8);
  }
  
  protected IndexView(String templateName, Charset charset) {
    super(templateName, charset);
  }

}
