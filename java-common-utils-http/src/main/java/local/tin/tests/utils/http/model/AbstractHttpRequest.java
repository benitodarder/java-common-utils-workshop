package local.tin.tests.utils.http.model;

import java.util.Map;
import java.util.Objects;
import local.tin.tests.utils.http.interfaces.IHttpRequest;

/**
 *
 * @author benitodarder
 */
public abstract class AbstractHttpRequest implements IHttpRequest {
    
    private String urlString;
    private Map<String, String> headers;
    private HttpMethod httpMethod;

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
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.urlString);
        hash = 29 * hash + Objects.hashCode(this.headers);
        hash = 29 * hash + Objects.hashCode(this.httpMethod);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractHttpRequest other = (AbstractHttpRequest) obj;
        if (!Objects.equals(this.urlString, other.urlString)) {
            return false;
        }
        if (!Objects.equals(this.headers, other.headers)) {
            return false;
        }
        if (this.httpMethod != other.httpMethod) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AbstractHttpRequest{" + "urlString=" + urlString + ", headers=" + headers + ", httpMethod=" + httpMethod + '}';
    }
    
    

}
