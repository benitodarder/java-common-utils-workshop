package local.tin.tests.utils.http;

    
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpResponseByteArray;
import local.tin.tests.utils.http.utils.URLConnectionFactory;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author benito.darder
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AbstractHttpClientWrapper.class, URLConnectionFactory.class, Logger.class, GZIPInputStream.class})
public class GenericHttpClientTest {

    private static final long SAMPLE_TIMESTAMP_MARKER = 1l;
    private static final int DELTA = 3;
    private static final String CONTENT_TYPE = "content type";
    private static final String SAMPLE_HEADER_VALUE = "value";
    private static final String SAMPLE_HEADER_KEY = "key";
    private static final String SAMPLE_URL = "url";
    private static Logger mockedLogger;
    private static final byte[] SAMPLE_BYTE_ARRAY = {1, 3, 7, 15, 31, 63, 127};
    private static final byte[] ANOTHER_SAMPLE_BYTE_ARRAY = {2, 4, 8, 16, 32, 64};
    private static URLConnectionFactory mockedURLConnectionFactory;
    private GenericHttpClient client;
    private InputStream mockedInputStream;
    private ByteArrayOutputStream mockedByteArrayOutputStream;
    private OutputStream mockedOutputStream;
    private HttpURLConnection mockedHttpURLConnection;
    private HttpsURLConnection mockedHttpsURLConnection;

