package ca.csf.rdore.pinmarks.daos;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import ca.csf.rdore.pinmarks.core.Tag;

@UseStringTemplate3StatementLocator
public interface TagDAO {
  @SqlBatch("INSERT INTO tag (name, bookmark_id) values (:tagText, :bookmarkID)")
  void insertTags(@Bind("tagText") List<String> tagText, @Bind("bookmarkID") int bookmarkID);
  
  @SqlBatch("INSERT INTO tag (tag_text, bookmark_id) values (:tagText, :bookmarkID)")
  void insertTagsBean(@BindBean List<Tag> bookmarkTags);
  
  @SqlQuery("SELECT tag.bookmark_id FROM tag WHERE (tag.tag_text IN (:it)) GROUP BY tag.bookmark_id")
  List<Integer> findBookmarksByTag(@Bind String tag);
  
  @SqlQuery("SELECT tag.bookmark_id FROM tag WHERE (tag.tag_text IN (:it)) GROUP BY tag.bookmark_id")
  List<Integer> findBookmarksByTagVersionTWO(@Bind String tag);
  
  @SqlQuery("SELECT tag.bookmark_id FROM tag WHERE (tag.tag_text IN (:it))")
  List<Integer> findBookmarksByTagVersionThree(@Bind String[] tags);
  
}
