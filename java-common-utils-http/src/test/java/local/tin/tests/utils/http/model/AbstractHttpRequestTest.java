package local.tin.tests.utils.http.model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class AbstractHttpRequestTest {
    
    public static final String PROTOCOL = "https";
    public static final String URL = "URL";
    private AbstractHttpRequest request;
    
    @Before
    public void setUp() {
        request = new AbstractHttpRequestWrapper();
    }
    
    @Test
    public void getProtocol_returns_expected_string_for_well_formed_string() {
        request.setURLString(PROTOCOL + AbstractHttpRequest.PROTOCOL_SERVER_SEPARATOR + URL);
        
        assertEquals(HttpProtocol.valueOf(PROTOCOL.toUpperCase()), request.getProtocol());
    }

    @Test(expected = HttpCommonException.class)
    public void getProtocol_throws_exception_when_malformed_url() {
        request.setURLString(PROTOCOL +  URL);

        request.getProtocol();
    }
}

class AbstractHttpRequestWrapper extends AbstractHttpRequest {
    
}