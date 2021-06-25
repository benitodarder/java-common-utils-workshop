package local.tin.tests.utils.http.model;


public class HttpResponseByteArray extends AbstractHttpResponse {
    
    public HttpResponseByteArray(int httpResponseCode, byte[] responseAsObject, String mediaType) {
        super(httpResponseCode, responseAsObject, mediaType);
    }

    /**
     * Returns the response as byte[]
     * 
     * @return byte array
     */
    public byte[] getResponseAsByteArray() {
        return (byte[]) super.getObjectResponse(); 
    }
    
    
}