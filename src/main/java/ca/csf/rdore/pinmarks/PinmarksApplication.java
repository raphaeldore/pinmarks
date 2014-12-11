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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hashids.Hashids;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

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
import ca.csf.rdore.pinmarks.resources.TestResource;

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
    final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
    
    Hashids hashid = new Hashids("https://www.youtube.com/watch?v=dQw4w9WgXcQ", 10);
    
    DateTime dateTime = new DateTime();
    int bookmark_id = bookmarkDao.create(new Bookmark("Vim is awesome", "http://vimisawesome.com", " vim vim vim vim vim vim vim vim vim vim", new Timestamp(dateTime.getMillis()), null, hashid.encode(1337)));

    String tags = "editor,programming,vim,wars";
    
    List<String> stringTagsList = Arrays.asList(tags.split("\\s*(=>|,|\\s)\\s*"));
    List<Integer> tag_ids = new ArrayList<Integer>();
    
    
    for (String tag : stringTagsList) {
      tag_ids.add(bookmarkDao.createNewTag(new Tag(tag)));
    }
    
    // WHY IS THERE AN OUT OF MEMORY ERROR??!?!?!
    
    // int tag_id = bookmarkDao.createNewTag("vim");
    
    
   bookmarkDao.batchInsertIDsIntoJunctionTable(bookmark_id, tag_ids);
    
    List<Bookmark> bookmarksList = new ArrayList<Bookmark>();
    bookmarksList = bookmarkDao.getAllBookmarksEvolved();
    
    for (Bookmark bookmark : bookmarksList) {
      System.out.println(bookmark.toString());
    }

    environment.healthChecks().register("template", healthCheck);
    environment.jersey().register(new RuntimeExceptionMapper());
    // environment.jersey().register(resource);
    environment.jersey().register(new IndexResource(bookmarkDao, tagDao));
    environment.jersey().register(new AddBookmarkResource(bookmarkDao, tagDao));
    environment.jersey().register(new BookmarkResource(bookmarkDao, tagDao));
    environment.jersey().register(new BookmarksResource(bookmarkDao, tagDao));
    // environment.jersey().register(new TestResource(bookmarkDao));
  }

}
