package local.tin.tests.utils.http.utils;

/**
 *
 * @author benitodarder
 */
public class MultipartUtils {

    public static final String CONTENT_DISPOSITION_PREFIX = "Content-Disposition: form-data; name=\"";
    public static final String QUOTED_QUOTES = "\"";
    public static final String CONTENT_DISPOSITION_FILE_NAME = "; filename=\"";
    public static final String CONTENT_TYPE = "Content-Type: ";
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: ";
    public static final String BOUNDARY_PREFIX = "==";
    public static final String BOUNDARY_SUFFIX = "==";
    public static final String BOUNDARY_END = "--";

    private MultipartUtils() {
    }

    public static MultipartUtils getInstance() {
        return MultipartUtilsHolder.INSTANCE;
    }

    private static class MultipartUtilsHolder {

        private static final MultipartUtils INSTANCE = new MultipartUtils();
    }

    public String getContentDisposition(String parameterName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTENT_DISPOSITION_PREFIX).append(parameterName).append(QUOTED_QUOTES);
        return stringBuilder.toString();
    }

    public String getContentDisposition(String parameterName, String fileName) {
        StringBuilder stringBuilder = new StringBuilder(getContentDisposition(parameterName));
        stringBuilder.append(CONTENT_DISPOSITION_FILE_NAME).append(fileName).append(QUOTED_QUOTES);
        return stringBuilder.toString();
    }

    public String getContentType(String contentType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTENT_TYPE).append(contentType);
        return stringBuilder.toString();
    }

    public String getContentTransferEncoding(String contentTransferEncoding) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTENT_TRANSFER_ENCODING).append(contentTransferEncoding);
        return stringBuilder.toString();
    }    
    
    public String getBoundary() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BOUNDARY_PREFIX).append(System.currentTimeMillis()).append(BOUNDARY_SUFFIX);
        return stringBuilder.toString();        
    }

}
