package local.tin.tests.utils.http.model;

import java.io.Serializable;


/**
 *
 * @author benito.darder
 */
public abstract class AbstractHttpResponse {
    
    private int httpResponseCode;
    private Serializable responseBody;
    private String contentType;

    public AbstractHttpResponse() {
    }
    
    /**
     * Creates a new http response object with the given values.
     * 
     * @param httpResponseCode int
     * @param responseAsObject Object
     * @param mediaType  String
     */
    protected AbstractHttpResponse(int httpResponseCode, Serializable responseAsObject, String mediaType) {
        this.httpResponseCode = httpResponseCode;
        this.responseBody = responseAsObject;
        this.contentType = mediaType;
    }

    public int getHttResponseCode() {
        return httpResponseCode;
    }

    protected Serializable getResponseBody() {
        return responseBody;
    }

    public String getContentType() {
        return contentType;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public void setResponseBody(Serializable responseAsObject) {
        this.responseBody = responseAsObject;
    }

    public void setContentType(String mediaType) {
        this.contentType = mediaType;
    }
    
    
}
