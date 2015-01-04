package ca.csf.rdore.pinmarks.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.csf.rdore.pinmarks.views.PublicFreemarkerView;

// Inspired from: http://gary-rowe.com/agilestack/2012/10/23/how-to-implement-a-runtimeexceptionmapper-for-dropwizard/
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

  public Response toResponse(RuntimeException exception) {
    // Build default response
    Response defaultResponse =
        Response.serverError().entity(new PublicFreemarkerView("errors/500.ftl")).build();

    // Check for any specific handling
    if (exception instanceof WebApplicationException) {

      return handleWebApplicationException(exception, defaultResponse);
    }

    // Use the default
    LOGGER.error(exception.getMessage(), exception);
    return defaultResponse;
  }

  private Response handleWebApplicationException(RuntimeException exception,
      Response defaultResponse) {
    WebApplicationException webAppException = (WebApplicationException) exception;

    
    if (webAppException.getResponse().getStatus() == 400) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new PublicFreemarkerView("errors/400.ftl")).build();
    }
    
    if (webAppException.getResponse().getStatus() == 401) {
      return Response.status(Response.Status.UNAUTHORIZED)
          .entity(new PublicFreemarkerView("errors/401.ftl")).build();
    }
    
    if (webAppException.getResponse().getStatus() == 403) {
      return Response.status(Response.Status.FORBIDDEN)
          .entity(new PublicFreemarkerView("errors/403.ftl")).build();
    }
    
    if (webAppException.getResponse().getStatus() == 404) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(new PublicFreemarkerView("errors/404.ftl")).build();
    }
    

    // Error logging
    LOGGER.error(exception.getMessage(), exception);

    return defaultResponse;
  }

}
