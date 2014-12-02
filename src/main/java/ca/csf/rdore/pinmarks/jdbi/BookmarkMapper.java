package ca.csf.rdore.pinmarks.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import ca.csf.rdore.pinmarks.core.Bookmark;

public class BookmarkMapper implements ResultSetMapper<Bookmark> {

  public Bookmark map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    return new Bookmark(r.getString("title"), r.getString("url"),
        r.getString("description"), r.getTimestamp("dateAdded"));
  }

}
