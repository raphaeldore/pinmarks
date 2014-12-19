package ca.csf.rdore.pinmarks.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

public class Bookmark {

  private String title;
  @NotEmpty
  private String url;
  private String description;
  private List<String> tags = new ArrayList<String>();
  private String slug; // Random string used for generating the URL, and the delete url.
  private Timestamp dateAdded;

  // When we create a bookmark, we don't really know its id.
  public Bookmark(String title, String url, String description, Timestamp dateAdded,
      List<String> tags, String slug) {
    setTitle(title);
    setUrl(url);
    setDescription(description);
    setDateAdded(dateAdded);
    setTags(tags);
    setSlug(slug);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public Timestamp getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(Timestamp dateAdded) {
    this.dateAdded = dateAdded;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
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
    return Objects.equals(title, bookmark.title)            &&
        Objects.equals(url, bookmark.url)                   &&
        Objects.equals(description, bookmark.description)   &&
        Objects.equals(dateAdded, bookmark.dateAdded)       &&
        Objects.equals(tags, bookmark.tags)                 &&
        Objects.equals(slug, bookmark.slug);
    /* @formatter:on */
  }

  @Override
  public String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
  }
}
