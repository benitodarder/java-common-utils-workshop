package local.tin.tests.utils.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import local.tin.tests.utils.http.model.GetRequest;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpMethod;
import local.tin.tests.utils.http.model.HttpProtocol;
import local.tin.tests.utils.http.model.HttpResponseByteArray;
import local.tin.tests.utils.http.model.PostRequest;
import local.tin.tests.utils.http.utils.StreamUtils;
import local.tin.tests.utils.http.utils.URLConnectionFactory;
import local.tin.tests.utils.http.utils.URLFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author benitodarder
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({URLFactory.class, StreamUtils.class})
public class PostClientTest {

    public static final String CONTENT_TYPE = "Content type";
    public static final int SAMPLE_RESPONSE_CODE = 200;
    public static final String SAMPLE_URL = "sample url";
    public static final String HEADER_NAME_A = "header a";
    public static final String HEADER_VALUE_A = "value a";
    public static final String HEADER_NAME_B = "header b";
    public static final String HEADER_VALUE_B = "value b";
    public static final byte[] SAMPLE_BYTE_ARRAY = {1, 3, 7, 15, 31, 63, 127};
    private static URLFactory mockedURLConnectionFactory;
    private static StreamUtils mockedStreamUtils;
    private PostRequest request;
    private PostClient client;
    private HttpsURLConnection mockedHttpsURLConnection;
    private Map<String, String> headers;
    private InputStream mockedInputStream;
    private GZIPInputStream mockedGZIPInputStream;
    private OutputStream mockedOutputStream;

    @BeforeClass
    public static void setUpClass() {
        mockedURLConnectionFactory = mock(URLFactory.class);
        mockedStreamUtils = mock(StreamUtils.class);

    }

    @Before
    public void setUp() throws HttpCommonException, IOException {
        PowerMockito.mockStatic(URLFactory.class);
        when(URLFactory.getInstance()).thenReturn(mockedURLConnectionFactory);
        PowerMockito.mockStatic(StreamUtils.class);
        when(StreamUtils.getInstance()).thenReturn(mockedStreamUtils);
        reset(mockedURLConnectionFactory);
        client = new PostClient();
        request = new PostRequest();
        request.setURLString(SAMPLE_URL);
        mockedHttpsURLConnection = mock(HttpsURLConnection.class);
        when(mockedURLConnectionFactory.getConnection(SAMPLE_URL)).thenReturn(mockedHttpsURLConnection);
        request.setHtppMethod(HttpMethod.POST);
        mockedInputStream = mock(InputStream.class);
        when(mockedHttpsURLConnection.getInputStream()).thenReturn(mockedInputStream);
        when(mockedInputStream.read()).thenReturn(GetClient.EOF_FLAG);
        when(mockedHttpsURLConnection.getResponseCode()).thenReturn(SAMPLE_RESPONSE_CODE);
        mockedGZIPInputStream = mock(GZIPInputStream.class);
        mockedOutputStream = mock(OutputStream.class);
        when(mockedHttpsURLConnection.getOutputStream()).thenReturn(mockedOutputStream);
        request.setBody(SAMPLE_BYTE_ARRAY);
    }

    @Test
    public void makeRequest_gets_connection() throws HttpCommonException {

        client.makeRequest(request);

        verify(mockedURLConnectionFactory).getConnection(SAMPLE_URL);

    }

    @Test
    public void makeRequest_sets_input_output() throws HttpCommonException {

        client.makeRequest(request);

        verify(mockedHttpsURLConnection).setDoInput(true);
        verify(mockedHttpsURLConnection).setDoOutput(true);
    }

    @Test
    public void makeRequest_assigns_headers() throws HttpCommonException {
        headers = new HashMap<>();
        headers.put(HEADER_NAME_A, HEADER_VALUE_A);
        headers.put(HEADER_NAME_B, HEADER_VALUE_B);
        request.setHeaders(headers);

        client.makeRequest(request);

        for (Map.Entry<String, String> current : headers.entrySet()) {
            verify(mockedHttpsURLConnection).setRequestProperty(current.getKey(), current.getValue());
        }
    }

    @Test
    public void makeRequest_assigns_method() throws HttpCommonException, ProtocolException {

        client.makeRequest(request);

        verify(mockedHttpsURLConnection).setRequestMethod(request.getHttpMethod().name());
    }

    @Test
    public void makeRequest_assigns_method_to_unsecure_connection() throws HttpCommonException, ProtocolException, IOException {
        HttpURLConnection mockedHttpURLConnection = mock(HttpURLConnection.class);
        when(mockedHttpURLConnection.getResponseCode()).thenReturn(SAMPLE_RESPONSE_CODE);
        when(mockedURLConnectionFactory.getConnection(SAMPLE_URL)).thenReturn(mockedHttpURLConnection);
        when(mockedHttpURLConnection.getInputStream()).thenReturn(mockedInputStream);
        when(mockedHttpURLConnection.getOutputStream()).thenReturn(mockedOutputStream);

        client.makeRequest(request);

        verify(mockedHttpURLConnection).setRequestMethod(HttpMethod.POST.name());
    }

    @Test
    public void makeRequest_assigns_method_to_secure_connection() throws HttpCommonException, ProtocolException {

        client.makeRequest(request);

        verify(mockedHttpsURLConnection).setRequestMethod(request.getHttpMethod().name());
    }

