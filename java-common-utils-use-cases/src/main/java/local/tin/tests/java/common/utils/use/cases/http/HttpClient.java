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

/**
 *
 * @author benitodarder
 */
public class HttpClient {

    public static final String PROPERTY_NAME_METHOD = "method";
    public static final String PROPERTY_NAME_URL = "url";
    public static final String PROPERTY_NAME_BODY = "body";
    public static final String PROPERTY_NAME_TLS_12 = "tls12";
    public static final String PROPERTY_NAME_HEADERS = "headers";
    private static final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, HttpCommonException {
        if (args.length != 1) {
            LOGGER.log(Level.SEVERE, "Usage: java -cp java-common-utils-use-cases.1.0-jar-with-dependencies.jar  local.tin.tests.java.common.utils.use.cases.http.HttpClient <Properties file>");
            LOGGER.log(Level.SEVERE, "Properties file:");
            LOGGER.log(Level.SEVERE, "\tmethod=POST/GET");
            LOGGER.log(Level.SEVERE, "\turl=<URL>");
            LOGGER.log(Level.SEVERE, "\tbody=<Single line>");
            LOGGER.log(Level.SEVERE, "\ttls12=<true/false>");
            LOGGER.log(Level.SEVERE, "\theaders=<Comma separated pairs of header name and value");
            System.exit(1);
        } else {
            Properties properties = FileUtils.getInstance().getPropertiesFile(args[0]);
            GenericHttpClient genericHttpClient = new GenericHttpClient();
            if (properties.getProperty(PROPERTY_NAME_TLS_12) != null) {
                genericHttpClient = new GenericHttpClient(Boolean.parseBoolean(properties.getProperty(PROPERTY_NAME_TLS_12)));
            }
            Map<String, String> headers = new HashMap<>();
            if (properties.getProperty(PROPERTY_NAME_HEADERS) != null) {
                String[] headersArray = properties.getProperty(PROPERTY_NAME_HEADERS).split(",");
                for (int i = 0; i < (headersArray.length - 1); i++) {
                    headers.put(headersArray[i], headersArray[i + 1]);
                }
            }
            HttpResponseByteArray httpResponseByteArray = null;
            switch (properties.getProperty(PROPERTY_NAME_METHOD)) {
                case "GET":
                    httpResponseByteArray = genericHttpClient.makeGetRequest(properties.getProperty(PROPERTY_NAME_URL), headers);
                    break;
                case "POST":
                    httpResponseByteArray = genericHttpClient.makePostRequest(properties.getProperty(PROPERTY_NAME_URL), headers, properties.getProperty(PROPERTY_NAME_BODY).getBytes());
                    break;
                default:
                    LOGGER.log(Level.WARNING, "We should not reached this point...");
            }
             LOGGER.log(Level.INFO, "Response: {0}", new String(httpResponseByteArray.getResponseAsByteArray()));
             LOGGER.log(Level.INFO, "Response Http code: {0}", httpResponseByteArray.getHttpResponseCode());
        }
    }

}
