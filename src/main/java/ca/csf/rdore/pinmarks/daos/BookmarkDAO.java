package ca.csf.rdore.pinmarks.daos;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface BookmarkDAO {
  @SqlUpdate("create table if not exists bookmark(id int NOT NULL auto_increment, url text, tags text, PRIMARY KEY (id)")
  void createTableIfNotExists();
  
  @SqlUpdate("insert into bookmark (id, url) values (:id, :url)")
  void insert(@Bind("id") int id, @Bind("url") String url);
  
  void close();
}
