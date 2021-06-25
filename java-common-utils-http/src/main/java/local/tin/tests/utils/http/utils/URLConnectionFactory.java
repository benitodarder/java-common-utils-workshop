package local.tin.tests.utils.http.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import local.tin.tests.utils.http.model.HttpCommonException;

/**
 *
 * @author benitodarder
 */
public class URLConnectionFactory {
    
    public static final String TLS_1_2 = "TLSv1.2";
    
    private URLConnectionFactory() {
    }
    
    public static URLConnectionFactory getInstance() {
        return URLConnectionFactoryHolder.INSTANCE;
    }
    
    private static class URLConnectionFactoryHolder {

        private static final URLConnectionFactory INSTANCE = new URLConnectionFactory();
    }
    
    public HttpURLConnection getHttpURLConnection(String urlString) throws HttpCommonException {
        HttpURLConnection httpURLConnection;
        try {
            URL url = getURLFromString(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            throw new HttpCommonException(ex);
        }
        return httpURLConnection;
    }

    private URL getURLFromString(String urlString) throws MalformedURLException {
        return new URL(urlString);
    }

    public HttpsURLConnection getHttpsURLConnectionTLS12(String urlString) throws HttpCommonException {
        HttpsURLConnection httpsURLConnection;
        try {
            URL url = getURLFromString(urlString);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            SSLContext sslContext = SSLContext.getInstance(TLS_1_2);
            sslContext.init(null, null, new SecureRandom());
            httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
        } catch (KeyManagementException | NoSuchAlgorithmException | IOException ex) {
            throw new HttpCommonException(ex);
        }
        return httpsURLConnection;
    }
    
}
