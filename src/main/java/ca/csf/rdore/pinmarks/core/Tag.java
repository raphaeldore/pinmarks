package ca.csf.rdore.pinmarks.core;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.Objects;

public class Tag {

  @NotEmpty
  private final String tagText;
  private final int bookmarkID;

  public Tag(String tagText, int bookmarkID) {
    this.tagText = tagText;
    this.bookmarkID = bookmarkID;
  }

  public String getTagText() {
    return tagText;
  }

  public int getBookmarkID() {
    return bookmarkID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Tag tag = (Tag) o;

    return Objects.equal(bookmarkID, tag.bookmarkID) && Objects.equal(tagText, tag.tagText);

  }

  @Override
  public int hashCode() {
    return Objects.hashCode(bookmarkID, tagText);
  }

  @Override
  public String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).toString();
  }

}
