package local.tin.tests.utils.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpMethod;
import local.tin.tests.utils.http.model.PostRequest;

/**
 *
 * @author benitodarder
 */
public class PostClient extends AbstractClient<PostRequest> {

    @Override
    protected PostRequest prepareRequest(PostRequest httpRequest) throws HttpCommonException  {
        return httpRequest;
    }

    @Override
    protected void writeContent(HttpURLConnection httpURLConnection, PostRequest httpRequest) throws HttpCommonException {
        try (OutputStream httpParameterStream = httpURLConnection.getOutputStream()) {
                httpParameterStream.write(httpRequest.getBody());
                httpParameterStream.flush();            
        } catch (IOException ex) {
           throw new HttpCommonException(ex);
        }   
    }

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

}
