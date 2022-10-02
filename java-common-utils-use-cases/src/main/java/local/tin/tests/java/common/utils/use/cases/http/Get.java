package local.tin.tests.java.common.utils.use.cases.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import local.tin.tests.utils.file.FileUtils;
import local.tin.tests.utils.http.GenericHttpClient;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpResponseByteArray;
import java.util.logging.Level;
import java.util.logging.Logger;
import local.tin.tests.utils.http.model.GetRequest;
import local.tin.tests.utils.http.model.HttpProtocol;

/**
 *
 * @author benitodarder
 */
public class Get {

    public static final String PROPERTY_NAME_METHOD = "method";
    public static final String PROPERTY_NAME_URL = "url";
    public static final String PROPERTY_NAME_BODY = "body";
    public static final String PROPERTY_NAME_TLS_12 = "tls12";
    public static final String PROPERTY_NAME_HEADERS = "headers";
    public static final String PROPERTY_NAME_PROTOCOL = "protocol";
    private static final Logger LOGGER = Logger.getLogger(Get.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, HttpCommonException {
        if (args.length != 1) {
            LOGGER.log(Level.SEVERE, "Usage: java -cp java-common-utils-use-cases.1.0-jar-with-dependencies.jar  local.tin.tests.java.common.utils.use.cases.http.HttpClient <Properties file>");
            LOGGER.log(Level.SEVERE, "Properties file:");
            LOGGER.log(Level.SEVERE, "\turl=<URL>");
            LOGGER.log(Level.SEVERE, "\tbody=<Single line>");
            LOGGER.log(Level.SEVERE, "\theaders=<Comma separated pairs of header name and value");
            System.exit(1);
        } else {
            GetRequest getRequest = new GetRequest();
            Properties properties = FileUtils.getInstance().getPropertiesFile(args[0]);
            Map<String, String> headers = new HashMap<>();
            if (properties.getProperty(PROPERTY_NAME_HEADERS) != null) {
                String[] headersArray = properties.getProperty(PROPERTY_NAME_HEADERS).split(",");
                for (int i = 0; i < (headersArray.length - 1); i++) {
                    getRequest.getHeaders().put(headersArray[i], headersArray[i + 1]);
                }
            }
            getRequest.setURLString(properties.getProperty(PROPERTY_NAME_URL));
            local.tin.tests.utils.http.GetClient getClient = new local.tin.tests.utils.http.GetClient();
            HttpResponseByteArray httpResponseByteArray = getClient.makeRequest(getRequest);
            LOGGER.log(Level.INFO, "Response: {0}", new String(httpResponseByteArray.getResponseAsByteArray()));
            LOGGER.log(Level.INFO, "Response Http code: {0}", httpResponseByteArray.getHttpResponseCode());
        }
    }

}
