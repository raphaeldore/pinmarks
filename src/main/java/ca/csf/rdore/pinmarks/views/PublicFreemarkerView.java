package ca.csf.rdore.pinmarks.views;

import io.dropwizard.views.View;

public class PublicFreemarkerView extends View {
  public PublicFreemarkerView(String templateName) {
    super("/views/" + templateName);
  }
}
