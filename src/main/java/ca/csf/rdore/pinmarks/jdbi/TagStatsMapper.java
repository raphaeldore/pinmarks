package ca.csf.rdore.pinmarks.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TagStatsMapper implements ResultSetMapper<HashMap<String, Integer>> {

  public HashMap<String, Integer> map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    HashMap<String, Integer> tagStat = new HashMap<String, Integer>();
    
    tagStat.put(r.getString("name"), r.getInt("num_items"));
    
    return tagStat;
  }

}
