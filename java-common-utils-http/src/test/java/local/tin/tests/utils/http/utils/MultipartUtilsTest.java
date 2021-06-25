package local.tin.tests.utils.http.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class MultipartUtilsTest {

    private static final String PARAMETER_NAME = "parameter name";
    private static final String FILE_NAME = "file name";
    private static final String CONTENT_TYPE = "content type";    
    private static final String TRANSFER_ENCODING = "transfer encoding";    

    @Test
    public void getContentDisposition_returns_expected_string() {

        String result = MultipartUtils.getInstance().getContentDisposition(PARAMETER_NAME);

        assertThat(result, equalTo(MultipartUtils.CONTENT_DISPOSITION_PREFIX + PARAMETER_NAME + MultipartUtils.QUOTED_QUOTES));
    }

    @Test
    public void getContentDisposition_returns_expected_string_with_file_name() {

        String result = MultipartUtils.getInstance().getContentDisposition(PARAMETER_NAME, FILE_NAME);

        assertThat(result, equalTo(MultipartUtils.CONTENT_DISPOSITION_PREFIX + PARAMETER_NAME + MultipartUtils.QUOTED_QUOTES + MultipartUtils.CONTENT_DISPOSITION_FILE_NAME + FILE_NAME + MultipartUtils.QUOTED_QUOTES));
    }

    @Test
    public void getContentType_returns_expected_string() {

        String result = MultipartUtils.getInstance().getContentType(CONTENT_TYPE);

        assertThat(result, equalTo(MultipartUtils.CONTENT_TYPE + CONTENT_TYPE));
    }    

    @Test
    public void getContentTransferEncoding_returns_expected_string() {

        String result = MultipartUtils.getInstance().getContentTransferEncoding(TRANSFER_ENCODING);

        assertThat(result, equalTo(MultipartUtils.CONTENT_TRANSFER_ENCODING + TRANSFER_ENCODING));
    }      

}
