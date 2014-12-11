package ca.csf.rdore.pinmarks.core;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.Objects;

public class Tag {

  private String name;

  public Tag(String name) {
    setName(name);
  }

  public String getNamet() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
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

    return Objects.equal(name, tag.name);

  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }

  @Override
  public String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).toString();
  }

}
