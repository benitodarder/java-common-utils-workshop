package local.tin.tests.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.Collection;
import java.util.Map;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpResponseByteArray;
import local.tin.tests.utils.http.model.MultipartItem;
import local.tin.tests.utils.http.utils.MultipartUtils;
import local.tin.tests.utils.http.utils.StreamUtils;
import local.tin.tests.utils.http.utils.URLConnectionFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author benito.darder
 */
public abstract class AbstractHttpClient {

    public static final String TLS_1_2 = "TLSv1.2";
    public static final String CONTENT_ENCODING_COMPRESSED = "gzip";
    public static final int RESPONSE_CODE_SUCCESS = 200;
    public static final int EOF_FLAG = -1;
    public static final int UNKOWN_RESPONSE_CODE = -1;
    public static final String CONTENT_TYPE_NOT_AVAILABLE = "N/A";
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String UNEXPECTED_IO_EXCEPTION_CLOSING_OUTPUT_STR = "Unexpected IOException closing output stream: ";
    public static final String UNEXPECTED_IO_EXCEPTION_CLOSING_PRINT_WRITER = "Unexpected IOException closing PrintWriter: ";
    public static final String UNEXPECTED_IO_EXCEPTION = "Unexpected IOException: ";
    public static final String UNEXPECTED_IO_EXCEPTION_RESPONSE_CODE = "Unexpected IOException retrieving response code after IOException: ";
    public static final String DEFAULT_CHARSET = "UTF-8";
    private static final Logger LOGGER = Logger.getLogger(AbstractHttpClient.class);
    private final boolean tls12Enabled;
    private int bufferSize = DEFAULT_BUFFER_SIZE;

    /**
     * Default AbstractHttpClient constructor. TLS 1.2 is disabled.
     *
     */
    protected AbstractHttpClient() {
        this.tls12Enabled = false;
    }

    /**
     * AbstractHttpClient constructor.
     *
     * @param tls12Enabled Allows to enable/disable TLS 1.2. Remember that
     * default constructor disables TLS 1.2
     *
     */
    protected AbstractHttpClient(boolean tls12Enabled) {
        this.tls12Enabled = tls12Enabled;
    }

    protected int getBufferSize() {
        return bufferSize;
    }

    protected void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    protected Logger getLogger() {
        return LOGGER;
    }

    /**
     * Makes a GET request to the given url with the given headers.
     *
     * @param urlString String
     * @param headers Map of String/String with header name and value
     * @return HttpResponseByteArray
     * @throws HttpCommonException
     */
    protected HttpResponseByteArray makeGetRequest(String urlString, Map<String, String> headers) throws HttpCommonException {
        return makeRequest(urlString, headers, GET_METHOD, null);
    }

    /**
     * Makes a POST request to the given url with the given headers, and sets
     * the body with the content of the byte array
     *
     * @param urlString String
     * @param headers Map of String/String with header name and value
     * @param body byte array with content
     * @return HttpResponseByteArray
     * @throws HttpCommonException
     */
    protected HttpResponseByteArray makePostRequest(String urlString, Map<String, String> headers, byte[] body) throws HttpCommonException {
        return makeRequest(urlString, headers, POST_METHOD, body);
    }

    private HttpResponseByteArray getResponseFromConnection(HttpURLConnection connection) throws HttpCommonException {
        byte[] result = new byte[0];
        InputStream inputStream = null;
        InputStream bodyStream = null;
        int responseCode = UNKOWN_RESPONSE_CODE;
        String contentType = CONTENT_TYPE_NOT_AVAILABLE;
        try {
            inputStream = connection.getInputStream();

            responseCode = connection.getResponseCode();
            contentType = connection.getContentType();
            if (isSuccessfulResponse(responseCode)) {
                bodyStream = getEfectiveResponseStream(connection, inputStream);
            } else {
                bodyStream = getEfectiveResponseStream(connection, connection.getErrorStream());
            }
            result = getStreamAsByteArray(bodyStream);
        } catch (IOException ioe) {
            try {
                responseCode = connection.getResponseCode();
                contentType = connection.getContentType();
                bodyStream = getEfectiveResponseStream(connection, connection.getErrorStream());
                if (bodyStream != null) {
                    result = getStreamAsByteArray(bodyStream);
                } else {
                    result = ioe.getMessage().getBytes();
                }
            } catch (IOException ioee) {
                 logErrorAndDebug(UNEXPECTED_IO_EXCEPTION_CLOSING_OUTPUT_STR, ioee);
            }
        } finally {
            if (inputStream != bodyStream && bodyStream != null) {
                try {
                    bodyStream.close();
                } catch (IOException ex) {
                    logErrorAndDebug(UNEXPECTED_IO_EXCEPTION_CLOSING_OUTPUT_STR, ex);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logErrorAndDebug(UNEXPECTED_IO_EXCEPTION_CLOSING_OUTPUT_STR, ex);
                }
            }
        }
        return new HttpResponseByteArray(responseCode, result, contentType);
    }

    private InputStream getEfectiveResponseStream(HttpURLConnection connection, InputStream inputStream) throws HttpCommonException {
        InputStream bodyStream;
        if (CONTENT_ENCODING_COMPRESSED.equals(connection.getContentEncoding())) {
            bodyStream = StreamUtils.getInstance().getGZIPInputStream(inputStream);
        } else {
            bodyStream = inputStream;
        }
        return bodyStream;
    }

    private boolean isSuccessfulResponse(int responseCode) {
        return responseCode >= 200 && responseCode < 300;
    }

