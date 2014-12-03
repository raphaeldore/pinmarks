package ca.csf.rdore.pinmarks.core;

import java.sql.Timestamp;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

public class Bookmark {

//  @NotNull
//  private final int id;

  @NotEmpty
  private final String title;

  @NotEmpty
  private final String url;

  private final String description;

  @NotNull
  private final Timestamp dateAdded;
  
  public Bookmark(String title, String url, String description, Timestamp dateAdded) {
    this.title = title;
    this.url = url;
    this.description = description;
    this.dateAdded = dateAdded;
  }

/*  public int getId() {
    return id;
  }
*/
  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }

  public Timestamp getDateAdded() {
    return dateAdded;
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, url, description, dateAdded);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Bookmark bookmark = (Bookmark) o;

    /* @formatter:off */
    return Objects.equals(title, bookmark.title) &&
        Objects.equals(url, bookmark.url) &&
        Objects.equals(description, bookmark.description) &&
        Objects.equals(dateAdded, bookmark.dateAdded);
    /* @formatter:on */
  }

  @Override
  public String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
  }
}
