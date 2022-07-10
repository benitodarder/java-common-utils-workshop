package local.tin.tests.utils.http.model;

/**
 *
 * @author benitodarder
 */
public class HttpClientConfig {

    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final String TLS_1_2 = "TLSv1.2";
    public static final boolean DEFAULT_TLS_1_2_ENABLED = true;
    private boolean tls12Enabled = DEFAULT_TLS_1_2_ENABLED;
    private int bufferSize = DEFAULT_BUFFER_SIZE;

    public HttpClientConfig() {
    }

    public HttpClientConfig(boolean tls12Enabled) {
        this.tls12Enabled = tls12Enabled;
    }
    
     public HttpClientConfig(int bufferSize) {
        this.bufferSize = bufferSize;
    }
    
     public HttpClientConfig(boolean tls12Enabled, int bufferSize) {
        this.tls12Enabled = tls12Enabled;
        this.bufferSize = bufferSize;
    }   

    public boolean isTls12Enabled() {
        return tls12Enabled;
    }

    public int getBufferSize() {
        return bufferSize;
    }
     
     
}
