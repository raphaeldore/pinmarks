package ca.csf.rdore.pinmarks.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import ca.csf.rdore.pinmarks.core.Bookmark;

public class BookmarkMapper implements ResultSetMapper<Bookmark> {

  public Bookmark map(int index, ResultSet r, StatementContext ctx) throws SQLException {

    String tags = r.getString("Tags");
    List<String> bookmarksTags = new ArrayList<String>();

    if (tags != null && tags.length() != 0) {
      bookmarksTags = Arrays.asList(tags.split("\\s*(=>|,|\\s)\\s*"));
    } else {
      bookmarksTags.add("");
    }

    return new Bookmark(r.getString("title"), r.getString("url"), r.getString("description"),
        r.getTimestamp("dateAdded"), bookmarksTags, r.getString("slug"));
  }

}
