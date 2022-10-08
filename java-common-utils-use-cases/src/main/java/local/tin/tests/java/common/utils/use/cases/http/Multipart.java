package local.tin.tests.java.common.utils.use.cases.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import local.tin.tests.utils.file.FileUtils;
import local.tin.tests.utils.http.model.HttpCommonException;
import local.tin.tests.utils.http.model.HttpResponseByteArray;
import java.util.logging.Level;
import java.util.logging.Logger;
import local.tin.tests.utils.http.model.MultipartItem;
import local.tin.tests.utils.http.model.MultipartRequest;

/**
 *
 * @author benitodarder
 */
public class Multipart {

    public static final String PROPERTY_NAME_METHOD = "method";
    public static final String PROPERTY_NAME_URL = "url";
    public static final String PROPERTY_NAME_BODY = "body";
    public static final String PROPERTY_NAME_TLS_12 = "tls12";
    public static final String PROPERTY_NAME_HEADERS = "headers";
    public static final String PROPERTY_NAME_PARAM = "parameter";
    public static final String PROPERTY_NAME_PROTOCOL = "protocol";
    public static final String PROPERTY_NAME_FILE = "file";
    private static final Logger LOGGER = Logger.getLogger(Multipart.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, HttpCommonException {
        if (args.length != 1) {
            LOGGER.log(Level.SEVERE, "Usage: java -cp java-common-utils-use-cases.1.0-jar-with-dependencies.jar  local.tin.tests.java.common.utils.use.cases.http.HttpClient <Properties file>");
            LOGGER.log(Level.SEVERE, "Properties file:");
            LOGGER.log(Level.SEVERE, "\turl=<URL>");
            LOGGER.log(Level.SEVERE, "\tfilee=<File path>");
            LOGGER.log(Level.SEVERE, "\tparameter=<Parameter>");
            LOGGER.log(Level.SEVERE, "\theaders=<Comma separated pairs of header name and value");
            System.exit(1);
        } else {
            MultipartRequest multipartRequest = new MultipartRequest();
            Properties properties = FileUtils.getInstance().getPropertiesFile(args[0]);
            Map<String, String> headers = new HashMap<>();
            if (properties.getProperty(PROPERTY_NAME_HEADERS) != null) {
                String[] headersArray = properties.getProperty(PROPERTY_NAME_HEADERS).split(",");
                for (int i = 0; i < (headersArray.length - 1); i++) {
                    multipartRequest.getHeaders().put(headersArray[i], headersArray[i + 1]);
                }
            }
            multipartRequest.setURLString(properties.getProperty(PROPERTY_NAME_URL));
            MultipartItem multipartItem = new MultipartItem();
            multipartItem.setContentType("text/plain; charset=us-ascii");
            multipartItem.setContentTransferEncoding("7bit");
            multipartItem.setContentDisposition("form-data; name=\"msg\"");
            multipartItem.setInputStream(new ByteArrayInputStream(properties.getProperty(PROPERTY_NAME_PARAM).getBytes()));
            multipartRequest.getMultipartItems().add(multipartItem);
            multipartItem = new MultipartItem();
            multipartItem.setContentType("application/octet-stream; name=" + properties.getProperty(PROPERTY_NAME_FILE));
            multipartItem.setContentTransferEncoding("binary");
            multipartItem.setContentDisposition("form-data; name=\"file\"; filename=\"" + properties.getProperty(PROPERTY_NAME_FILE) + "\"");
            multipartItem.setInputStream(new ByteArrayInputStream(FileUtils.getInstance().getFileAsString(properties.getProperty(PROPERTY_NAME_FILE)).getBytes()));
            multipartRequest.getMultipartItems().add(multipartItem);
            local.tin.tests.utils.http.MultipartClient getClient = new local.tin.tests.utils.http.MultipartClient();
            HttpResponseByteArray httpResponseByteArray = getClient.makeRequest(multipartRequest);
            LOGGER.log(Level.INFO, "Response: {0}", new String(httpResponseByteArray.getResponseAsByteArray()));
            LOGGER.log(Level.INFO, "Response Http code: {0}", httpResponseByteArray.getHttpResponseCode());
        }
    }

}
