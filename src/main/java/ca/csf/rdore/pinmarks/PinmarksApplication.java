package ca.csf.rdore.pinmarks;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.core.Tag;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.daos.TagDAO;
import ca.csf.rdore.pinmarks.exceptions.RuntimeExceptionMapper;
import ca.csf.rdore.pinmarks.health.TemplateHealthCheck;
import ca.csf.rdore.pinmarks.resources.AddBookmarkResource;
import ca.csf.rdore.pinmarks.resources.BookmarkResource;
import ca.csf.rdore.pinmarks.resources.BookmarksResource;
import ca.csf.rdore.pinmarks.resources.IndexResource;

// import ca.csf.rdore.pinmarks.health.TemplateHealthCheck;

public class PinmarksApplication extends Application<PinmarksConfiguration> {
  private static final Logger LOGGER = LoggerFactory.getLogger(PinmarksConfiguration.class);

  public static void main(String[] args) throws Exception {
    new PinmarksApplication().run(args);
  }

  @Override
  public String getName() {
    return "Hello, World!";
  }

  @Override
  public void initialize(Bootstrap<PinmarksConfiguration> bootstrap) {

    bootstrap.addBundle(new MigrationsBundle<PinmarksConfiguration>() {
      public DataSourceFactory getDataSourceFactory(PinmarksConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });

    bootstrap.addBundle(new AssetsBundle("/assets/bootstrap/css", "/bootstrapCss", null,
        "bootstrapCss"));
    bootstrap.addBundle(new AssetsBundle("/assets/bootstrap/js", "/bootstrapJs", null,
        "bootstrapJs"));
    bootstrap.addBundle(new AssetsBundle("/assets/bootstrap/fonts", "/fonts", null,
        "bootstrapFonts"));
    bootstrap.addBundle(new AssetsBundle("/assets/css/", "/css", null, "css"));
    bootstrap.addBundle(new AssetsBundle("/assets/img/", "/img", null, "img"));
    bootstrap.addBundle(new ViewBundle());

  }

  @Override
  public void run(PinmarksConfiguration configuration, Environment environment) throws Exception {
    LOGGER.info("The Application Has Started :) ");

    final DBIFactory factory = new DBIFactory();
    final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "pinmarks");
    final BookmarkDAO bookmarkDao = jdbi.onDemand(BookmarkDAO.class);
    final TagDAO tagDao = jdbi.onDemand(TagDAO.class);
    environment.jersey().register(new BookmarkResource(bookmarkDao, tagDao));
    environment.jersey().register(new BookmarksResource(bookmarkDao, tagDao));

    // bookmarkDao.insert(1, "http://patate.com");

    String description =
        "Yes, even if you can't believe it, there are a lot fans of the 30-years-old vi editor (or its more recent, just-15-years-old, best clone & great improvement, vim).";

    DateTime dateTime = new DateTime();
    Timestamp timeStamp = new Timestamp(dateTime.getMillis());

    Bookmark bookmark =
        new Bookmark("Why, oh WHY, do those #?@! nutheads use vi",
            "http://www.viemu.com/a-why-vi-vim.html", description, timeStamp);
    int newBookmarkID = bookmarkDao.create(bookmark);

    List<Tag> bookmarksTagsList = new ArrayList<Tag>();
    bookmarksTagsList.add(new Tag("vim", newBookmarkID));
    bookmarksTagsList.add(new Tag("programming", newBookmarkID));
    bookmarksTagsList.add(new Tag("editor", newBookmarkID));
    bookmarksTagsList.add(new Tag("emacs", newBookmarkID));
    bookmarksTagsList.add(new Tag("awesome", newBookmarkID));

    tagDao.insertTagsBean(bookmarksTagsList);


      List<Bookmark> listOfBookmarks;

      listOfBookmarks = bookmarkDao.getAllBookmarks();
      
      for (Bookmark bookmark2 : listOfBookmarks) {
        System.out.println(bookmark2.toString());
      }
      
      Map<String, Object> root = new HashMap<String, Object>();
      root.put("bookmarks", listOfBookmarks);
      

    // final PinmarksResource resource =
    // new PinmarksResource(configuration.getTemplate(), configuration.getDefaultName());
    final IndexResource indexResource = new IndexResource(bookmarkDao);
    final AddBookmarkResource addBookmarkResource = new AddBookmarkResource();
    final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

    environment.healthChecks().register("template", healthCheck);
    environment.jersey().register(new RuntimeExceptionMapper());
    // environment.jersey().register(resource);
    environment.jersey().register(indexResource);
    environment.jersey().register(addBookmarkResource);
  }

}
