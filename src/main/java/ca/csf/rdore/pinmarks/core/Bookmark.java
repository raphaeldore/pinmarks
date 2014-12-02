package ca.csf.rdore.pinmarks.core;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.google.common.base.Optional;

public class Bookmark {

  @NotNull
  private final int id;

  @NotEmpty 
  private final String title;

  @NotEmpty
  private final String url;

  private final Optional<String> description;
  private final List<String> tags;

  @NotNull
  private final DateTime dateAdded;

  private Bookmark(int id, String title, String url, Optional<String> description,
      List<String> tags, DateTime dateAdded) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.description = description;
    this.tags = tags;
    this.dateAdded = dateAdded;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }

  public Optional<String> getDescription() {
    return description;
  }

  public List<String> getTags() {
    return tags;
  }

  public DateTime getDateAdded() {
    return dateAdded;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, url, description, tags, dateAdded);
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
    return Objects.equals(id, bookmark.id) &&
        Objects.equals(title, bookmark.title) &&
        Objects.equals(url, bookmark.url) &&
        Objects.equals(description, bookmark.description) &&
        Objects.equals(tags, bookmark.tags);
    /* @formatter:on */
  }

  @Override
  public String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).toString();
  }
}
