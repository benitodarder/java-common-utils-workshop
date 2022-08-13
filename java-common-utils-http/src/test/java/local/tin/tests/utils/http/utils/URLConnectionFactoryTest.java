package local.tin.tests.utils.http.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import local.tin.tests.utils.http.model.HttpCommonException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
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
@PrepareForTest({URLFactory.class})
public class URLConnectionFactoryTest {

    private static final String SAMPLE_URL = "url";
    private static URLFactory mockedURLFactory;

    @BeforeClass
    public static void setUpClass() {
        mockedURLFactory = mock(URLFactory.class);
    }

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(URLFactory.class);
        when(URLFactory.getInstance()).thenReturn(mockedURLFactory);
    }


    @Test(expected = HttpCommonException.class)
    public void getHttpURLConnection_throws_httpcommonexception_when_urlfactory_does() throws HttpCommonException, IOException {
        when(mockedURLFactory.getURLFromString(SAMPLE_URL)).thenThrow(HttpCommonException.class);

        URLConnectionFactory.getInstance().getHttpURLConnection(SAMPLE_URL);

    }
}
