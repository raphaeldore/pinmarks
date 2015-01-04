package ca.csf.rdore.pinmarks.resources;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.csf.rdore.pinmarks.core.Bookmark;
import ca.csf.rdore.pinmarks.daos.BookmarkDAO;
import ca.csf.rdore.pinmarks.util.MiscUtils;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.impl.provider.entity.FormProvider;



@RunWith(MockitoJUnitRunner.class)
public class BookmarkResourceTest {

  private static final BookmarkDAO dao = mock(BookmarkDAO.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new BookmarkResource(dao)).addProvider(FormProvider.class).build();

  Bookmark bookmark;

  @Before
  public void setUp() {
    DateTime dateTime = new DateTime();

    bookmark =
        new Bookmark("Google", "http://www.google.com", "Google Is Nice", new Timestamp(
            dateTime.getMillis()), new ArrayList<String>(), MiscUtils.GenerateRandomSlug());

  }

  @After
  public void tearDown() {
    reset(dao);
  }

  @Test
  public void whenSubmittingValidAddBookmarkForm_thenReturnStatusCreated() throws Exception {

    Form submitedForm = new Form();
    submitedForm.add("title", "Google");
    submitedForm.add("url", "http://www.google.com");
    submitedForm.add("description", "A search engine");
    submitedForm.add("tags", "search");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.CREATED;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/add").type(MediaType.APPLICATION_FORM_URLENCODED)
            .post(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);

  }

  @Test
  public void whenSubmittingFormWithoutTitle_thenReturnBadRequestStatus() {
    Form submitedForm = new Form();
    submitedForm.add("title", "");
    submitedForm.add("url", "http://www.google.com");
    submitedForm.add("description", "A search engine");
    submitedForm.add("tags", "search");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.BAD_REQUEST;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/add").type(MediaType.APPLICATION_FORM_URLENCODED)
            .post(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }

  @Test
  public void whenSubmittingFormWithoutURL_thenReturnBadRequestStatus() {
    Form submitedForm = new Form();
    submitedForm.add("title", "Google");
    submitedForm.add("url", "");
    submitedForm.add("description", "A search engine");
    submitedForm.add("tags", "search");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.BAD_REQUEST;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/add").type(MediaType.APPLICATION_FORM_URLENCODED)
            .post(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }

  @Test
  public void whenSubmittingFormWithInvalidURL_thenReturnBadRequestStatus() {
    Form submitedForm = new Form();
    submitedForm.add("title", "Google");
    submitedForm.add("url", "http://google.comé");
    submitedForm.add("description", "A search engine");
    submitedForm.add("tags", "search");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.BAD_REQUEST;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/add").type(MediaType.APPLICATION_FORM_URLENCODED)
            .post(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }

  @Test
  public void whenSubmittingValidEditedBookmark_thenReturnOkRequestStatus() {
    when(dao.getBookmarkBySlug(anyString())).thenReturn(bookmark);
    when(dao.getBookmarkIDfromSlug(anyString())).thenReturn(1);

    Form submitedForm = new Form();
    submitedForm.add("title", "Google");
    submitedForm.add("url", "http://google.com");
    submitedForm.add("description", "A search engine");
    submitedForm.add("tags", "search");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.OK;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/" + bookmark.getSlug() + "/edit")
            .type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }

  @Test
  public void whenSubmittingInvalidEditedBookmark_thenReturnBadRequestStatus() {
    when(dao.getBookmarkBySlug(anyString())).thenReturn(bookmark);
    when(dao.getBookmarkIDfromSlug(anyString())).thenReturn(1);

    Form submitedForm = new Form();
    submitedForm.add("title", "Google");
    submitedForm.add("url", "http://google.comé");
    submitedForm.add("description", "A search engine");
    submitedForm.add("tags", "search");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.BAD_REQUEST;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/" + bookmark.getSlug() + "/edit")
            .type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }

  @Test
  public void whenEditedBookmarkIsNotFound_thenReturnNotFoundStatus() {
    when(dao.getBookmarkBySlug(anyString())).thenReturn(null);
    when(dao.getBookmarkIDfromSlug(anyString())).thenReturn(1);

    Form submitedForm = new Form();
    submitedForm.add("title", "Google");
    submitedForm.add("url", "http://google.com");
    submitedForm.add("description", "A search engine");
    submitedForm.add("tags", "search");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.NOT_FOUND;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/thisbookmarkdoesnotexist/edit")
            .type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }
  
  @Test
  public void whenDeletingAValidBookmark_thenReturnOkResponseStatus() {
    when(dao.getBookmarkBySlug(anyString())).thenReturn(bookmark);
    
    Form submitedForm = new Form();
    submitedForm.add("bookmarkSlug", bookmark.getSlug());
    
    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.OK;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/delete")
            .type(MediaType.APPLICATION_FORM_URLENCODED).delete(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
    
  }

  @Test
  public void whenDeletingBookmarkWithoutSlug_thenReturnBadRequestStatus() {
    Form submitedForm = new Form();
    submitedForm.add("bookmarkSlug", "");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.BAD_REQUEST;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/delete").type(MediaType.APPLICATION_FORM_URLENCODED)
            .delete(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }

  @Test
  public void whenDeletingNonExistantBookmark_thenReturnNoFoundStatus() {
    Form submitedForm = new Form();
    submitedForm.add("bookmarkSlug", "1243446KJHFKJ");

    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.NOT_FOUND;

    final ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/delete").type(MediaType.APPLICATION_FORM_URLENCODED)
            .delete(ClientResponse.class, submitedForm);

    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }

  @Test()
  public void whenAccessingBookmarkDeletePageWithAGetRequest_thenReturnForbiddenStatus() {
   
    final ClientResponse.Status expectedClientResponseStatus = ClientResponse.Status.FORBIDDEN;
    
    ClientResponse actualClientResponse =
        resources.client().resource("/bookmark/delete").type(MediaType.TEXT_HTML_TYPE)
            .get(ClientResponse.class);
    
    assertThat(actualClientResponse.getStatusInfo()).isEqualTo(expectedClientResponseStatus);
  }

}
