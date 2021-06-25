package local.tin.tests.utils.http;

import java.util.Map;
import local.tin.tests.utils.http.interfaces.IGenericHttpClient;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpResponseByteArray;

/**
 *
 * @author benitodarder
 */
public class GenericHttpClient extends AbstractHttpClient implements IGenericHttpClient {

    /**
     * GenericHttpClient constructor. 
     * 
     * @param tls12Enabled Allows to enable/disable TLS 1.2. 
     */     
    public GenericHttpClient(boolean tls12Enabled) {
        super(tls12Enabled);
    }

    /**
     * GenericHttpClient default constructor. 
     * 
     * TLS 1.2 is disabled.
     */     
    public GenericHttpClient() {
        super();
    }
    
    
    @Override
    public HttpResponseByteArray makeGetRequest(String urlString, Map<String, String> headers) throws HttpCommonException {
        return super.makeGetRequest(urlString, headers);
    }

    @Override
    public HttpResponseByteArray makePostRequest(String urlString, Map<String, String> headers, byte[] body) throws HttpCommonException {
        return super.makePostRequest(urlString, headers, body);
    }


    
    
}
