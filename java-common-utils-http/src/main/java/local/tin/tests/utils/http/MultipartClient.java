package local.tin.tests.utils.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpMethod;
import local.tin.tests.utils.http.model.MultipartItem;
import local.tin.tests.utils.http.model.MultipartRequest;
import local.tin.tests.utils.http.utils.MultipartUtils;

/**
 *
 * @author benitodarder
 */
public class MultipartClient extends AbstractClient<MultipartRequest> {

    public static final String UNEXPECTED_EXCEPTION = "Unexpected Exception: ";
    public static final String HEADER_VALUE_SEPARATOR = ": ";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";

    @Override
    protected MultipartRequest prepareRequest(MultipartRequest httpRequest) throws HttpCommonException {
        String boundary = MultipartUtils.getInstance().getBoundary();
        httpRequest.getHeaders().put(CONTENT_TYPE, httpRequest.getHeaders().get(CONTENT_TYPE) + "; boundary=\"" + boundary + "\"");
        httpRequest.setBoundary(boundary);
        return httpRequest;
    }

    @Override
    protected void writeContent(HttpURLConnection httpURLConnection, MultipartRequest httpRequest) throws HttpCommonException {
        OutputStream httpParameterStream = null;
        try {
            httpURLConnection.setUseCaches(false);
            httpParameterStream = httpURLConnection.getOutputStream();
            httpParameterStream.write(System.lineSeparator().getBytes());
            for (MultipartItem multipartItem : httpRequest.getMultipartItems()) {
                httpParameterStream.write(MultipartUtils.BOUNDARY_END.getBytes());
                httpParameterStream.write(httpRequest.getBoundary().getBytes());
                httpParameterStream.write(System.lineSeparator().getBytes());
                httpParameterStream.write((CONTENT_TYPE + HEADER_VALUE_SEPARATOR + multipartItem.getContentType()).getBytes());
                httpParameterStream.write(System.lineSeparator().getBytes());
                httpParameterStream.write((CONTENT_TRANSFER_ENCODING + HEADER_VALUE_SEPARATOR + multipartItem.getContentTransferEncoding()).getBytes());
                httpParameterStream.write(System.lineSeparator().getBytes());
                httpParameterStream.write((CONTENT_DISPOSITION + HEADER_VALUE_SEPARATOR + multipartItem.getContentDisposition()).getBytes());
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
            httpParameterStream.write(httpRequest.getBoundary().getBytes());
            httpParameterStream.write(MultipartUtils.BOUNDARY_END.getBytes());
            httpParameterStream.write(System.lineSeparator().getBytes());
            httpParameterStream.flush();
        } catch (IOException e) {
            throw new HttpCommonException(UNEXPECTED_EXCEPTION, e);
        } finally {
            if (httpParameterStream != null) {
                try {
                    httpParameterStream.close();
                } catch (IOException ex) {
                    throw new HttpCommonException(ex);
                }
            }
        }
    }

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }
}