    private HttpURLConnection getConnection(String urlString, Map<String, String> headers, String method) throws HttpCommonException {
        HttpURLConnection httpConnection;
        if (tls12Enabled) {
            httpConnection = URLConnectionFactory.getInstance().getHttpsURLConnectionTLS12(urlString);
        } else {
            httpConnection = URLConnectionFactory.getInstance().getHttpURLConnection(urlString);
        }
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);
        if (headers != null) {
            for (Map.Entry<String, String> current : headers.entrySet()) {
                httpConnection.setRequestProperty(current.getKey(), current.getValue());
            }
        }
        try {
            httpConnection.setRequestMethod(method);
        } catch (ProtocolException pe) {
            throw new HttpCommonException(pe);
        }
        return httpConnection;
    }

    private byte[] getStreamAsByteArray(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[getBufferSize()];
        int n;
        ByteArrayOutputStream output = StreamUtils.getInstance().getByteArrayOutputStream();
        while ((n = inputStream.read(buffer)) != EOF_FLAG) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    private HttpResponseByteArray makeRequest(String urlString, Map<String, String> headers, String method, byte[] body) throws HttpCommonException {
        long t0 = System.currentTimeMillis();
        OutputStream httpParameterStream = null;
        HttpResponseByteArray httResponseByteArray = null;
        HttpURLConnection connection = null;
        try {
            connection = getConnection(urlString, headers, method);

            if (POST_METHOD.equals(method) && body != null) {
                httpParameterStream = connection.getOutputStream();
                httpParameterStream.write(body);
                httpParameterStream.flush();
                httpParameterStream.close();
            }

            httResponseByteArray = getResponseFromConnection(connection);

            LOGGER.debug("'" + urlString + "' response code:" + httResponseByteArray.getHttpResponseCode() + ", content type: " + httResponseByteArray.getMediaType());
            return httResponseByteArray;
        } catch (IOException | HttpCommonException e) {
            logErrorAndDebug(UNEXPECTED_IO_EXCEPTION, e);
            throw new HttpCommonException(e);
        } finally {
            processFinally(httpParameterStream, urlString, method, t0);
        }
    }

    public HttpResponseByteArray makeRequestMultipart(String urlString, Map<String, String> headers, Collection<MultipartItem> multipartItems) throws HttpCommonException {
        long t0 = System.currentTimeMillis();
        OutputStream httpParameterStream = null;
        HttpResponseByteArray httResponseByteArray = null;
        HttpURLConnection connection = null;
        try {
            String boundary = MultipartUtils.getInstance().getBoundary();
            headers.put("Content-Type", headers.get("Content-Type") + "; boundary=\"" + boundary + "\"");
            connection = getConnection(urlString, headers, POST_METHOD);
            connection.setUseCaches(false);
            httpParameterStream = connection.getOutputStream();
            httpParameterStream.write(System.lineSeparator().getBytes());
            for (MultipartItem multipartItem : multipartItems) {
                httpParameterStream.write(MultipartUtils.BOUNDARY_END.getBytes());
                httpParameterStream.write(boundary.getBytes());
                httpParameterStream.write(System.lineSeparator().getBytes());
                httpParameterStream.write(multipartItem.getContentType().getBytes());
                httpParameterStream.write(System.lineSeparator().getBytes());
                httpParameterStream.write(multipartItem.getContentTransferEncoding().getBytes());
                httpParameterStream.write(System.lineSeparator().getBytes());
                httpParameterStream.write(multipartItem.getContentDisposition().getBytes());
                httpParameterStream.write(System.lineSeparator().getBytes());
                if (multipartItem.getFormField() != null) {
                    httpParameterStream.write(System.lineSeparator().getBytes());
                    httpParameterStream.write(multipartItem.getFormField().getBytes());
                    httpParameterStream.write(System.lineSeparator().getBytes());
                } else if (multipartItem.getInputStream() != null) {
                    httpParameterStream.write(System.lineSeparator().getBytes());
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = multipartItem.getInputStream().read(buffer)) != -1) {
                        httpParameterStream.write(buffer, 0, bytesRead);
                    }
                    httpParameterStream.write(System.lineSeparator().getBytes());
                    multipartItem.getInputStream().close();
                }
                httpParameterStream.flush();
            }
            httpParameterStream.write(MultipartUtils.BOUNDARY_END.getBytes());
            httpParameterStream.write(boundary.getBytes());
            httpParameterStream.write(MultipartUtils.BOUNDARY_END.getBytes());
            httpParameterStream.write(System.lineSeparator().getBytes());
            httpParameterStream.flush();
            httResponseByteArray = getResponseFromConnection(connection);
            LOGGER.debug("'" + urlString + "' response code:" + httResponseByteArray.getHttpResponseCode() + ", content type: " + httResponseByteArray.getMediaType());
            return httResponseByteArray;
        } catch (IOException | HttpCommonException e) {
            logErrorAndDebug(UNEXPECTED_IO_EXCEPTION, e);
            throw new HttpCommonException(UNEXPECTED_IO_EXCEPTION, e);
        } finally {
            processFinally(httpParameterStream, urlString, POST_METHOD, t0);
        }
    }

    private void processFinally(OutputStream httpParameterStream, String urlString, String method, long t0) {
        try {
            if (httpParameterStream != null) {
                httpParameterStream.close();
            }
        } catch (IOException e) {
            logErrorAndDebug(UNEXPECTED_IO_EXCEPTION_CLOSING_OUTPUT_STR, e);
        }
        LOGGER.debug(urlString + " " + method + " " + (System.currentTimeMillis() - t0) + "ms");
    }

    private void logErrorAndDebug(String prefix, Exception e) {
        LOGGER.error(prefix + e.getLocalizedMessage());
        LOGGER.debug(prefix + e.getLocalizedMessage(), e);
    }    
}
