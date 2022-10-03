package local.tin.tests.utils.http;

import java.net.HttpURLConnection;
import local.tin.tests.utils.http.model.GetRequest;
import local.tin.tests.utils.http.model.HttpMethod;

/**
 *
 * @author benitodarder
 */
public class GetClient extends AbstractClient<GetRequest> {

    @Override
    protected GetRequest prepareRequest(GetRequest httpRequest) {
        return httpRequest;
    }

    @Override
    protected void writeContent(HttpURLConnection httpURLConnection, GetRequest httpRequest) {
    }

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

}
