package local.tin.tests.utils.http.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import local.tin.tests.utils.http.model.HttpCommonException;

/**
 *
 * @author benitodarder
 */
public class URLFactory {

    private URLFactory() {
    }

    public static URLFactory getInstance() {
        return URLFactoryHolder.INSTANCE;
    }

    private static class URLFactoryHolder {
        private static final URLFactory INSTANCE = new URLFactory();
    }
    
     public URL getURLFromString(String urlString) throws HttpCommonException {
        try {
            return new URL(urlString);
        } catch (MalformedURLException ex) {
            throw new HttpCommonException(ex);
        }
    }   
     
     public URLConnection getConnection(String urlSstring) throws HttpCommonException {
        try {
            URL url = getURLFromString(urlSstring);
            return url.openConnection();
        } catch (IOException ex) {
            throw new HttpCommonException(ex);
        }
     }
 }
