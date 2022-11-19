package local.tin.tests.utils.http.interfaces;

import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpResponseByteArray;

/**
 *
 * @author benitodarder
 * @param <K>
 */
public interface ISimpleHttpClient<K extends IHttpRequest> {
    
    /**
     * Makes a request to the given URL with the given headers.
     *
     * @param httpRequest
     * @return HttpResponseByteArray
     * @throws HttpCommonException
     */
    public HttpResponseByteArray makeRequest(K httpRequest) throws HttpCommonException;
}