    @Test(expected = HttpCommonException.class)
    public void makeRequest_throws_httpcommonexception_when_protocol_exception() throws HttpCommonException, ProtocolException {
        PowerMockito.doThrow(new ProtocolException()).when(mockedHttpsURLConnection).setRequestMethod(anyString());

        client.makeRequest(request);

    }

    @Test
    public void makeRequest_reads_from_connection() throws HttpCommonException, IOException {

        client.makeRequest(request);

        verify(mockedHttpsURLConnection).getInputStream();
    }

    @Test(expected = HttpCommonException.class)
    public void makeRequest_throws_httpcommonexception_when_ioexception_from_inputstream() throws HttpCommonException, IOException {
        when(mockedHttpsURLConnection.getInputStream()).thenThrow(IOException.class);

        client.makeRequest(request);

    }

    @Test
    public void makeRequest_returns_response_code() throws HttpCommonException, IOException {
        when(mockedHttpsURLConnection.getInputStream()).thenReturn(mockedInputStream);

        HttpResponseByteArray response = client.makeRequest(request);

        assertEquals(SAMPLE_RESPONSE_CODE, response.getHttpResponseCode());
    }

    @Test
    public void makeRequest_returns_response_media_type() throws HttpCommonException, IOException {
        when(mockedHttpsURLConnection.getInputStream()).thenReturn(mockedInputStream);
        when(mockedHttpsURLConnection.getContentType()).thenReturn(CONTENT_TYPE);

        HttpResponseByteArray response = client.makeRequest(request);

        assertEquals(CONTENT_TYPE, response.getContentType());
    }

    @Test
    public void makeRequest_returns_connection_stream() throws IOException, HttpCommonException {
        when(mockedInputStream.read())
                .thenReturn((int) SAMPLE_BYTE_ARRAY[0])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[1])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[2])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[3])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[4])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[5])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[6])
                .thenReturn(GetClient.EOF_FLAG);

        HttpResponseByteArray response = client.makeRequest(request);

        assertTrue(Arrays.equals(SAMPLE_BYTE_ARRAY, response.getResponseAsByteArray()));
    }

    @Test
    public void makeRequest_returns_connection_error_stream_when_not_success_response_code() throws IOException, HttpCommonException {
        when(mockedHttpsURLConnection.getInputStream()).thenReturn(null);
        when(mockedHttpsURLConnection.getErrorStream()).thenReturn(mockedInputStream);
        when(mockedHttpsURLConnection.getResponseCode()).thenReturn(GetClient.LAST_SUCCESS_CODE + 1);
        when(mockedInputStream.read())
                .thenReturn((int) SAMPLE_BYTE_ARRAY[0])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[1])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[2])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[3])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[4])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[5])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[6])
                .thenReturn(GetClient.EOF_FLAG);

        HttpResponseByteArray response = client.makeRequest(request);

        assertTrue(Arrays.equals(SAMPLE_BYTE_ARRAY, response.getResponseAsByteArray()));
    }

    @Test(expected = HttpCommonException.class)
    public void makeRequest_throws_httpcommonexception_when_closing_inputstream_throws_ioexception() throws IOException, HttpCommonException {
        when(mockedInputStream.read()).thenReturn(GetClient.EOF_FLAG);
        PowerMockito.doThrow(new IOException()).when(mockedInputStream).close();

        HttpResponseByteArray response = client.makeRequest(request);

    }

    @Test
    public void makeRequest_returns_compressed_connection_stream() throws IOException, HttpCommonException {
        when(mockedHttpsURLConnection.getContentEncoding()).thenReturn(GetClient.CONTENT_ENCODING_COMPRESSED);
        when(mockedStreamUtils.getGZIPInputStream(mockedInputStream)).thenReturn(mockedGZIPInputStream);
        when(mockedGZIPInputStream.read())
                .thenReturn((int) SAMPLE_BYTE_ARRAY[0])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[1])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[2])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[3])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[4])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[5])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[6])
                .thenReturn(GetClient.EOF_FLAG);

        HttpResponseByteArray response = client.makeRequest(request);

        verify(mockedStreamUtils).getGZIPInputStream(mockedInputStream);
    }

    @Test
    public void makeRequest_returns_compressed_connection_error_stream_when_not_success_response_code() throws IOException, HttpCommonException {
        when(mockedHttpsURLConnection.getInputStream()).thenReturn(null);
        when(mockedHttpsURLConnection.getErrorStream()).thenReturn(mockedInputStream);
        when(mockedHttpsURLConnection.getResponseCode()).thenReturn(GetClient.LAST_SUCCESS_CODE + 1);
        when(mockedHttpsURLConnection.getContentEncoding()).thenReturn(GetClient.CONTENT_ENCODING_COMPRESSED);
        when(mockedStreamUtils.getGZIPInputStream(mockedInputStream)).thenReturn(mockedGZIPInputStream);
        when(mockedGZIPInputStream.read())
                .thenReturn((int) SAMPLE_BYTE_ARRAY[0])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[1])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[2])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[3])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[4])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[5])
                .thenReturn((int) SAMPLE_BYTE_ARRAY[6])
                .thenReturn(GetClient.EOF_FLAG);

        HttpResponseByteArray response = client.makeRequest(request);

        verify(mockedStreamUtils).getGZIPInputStream(mockedInputStream);
    }

    @Test
    public void makeRequest_posts_body() throws HttpCommonException, IOException {

        client.makeRequest(request);

        verify(mockedOutputStream).write(SAMPLE_BYTE_ARRAY);
        verify(mockedOutputStream).flush();
    }
}
