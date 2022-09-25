package local.tin.tests.utils.http;

import local.tin.tests.utils.http.interfaces.IHttpRequest;
import local.tin.tests.utils.http.interfaces.ISimpleHttpClient;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpResponseByteArray;

/**
 *
 * @author benitodarder
 */
public class GetClient implements ISimpleHttpClient {

    @Override
    public HttpResponseByteArray makeRequest(IHttpRequest httpRequest) throws HttpCommonException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
