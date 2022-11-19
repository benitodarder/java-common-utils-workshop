package local.tin.tests.utils.http.model;

/**
 *
 * @author benitodarder
 */
public enum HttpProtocol {
    
    HTTP("http"),
    HTTPS("https");
    
    public final String protocol;

    private HttpProtocol(String protocol) {
        this.protocol = protocol;
    }    
}
