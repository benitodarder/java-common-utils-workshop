package local.tin.tests.utils.http.model;

import java.util.Map;
import local.tin.tests.utils.http.interfaces.IHttpRequest;

/**
 *
 * @author benitodarder
 */
public abstract class AbstractHttpRequest implements IHttpRequest {
    
    private String urlString;
    private Map<String, String> headers;
    private HttpMethod httpMethod;
    private boolean tls12Enabled;

    @Override
    public void setURLString(String urlString) {
        this.urlString = urlString;
    }

    @Override
    public String getURLString() {
        return urlString;
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public void setHtppMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public void setTLS12Enabled(boolean tls12Enabled) {
        this.tls12Enabled = tls12Enabled;
    }

    @Override
    public boolean isTLS12Enabled() {
        return tls12Enabled;
    }

    

}
