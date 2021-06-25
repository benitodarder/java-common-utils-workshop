package local.tin.tests.utils.http.interfaces;

import java.util.Map;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpResponseByteArray;

/**
 *
 * @author benitodarder
 */
public interface IGenericHttpClient {
    
    /**
     * Makes a GET request to the given URL with the given headers.
     *
     * @param urlString String
     * @param headers Map of String/String with header name and value
     * @return HttpResponseByteArray
     * @throws HttpCommonException
     */
    public HttpResponseByteArray makeGetRequest(String urlString, Map<String, String> headers) throws HttpCommonException;

    /**
     * Makes a POST request to the given URL with the given headers, and sets
     * the body with the content of the byte array
     *
     * @param urlString String
     * @param headers Map of String/String with header name and value
     * @param body byte array with content
     * @return HttpResponseByteArray
     * @throws HttpCommonException
     */
    public HttpResponseByteArray makePostRequest(String urlString, Map<String, String> headers, byte[] body) throws HttpCommonException;    
    
}
