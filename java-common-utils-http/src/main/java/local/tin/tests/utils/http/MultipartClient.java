package local.tin.tests.utils.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import static local.tin.tests.utils.http.AbstractHttpClient.UNEXPECTED_EXCEPTION;
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


    @Override
    protected MultipartRequest prepareRequest(MultipartRequest httpRequest) throws HttpCommonException {
        String boundary = MultipartUtils.getInstance().getBoundary();
        httpRequest.getHeaders().put("Content-Type", httpRequest.getHeaders().get("Content-Type") + "; boundary=\"" + boundary + "\"");
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
        }    }

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }
}
