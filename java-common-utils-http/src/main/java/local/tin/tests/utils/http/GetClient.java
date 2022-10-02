package local.tin.tests.utils.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import local.tin.tests.utils.http.interfaces.IHttpRequest;
import local.tin.tests.utils.http.interfaces.ISimpleHttpClient;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpMethod;
import local.tin.tests.utils.http.model.HttpResponseByteArray;
import local.tin.tests.utils.http.utils.StreamUtils;
import local.tin.tests.utils.http.utils.URLFactory;

/**
 *
 * @author benitodarder
 */
public class GetClient implements ISimpleHttpClient {

    public static final String CONTENT_ENCODING_COMPRESSED = "gzip";
    public static final int FIRST_SUCCESS_CODE = 200;
    public static final int LAST_SUCCESS_CODE = 299;
    public static final int EOF_FLAG = -1;

    @Override
    public HttpResponseByteArray makeRequest(IHttpRequest httpRequest) throws HttpCommonException {
        URLConnection uRLConnection = URLFactory.getInstance().getConnection(httpRequest.getURLString());
        uRLConnection.setDoInput(true);
        uRLConnection.setDoOutput(true);
        for (Map.Entry<String, String> current : httpRequest.getHeaders().entrySet()) {
            uRLConnection.setRequestProperty(current.getKey(), current.getValue());
        }
        try {
            getHttpURLConnection(uRLConnection).setRequestMethod(HttpMethod.GET.name());
        } catch (ProtocolException ex) {
            throw new HttpCommonException(ex);
        }
        HttpResponseByteArray httpResponseByteArray = new HttpResponseByteArray();
        InputStream effectiveStream = null;
        try {
            httpResponseByteArray.setHttpResponseCode(getHttpURLConnection(uRLConnection).getResponseCode());
            if (httpResponseByteArray.getHttpResponseCode() >= FIRST_SUCCESS_CODE && httpResponseByteArray.getHttpResponseCode() <= LAST_SUCCESS_CODE) {
                effectiveStream = uRLConnection.getInputStream();
            } else {
                effectiveStream = getHttpURLConnection(uRLConnection).getErrorStream();
            }
            if (CONTENT_ENCODING_COMPRESSED.equals(getHttpURLConnection(uRLConnection).getContentEncoding())) {
                effectiveStream = StreamUtils.getInstance().getGZIPInputStream(effectiveStream);
            }
            httpResponseByteArray.setContentType(getHttpURLConnection(uRLConnection).getContentType());
            List<Byte> bytesList = new ArrayList<>();
            for (int current = effectiveStream.read(); current != EOF_FLAG; current = effectiveStream.read()) {
                bytesList.add((byte) current);
            }
            byte[] byteArray = new byte[bytesList.size()];
            for (int i = 0; i < bytesList.size(); i++) {
                byteArray[i] = bytesList.get(i);
            }
            httpResponseByteArray.setResponseBody(byteArray);
        } catch (IOException ex) {
            throw new HttpCommonException(ex);
        } finally {
            if (effectiveStream != null) {
                try {
                    effectiveStream.close();
                } catch (IOException ex) {
                    throw new HttpCommonException(ex);
                }
            }
        }
        return httpResponseByteArray;
    }

    private HttpURLConnection getHttpURLConnection(URLConnection uRLConnection) {
        return (HttpURLConnection) uRLConnection;
    }

}
