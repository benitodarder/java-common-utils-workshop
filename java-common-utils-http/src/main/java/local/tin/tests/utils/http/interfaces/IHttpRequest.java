package local.tin.tests.utils.http.interfaces;

import java.util.Map;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpProtocol;
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
    
    public HttpProtocol getProtocol() throws HttpCommonException;
}
