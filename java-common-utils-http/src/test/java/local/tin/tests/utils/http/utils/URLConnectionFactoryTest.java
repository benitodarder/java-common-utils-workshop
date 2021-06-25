package local.tin.tests.utils.http.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import local.tin.tests.utils.http.model.HttpCommonException;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author tubdapmi
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({URL.class, URLConnectionFactory.class})
public class URLConnectionFactoryTest {

    private static final String SAMPLE_URL = "url";
    private URL mockedURL;
    private URLConnection mockedURLConnection;
    private HttpURLConnection mockedHttpURLConnection;
    private HttpsURLConnection mockedHttpsURLConnection;
    
    @Before
    public void setUp() throws Exception {
        mockedURL = PowerMockito.mock(URL.class);
        PowerMockito.whenNew(URL.class).withParameterTypes(String.class).withArguments(SAMPLE_URL).thenReturn(mockedURL);
        mockedHttpURLConnection = mock(HttpURLConnection.class);
        when(mockedURL.openConnection()).thenReturn(mockedHttpURLConnection);     
    }

    @Test
    public void getHttpURLConnection_opens_connection() throws HttpCommonException {

        HttpURLConnection result = URLConnectionFactory.getInstance().getHttpURLConnection(SAMPLE_URL);
        
        assertThat(result, equalTo(mockedHttpURLConnection));
    }

    @Test(expected=HttpCommonException.class)
    public void getHttpURLConnection_throws_httpcommonexception_when_can_not_open_connection() throws HttpCommonException, IOException {
        when(mockedURL.openConnection()).thenThrow(IOException.class);
        
        HttpURLConnection result = URLConnectionFactory.getInstance().getHttpURLConnection(SAMPLE_URL);
        
    }    
}
