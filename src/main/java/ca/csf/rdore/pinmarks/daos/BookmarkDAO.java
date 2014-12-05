package ca.csf.rdore.pinmarks.daos;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.DefaultValue;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.jdbi.BookmarkMapper;

@UseStringTemplate3StatementLocator
@RegisterMapper(BookmarkMapper.class)
public interface BookmarkDAO {
  @SqlUpdate("CREATE TABLE IF NOT EXISTS bookmark(bookmark_id INT NOT NULL auto_increment, title VARCHAR(255), url TEXT, description TEXT, TIMESTAMP dateAdded PRIMARY KEY (bookmark_id)")
  void createTableIfNotExists();

  @SqlUpdate("INSERT INTO bookmark (title, url, description, dateAdded) values (:title, :url, :description, :dateAdded)")
  void insert(@Bind("title") String title, @Bind("url") String url, @Bind("description") String description, @Bind("dateAdded") Timestamp dateAdded);
  
  @SqlUpdate("insert into bookmark (title, url, description, dateAdded) values (:title, :url, :description, :dateAdded)")
  @GetGeneratedKeys // We need to use the bookmarkID key for the tag table
  int create(@BindBean Bookmark bookmark);
  
  @SqlQuery("select title, url, description, dateAdded from bookmark WHERE bookmark_id = :it")
  Bookmark findById(@Bind int id);
  
  // This is not very safe...
  @SqlQuery("select title, url, description, dateAdded from bookmark WHERE <columnName> LIKE :pattern")
  List<Bookmark> findBookmarksByPattern(@Define("columnName") String columnName, @Bind("pattern") String pattern);
  
  @SqlQuery("select title, url, description, dateAdded from bookmark ORDER BY dateAdded DESC")
  List<Bookmark> getAllBookmarks();

  void close();
}
