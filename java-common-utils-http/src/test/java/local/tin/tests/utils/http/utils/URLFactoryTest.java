package local.tin.tests.utils.http.utils;

import local.tin.tests.utils.http.model.HttpCommonException;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class URLFactoryTest {
    
    @Test(expected = HttpCommonException.class)
    public void getURLFromString_throws_http_common_execption_when_malformed_url() throws HttpCommonException {
        
        URLFactory.getInstance().getURLFromString("aaaaaaaarrrgggg!!");
        
    }

    @Test
    public void getURLFromString_returns_non_null_url_for_well_formed_url() throws HttpCommonException {
        
        assertNotNull(URLFactory.getInstance().getURLFromString("http://localhost"));
        
    }  
    
    @Test(expected = HttpCommonException.class)
    public void getConnection_throws_http_common_execption_when_open_connection_does() throws HttpCommonException {
        
        URLFactory.getInstance().getConnection("aaaaaaaarrrgggg!!");
        
    }    
}
