package local.tin.tests.utils.http;

import local.tin.tests.utils.http.model.GetRequest;
import local.tin.tests.utils.http.utils.URLConnectionFactory;
import org.junit.Before;

/**
 *
 * @author benitodarder
 */
public class GetClientTest {

    private static URLConnectionFactory mockedURLConnectionFactory;
    private GetRequest request;
    private GetClient client;

    @Before
    public void setUp() {
        client = new GetClient();
        request = new GetRequest();
    }

}