    @BeforeClass
    public static void setUpClass() {
        mockedLogger = mock(Logger.class);
        mockedURLConnectionFactory = mock(URLConnectionFactory.class);
    }

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Logger.class);
        when(Logger.getLogger(AbstractHttpClient.class)).thenReturn(mockedLogger);
        PowerMockito.mockStatic(URLConnectionFactory.class);
        when(URLConnectionFactory.getInstance()).thenReturn(mockedURLConnectionFactory);
        client = new GenericHttpClient();
        mockedHttpURLConnection = mock(HttpURLConnection.class);
        when(mockedURLConnectionFactory.getHttpURLConnection(SAMPLE_URL)).thenReturn(mockedHttpURLConnection);
        when(mockedHttpURLConnection.getResponseCode()).thenReturn(AbstractHttpClient.RESPONSE_CODE_SUCCESS);
        mockedHttpsURLConnection = mock(HttpsURLConnection.class);
        when(mockedURLConnectionFactory.getHttpsURLConnectionTLS12(SAMPLE_URL)).thenReturn(mockedHttpsURLConnection);
        when(mockedHttpsURLConnection.getResponseCode()).thenReturn(AbstractHttpClient.RESPONSE_CODE_SUCCESS);
    }

    @Test
    public void makeGetRequest_reads_contents_from_given_url() throws Exception {
        setInputStreamMocks();

        client.makeGetRequest(SAMPLE_URL, null);

        verify(mockedInputStream).read(any(byte[].class));
    }

    @Test
    public void makeGetRequest_assigns_headers_from_map() throws Exception {
        setInputStreamMocks();
        Map<String, String> headers = new HashMap<>();
        headers.put(SAMPLE_HEADER_KEY, SAMPLE_HEADER_VALUE);

        client.makeGetRequest(SAMPLE_URL, headers);

        verify(mockedHttpURLConnection).setRequestProperty(SAMPLE_HEADER_KEY, SAMPLE_HEADER_VALUE);
    }

    @Test
    public void makeGetRequest_returns_expected_httpresponsebytearray() throws Exception {
        setInputStreamMocks();
        setByteArrayOutputStreamMocks();

        HttpResponseByteArray result = client.makeGetRequest(SAMPLE_URL, null);

        assertThat(result.getHttpResponseCode(), equalTo(AbstractHttpClient.RESPONSE_CODE_SUCCESS));
        assertThat(result.getResponseAsByteArray(), equalTo(SAMPLE_BYTE_ARRAY));
        assertThat(result.getMediaType(), equalTo(CONTENT_TYPE));
    }

    private void setByteArrayOutputStreamMocks() throws Exception {
        mockedByteArrayOutputStream = mock(ByteArrayOutputStream.class);
        PowerMockito.whenNew(ByteArrayOutputStream.class).withNoArguments().thenReturn(mockedByteArrayOutputStream);
        when(mockedByteArrayOutputStream.toByteArray()).thenReturn(SAMPLE_BYTE_ARRAY);
    }

    @Test
    public void makeGetRequest_reads_errorstream_when_response_is_not_success() throws Exception {
        setErrorInputStreamMocks();
        setByteArrayOutputStreamMocks();

        HttpResponseByteArray result = client.makeGetRequest(SAMPLE_URL, null);

        verify(mockedHttpURLConnection).getErrorStream();
    }

    @Test
    public void makePostRequest_reads_contents_from_given_url() throws Exception {
        setInputStreamMocks();

        client.makePostRequest(SAMPLE_URL, null, null);

        verify(mockedInputStream).read(any(byte[].class));
    }

    @Test
    public void makePostRequest_assigns_headers_from_map() throws Exception {
        setInputStreamMocks();
        Map<String, String> headers = new HashMap<>();
        headers.put(SAMPLE_HEADER_KEY, SAMPLE_HEADER_VALUE);

        client.makePostRequest(SAMPLE_URL, headers, null);

        verify(mockedHttpURLConnection).setRequestProperty(SAMPLE_HEADER_KEY, SAMPLE_HEADER_VALUE);
    }

    @Test
    public void makePostRequest_returns_expected_httpresponsebytearray() throws Exception {
        setInputStreamMocks();
        setByteArrayOutputStreamMocks();

        HttpResponseByteArray result = client.makePostRequest(SAMPLE_URL, null, null);

        assertThat(result.getHttpResponseCode(), equalTo(AbstractHttpClient.RESPONSE_CODE_SUCCESS));
        assertThat(result.getResponseAsByteArray(), equalTo(SAMPLE_BYTE_ARRAY));
        assertThat(result.getMediaType(), equalTo(CONTENT_TYPE));
    }

    @Test
    public void makePostRequest_reads_errorstream_when_response_is_not_success() throws Exception {
        setErrorInputStreamMocks();
        setByteArrayOutputStreamMocks();

        HttpResponseByteArray result = client.makePostRequest(SAMPLE_URL, null, null);

        verify(mockedHttpURLConnection).getErrorStream();
    }

    @Test
    public void makePostRequest_writes_the_body_to_the_conneciton() throws Exception {
        setInputStreamMocks();
        setByteArrayOutputStreamMocks();
        setOutputStreamMocks();

        HttpResponseByteArray result = client.makePostRequest(SAMPLE_URL, null, ANOTHER_SAMPLE_BYTE_ARRAY);

        verify(mockedOutputStream).write(ANOTHER_SAMPLE_BYTE_ARRAY);;
    }

    @Test
    public void makePostRequest_closes_input_stream() throws Exception {
        setInputStreamMocks();

        client.makePostRequest(SAMPLE_URL, null, null);

        verify(mockedInputStream).close();
    }

    @Test
    public void makeGestRequest_closes_input_stream() throws Exception {
        setInputStreamMocks();

        client.makeGetRequest(SAMPLE_URL, null);

        verify(mockedInputStream).close();
    }

    @Test
    public void makePostRequest_closes_body_stream() throws Exception {
        setInputStreamMocks();
        setByteArrayOutputStreamMocks();
        setOutputStreamMocks();

        HttpResponseByteArray result = client.makePostRequest(SAMPLE_URL, null, ANOTHER_SAMPLE_BYTE_ARRAY);

        verify(mockedOutputStream, atLeastOnce()).close();
    }

    @Test
    public void makePostRequest_logs_debug_mode() throws Exception {
        setInputStreamMocks();
        setByteArrayOutputStreamMocks();
        setOutputStreamMocks();

        HttpResponseByteArray result = client.makePostRequest(SAMPLE_URL, null, ANOTHER_SAMPLE_BYTE_ARRAY);

        verify(mockedLogger, atLeastOnce()).debug(anyString());
    }

    @Test
    public void makeGetRequest_logs_debug_mode() throws Exception {
        setInputStreamMocks();
        setByteArrayOutputStreamMocks();

        HttpResponseByteArray result = client.makeGetRequest(SAMPLE_URL, null);

        verify(mockedLogger, atLeastOnce()).debug(anyString());
    }

    @Test
    public void makeGetRequest_expands_compressed_response() throws Exception {
        when(mockedHttpURLConnection.getContentEncoding()).thenReturn(AbstractHttpClient.CONTENT_ENCODING_COMPRESSED);
        setInputStreamMocks();
        GZIPInputStream mockedGZIPInputStream = mock(GZIPInputStream.class);
        PowerMockito.whenNew(GZIPInputStream.class).withArguments(mockedInputStream).thenReturn(mockedGZIPInputStream);
        when(mockedGZIPInputStream.read(any(byte[].class))).thenReturn(AbstractHttpClient.EOF_FLAG);

        client.makeGetRequest(SAMPLE_URL, null);

        verify(mockedGZIPInputStream).read(any(byte[].class));
    }

    @Test
    public void tsl_1_2_used_when_configured() throws IOException, HttpCommonException, Exception {
        client = new GenericHttpClient(true);
        setInputStreamMocks();
        setByteArrayOutputStreamMocks();

        client.makeGetRequest(SAMPLE_URL, null);

        verify(mockedURLConnectionFactory).getHttpsURLConnectionTLS12(SAMPLE_URL);
    }

    private void setOutputStreamMocks() throws IOException {
        mockedOutputStream = mock(OutputStream.class);
        when(mockedHttpURLConnection.getOutputStream()).thenReturn(mockedOutputStream);
    }

    private void setErrorInputStreamMocks() throws IOException {
        when(mockedHttpURLConnection.getResponseCode()).thenReturn(AbstractHttpClient.RESPONSE_CODE_SUCCESS + AbstractHttpClient.RESPONSE_CODE_SUCCESS);
        mockedInputStream = mock(InputStream.class);
        when(mockedHttpURLConnection.getErrorStream()).thenReturn(mockedInputStream);
        when(mockedInputStream.read(any(byte[].class))).thenReturn(AbstractHttpClient.EOF_FLAG);
        when(mockedHttpURLConnection.getContentType()).thenReturn(CONTENT_TYPE);
    }

    private void setInputStreamMocks() throws IOException {
        mockedInputStream = mock(InputStream.class);
        when(mockedHttpURLConnection.getInputStream()).thenReturn(mockedInputStream);
        when(mockedHttpsURLConnection.getInputStream()).thenReturn(mockedInputStream);
        when(mockedInputStream.read(any(byte[].class))).thenReturn(AbstractHttpClient.EOF_FLAG);
        when(mockedHttpURLConnection.getContentType()).thenReturn(CONTENT_TYPE);
        when(mockedHttpsURLConnection.getContentType()).thenReturn(CONTENT_TYPE);
    }
    
    
}
