package local.tin.tests.utils.http.model;


/**
 *
 * @author benito.darder
 */
public abstract class AbstractHttpResponse {
    
    private final int httpResponseCode;
    private final Object responseAsObject;
    private final String mediaType;

    /**
     * Creates a new http response object with the given values.
     * 
     * @param httpResponseCode int
     * @param responseAsObject Object
     * @param mediaType  String
     */
    protected AbstractHttpResponse(int httpResponseCode, Object responseAsObject, String mediaType) {
        this.httpResponseCode = httpResponseCode;
        this.responseAsObject = responseAsObject;
        this.mediaType = mediaType;
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    protected Object getObjectResponse() {
        return responseAsObject;
    }

    public String getMediaType() {
        return mediaType;
    }
}
