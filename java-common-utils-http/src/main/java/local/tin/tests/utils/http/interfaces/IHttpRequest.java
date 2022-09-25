package local.tin.tests.utils.http.interfaces;

import java.util.Map;
import local.tin.tests.utils.http.model.HttpMethod;

/**
 *
 * @author benitodarder
 */
public interface IHttpRequest {

    public void setURLString(String urlString);
    
    public String getURLString();
    
    public void setHeaders(Map<String, String> headers);
    
    public Map<String, String> getHeaders();
    
    public void setHtppMethod(HttpMethod httpMethod);
    
    public HttpMethod getHttpMethod();
    
    public void setTLS12Enabled(boolean tls12Enabled);
    
    public boolean isTLS12Enabled();
}
