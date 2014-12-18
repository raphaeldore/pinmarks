package ca.csf.rdore.pinmarks.daos;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlStatementCustomizingAnnotation;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.core.Tag;
import ca.csf.rdore.pinmarks.jdbi.BookmarkMapper;
import ca.csf.rdore.pinmarks.jdbi.TagStatsMapper;

@UseStringTemplate3StatementLocator
@RegisterMapper(BookmarkMapper.class)
public interface BookmarkDAO {

  /* @formatter:off */
  
  @SqlUpdate("INSERT INTO bookmark (title, url, description, dateAdded) values (:title, :url, :description, :dateAdded)")
  void insert(@Bind("title") String title, @Bind("url") String url,
      @Bind("description") String description, @Bind("dateAdded") Timestamp dateAdded);

  @SqlUpdate("insert into bookmark (title, url, description, dateAdded, slug) values (:title, :url, :description, :dateAdded, :slug)")
  @GetGeneratedKeys
  // We need to use the bookmarkID key for the tag table
  int create(@BindBean Bookmark bookmark);
  
  @SqlUpdate("UPDATE bookmark SET title = :title, url = :url, description = :description WHERE slug = :slug;")
  void updateBookmark(@BindBean Bookmark bookmark);
  
  @SqlQuery("SELECT id from bookmark where slug = :slug")
  int getBookmarkIDfromSlug(@Bind("slug") String bookmarkSlug);
  
  @SqlUpdate("DELETE FROM bookmarks_tags WHERE bookmark_id = :bookmark_id")
  void deleteBookmarkTags(@Bind("bookmark_id") int bookmark_id);

  @SqlUpdate("INSERT INTO tag (name) values (:name)")
  @GetGeneratedKeys
  int createNewTag(@BindBean Tag tag);
  
  @SqlBatch("INSERT INTO bookmarks_tags (bookmark_id, tag_id) values (:bookmark_id, :tag_id)")
  void batchInsertIDsIntoJunctionTable(@Bind("bookmark_id") int bookmark_id, @Bind("tag_id") List<Integer> tag_ids);
  
  @SqlBatch("INSERT INTO bookmarks_tags (bookmark_id, tag_id) values (:bookmark_id, :tag_id)")
  void insertIDIntoJunctionTable(@Bind("bookmark_id") int bookmark_id, @Bind("tag_id") int tag_id);

    /* Here is the getAllBookmarks query in a more human readable format: 

SELECT b.*,
       Group_concat(t.name ORDER BY t.name) AS tags -- Temporary table
FROM   bookmark b
       LEFT JOIN bookmarks_tags bt
              ON b.id = bt.bookmark_id
       LEFT JOIN tag t
              ON bt.tag_id = t.id
GROUP  BY b.id
ORDER  BY dateadded DESC;  

   */
  
 
  @SqlQuery("SELECT b.*, GROUP_CONCAT(t.name ORDER BY t.name) AS tags FROM bookmark b LEFT JOIN bookmarks_tags bt ON b.id = bt.bookmark_id LEFT JOIN Tag t ON bt.tag_id = t.id GROUP BY b.id ORDER BY dateAdded DESC;")
  List<Bookmark> getAllBookmarksEvolved();
  
  @SqlQuery("SELECT b.*, GROUP_CONCAT(t.name ORDER BY t.name) AS tags FROM bookmark b LEFT JOIN bookmarks_tags bt ON b.id = bt.bookmark_id LEFT JOIN Tag t ON bt.tag_id = t.id WHERE slug = :bookmark_slug GROUP BY b.id;")
  Bookmark getBookmarkBySlug(@Bind("bookmark_slug") String slug);
  /*
  
    In Human readable format:
    
SELECT b.*,
       Group_concat(t.name ORDER BY t.name) AS tags
FROM   bookmark b
       LEFT JOIN bookmarks_tags bt1
              ON b.id = bt1.bookmark_id
       LEFT JOIN tag t
              ON bt1.tag_id = t.id
WHERE  EXISTS(SELECT *
              FROM   bookmarks_tags bt2
                     INNER JOIN tag
                             ON bt2.tag_id = tag.id
              WHERE  b.id = bt2.bookmark_id
                     AND tag.name LIKE Lower(:tag_name))
GROUP  BY b.id;  
   
  */
  
  @SqlQuery("SELECT b.*, GROUP_CONCAT(t.name ORDER BY t.name) AS tags FROM bookmark b LEFT JOIN bookmarks_tags bt1 ON b.id = bt1.bookmark_id LEFT JOIN Tag t ON bt1.tag_id = t.id WHERE EXISTS(SELECT * FROM bookmarks_tags bt2 INNER JOIN tag on bt2.tag_id = tag.ID WHERE b.ID = bt2.bookmark_id AND tag.name like LOWER(:tag_name)) GROUP BY b.id;")
  List<Bookmark> searchByTags_old(@Bind("tag_name") String tag_name);
  
  /*
   
   In Human readable format:

SELECT b.*,
       Group_concat(t.name ORDER BY t.name) AS tags
FROM   bookmark b
       LEFT JOIN bookmarks_tags bt1
              ON b.id = bt1.bookmark_id
       LEFT JOIN tag t
              ON bt1.tag_id = t.id
WHERE  EXISTS(SELECT *
              FROM   bookmarks_tags bt2
                     INNER JOIN tag
                             ON bt2.tag_id = tag.id
              WHERE  b.id = bt2.bookmark_id
                     AND :tag_name LIKE Concat('%', tag.name, '%'))
GROUP  BY b.id;  
  
  */
  
  /* User must use the full tag name for this to work. Searching for "prog" doesn't return tags that have "programming",
    but searching for "programming eclipse firefox" returns a list of bookmarks that have any of those tags. */
  @SqlQuery("SELECT b.*, GROUP_CONCAT(t.name ORDER BY t.name) AS tags FROM bookmark b LEFT JOIN bookmarks_tags bt1 ON b.id = bt1.bookmark_id LEFT JOIN Tag t ON bt1.tag_id = t.id WHERE EXISTS(SELECT * FROM bookmarks_tags bt2 INNER JOIN tag on bt2.tag_id = tag.ID WHERE b.ID = bt2.bookmark_id AND :tag_name LIKE CONCAT('%',tag.name,'%')) GROUP BY b.id;")
  List<Bookmark> searchByTags(@Bind("tag_name") String tag_name);
  
  @SqlUpdate("DELETE FROM bookmark WHERE slug = :it;")
  void deleteBookmarkBySlug(@Bind String slug);
  
  @RegisterMapper(TagStatsMapper.class)
  @SqlQuery("SELECT name, COUNT(*) as num_items FROM bookmarks_tags bt INNER JOIN tag t ON bt.tag_id = t.id GROUP BY name;")
  List<HashMap<String,Integer>> getTagStats();
  
  void close();
  
  /* @formatter:on */

}
