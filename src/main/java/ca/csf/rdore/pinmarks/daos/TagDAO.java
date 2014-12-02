package ca.csf.rdore.pinmarks.daos;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;

import ca.csf.rdore.pinmarks.core.Tag;

public interface TagDAO {
  @SqlBatch("INSERT INTO TAG (tag_text, bookmark_id) values (:tagText, :bookmarkID)")
  void insertTags(@Bind("tagText") List<String> tagText, @Bind("bookmarkID") int bookmarkID);
  
  @SqlBatch("INSERT INTO TAG (tag_text, bookmark_id) values (:tagText, :bookmarkID)")
  void insertTagsBean(@BindBean List<Tag> bookmarkTags);
  
}
