package ca.csf.rdore.pinmarks.api;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookmarkApi {

  @NotNull
  @JsonProperty
  private final int id;

  @NotEmpty
  @JsonProperty
  private final String title;

  @NotEmpty
  @JsonProperty
  private final String url;

  @JsonProperty
  private final String description;

  @JsonProperty
  private final List<String> tags;

  @NotNull
  @JsonProperty
  private final DateTime dateAdded;

  @JsonCreator
  public BookmarkApi(@JsonProperty("id") int id, @JsonProperty("title") String title,
      @JsonProperty("url") String url, @JsonProperty("description") String description,
      @JsonProperty("tags") List<String> tags, @JsonProperty("dateAdded") DateTime dateAdded) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.description = description;
    this.tags = tags;
    this.dateAdded = dateAdded;

  }

}
