package ca.csf.rdore.pinmarks.exceptions;

import javax.ws.rs.core.Response.Status;

/**
 * Implementation of StatusType for BadUrlException.
 */
public class BadURLException extends AbstractStatusType{
    
    private static final String BAD_URL_EXCEPTION = "You've entered an invalid url. Please try again.";
    
    public BadURLException(Status httpStatus) { 
        super(httpStatus,  BAD_URL_EXCEPTION);
    }

}