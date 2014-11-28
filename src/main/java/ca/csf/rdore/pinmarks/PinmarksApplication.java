package ca.csf.rdore.pinmarks;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import ca.csf.rdore.pinmarks.health.TemplateHealthCheck;
import ca.csf.rdore.pinmarks.resources.IndexResource;
import ca.csf.rdore.pinmarks.resources.PinmarksResource;

// import ca.csf.rdore.pinmarks.health.TemplateHealthCheck;

public class PinmarksApplication extends Application<PinmarksConfiguration> {
  public static void main(String[] args) throws Exception {
    new PinmarksApplication().run(args);
  }

  @Override
  public String getName() {
    return "Hello, World!";
  }

  @Override
  public void initialize(Bootstrap<PinmarksConfiguration> bootstrap) {
    bootstrap.addBundle(new AssetsBundle("/assets/bootstrap/css", "/css", null, "css"));
    bootstrap.addBundle(new AssetsBundle("/assets/bootstrap/js", "/js", null, "js"));
    bootstrap.addBundle(new AssetsBundle("/assets/bootstrap/fonts", "/fonts", null, "fonts"));
    
    bootstrap.addBundle(new ViewBundle());
  }

  @Override
  public void run(PinmarksConfiguration configuration, Environment environment) throws Exception {
    final PinmarksResource resource =
        new PinmarksResource(configuration.getTemplate(), configuration.getDefaultName());
    final IndexResource indexResource = new IndexResource();
    final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

    environment.healthChecks().register("template", healthCheck);
    environment.jersey().register(resource);
    environment.jersey().register(indexResource);
  }

}
