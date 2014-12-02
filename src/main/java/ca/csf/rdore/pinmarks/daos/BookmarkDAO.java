package ca.csf.rdore.pinmarks.daos;

import java.util.List;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface BookmarkDAO {
  @SqlUpdate("create table if not exists bookmark(id int NOT NULL auto_increment, title varchar(255), url text, description text, tags array, time dateAdded PRIMARY KEY (id)")
  void createTableIfNotExists();

  @SqlUpdate("insert into bookmark (id, title, url, description, tags, dateAdded) values (:id, :title, :url, :description, :tags, :dateAdded)")
  void insert(@Bind("id") int id, @Bind("title") String title, @Bind("url") String url, @Bind("description") String description,  @Bind("tags") List<String> tags, @Bind("dateAdded") DateTime dateAdded);

  void close();
}
