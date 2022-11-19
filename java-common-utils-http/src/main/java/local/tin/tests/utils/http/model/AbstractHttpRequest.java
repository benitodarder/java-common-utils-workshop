package local.tin.tests.utils.http.model;

import java.util.HashMap;
import java.util.Map;
import local.tin.tests.utils.http.interfaces.IHttpRequest;

/**
 *
 * @author benitodarder
 */
public abstract class AbstractHttpRequest implements IHttpRequest {

    public static final String PROTOCOL_SERVER_SEPARATOR = "://";
    private String uRLString;
    private Map<String, String> headers;
    private HttpMethod httpMethod;
    private HttpProtocol protocol;

    @Override
    public void setURLString(String urlString) {
        this.uRLString = urlString;
    }

    @Override
    public String getURLString() {
        return uRLString;
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
        }
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
    public HttpProtocol getProtocol() throws HttpCommonException {
        try {
            return HttpProtocol.valueOf(uRLString.substring(0, uRLString.indexOf(PROTOCOL_SERVER_SEPARATOR)).toUpperCase());
        } catch (IndexOutOfBoundsException | NullPointerException ex) {
            throw new HttpCommonException(ex);
        }
    }

}
