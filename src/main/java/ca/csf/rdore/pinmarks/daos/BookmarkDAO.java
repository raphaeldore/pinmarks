package ca.csf.rdore.pinmarks.daos;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.DefaultValue;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.core.Tag;
import ca.csf.rdore.pinmarks.jdbi.BookmarkMapper;

@UseStringTemplate3StatementLocator
@RegisterMapper(BookmarkMapper.class)
public interface BookmarkDAO {

  @SqlUpdate("INSERT INTO bookmark (title, url, description, dateAdded) values (:title, :url, :description, :dateAdded)")
  void insert(@Bind("title") String title, @Bind("url") String url,
      @Bind("description") String description, @Bind("dateAdded") Timestamp dateAdded);

  @SqlUpdate("insert into bookmark (title, url, description, dateAdded, slug) values (:title, :url, :description, :dateAdded, :slug)")
  @GetGeneratedKeys
  // We need to use the bookmarkID key for the tag table
  int create(@BindBean Bookmark bookmark);

  @SqlQuery("select title, url, description, dateAdded from bookmark WHERE bookmark_id = :it")
  Bookmark findById(@Bind int id);

  // This is not very safe...
  @SqlQuery("select title, url, description, dateAdded from bookmark WHERE <columnName> LIKE :pattern")
  List<Bookmark> findBookmarksByPattern(@Define("columnName") String columnName,
      @Bind("pattern") String pattern);

  @SqlQuery("select title, url, description, dateAdded from bookmark ORDER BY dateAdded DESC")
  List<Bookmark> getAllBookmarks();
  
  @SqlUpdate("INSERT INTO tag (name) values (:name)")
  @GetGeneratedKeys
  int createNewTag(@BindBean Tag tag);
  
  @SqlBatch("INSERT INTO bookmarks_tags (bookmark_id, tag_id) values (:bookmark_id, :tag_id)")
  void batchInsertIDsIntoJunctionTable(@Bind("bookmark_id") int bookmark_id, @Bind("tag_id") List<Integer> tag_ids);
  
  @SqlBatch("INSERT INTO bookmarks_tags (bookmark_id, tag_id) values (:bookmark_id, :tag_id)")
  void insertIDIntoJunctionTable(@Bind("bookmark_id") int bookmark_id, @Bind("tag_id") int tag_id);

/* @formatter:off */

    /* Here is the getAllBookmarks query in a more human readable format: 

  SELECT b.*,
    GROUP_CONCAT(t.name ORDER BY t.name) AS tags
  FROM bookmark b
    LEFT JOIN bookmarks_tags bt
        ON b.id = bt.bookmark_id
    LEFT JOIN Tag t
        ON bt.tag_id = t.id
  GROUP BY b.id;

@formatter:on

   */

  /* @formatter:on */
  
  @SqlQuery("SELECT b.*, GROUP_CONCAT(t.name ORDER BY t.name) AS tags FROM bookmark b LEFT JOIN bookmarks_tags bt ON b.id = bt.bookmark_id LEFT JOIN Tag t ON bt.tag_id = t.id GROUP BY b.id;")
  List<Bookmark> getAllBookmarksEvolved();

  void close();
}