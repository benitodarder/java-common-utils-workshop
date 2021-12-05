package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author developer01
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Commons.class, File.class})
public class PutObjectTest extends AbstractS3RequestTest {

    private PutObjectRequest mockedPutObjectRequest;
    private File mockedFile;
    private Map<String, String> tagsMap;
    private ObjectTagging mockedObjectTagging;
    private byte[] sampleByte;
    
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }
    
    @Test
    public void execute_stores_expected_object() throws IOException, S3Exception {
        sampleByte = new byte[SAMPLE_READ_LENGTH];
        when(mockedRequest.getFile()).thenReturn(sampleByte);
        mockedFile = mock(File.class);
        PowerMockito.mockStatic(File.class);
        when(File.createTempFile(KEY, BUCKET_NAME)).thenReturn(mockedFile);
        mockedPutObjectRequest = mock(PutObjectRequest.class);
        when(mockedCommons.getPutObjectRequest(eq(BUCKET_NAME), eq(KEY), any(File.class))).thenReturn(mockedPutObjectRequest);
        tagsMap = new HashMap<>();
        tagsMap.put(TAG_KEY, TAG_VALUE);
        when(mockedRequest.getTags()).thenReturn(tagsMap);
        mockedObjectTagging = mock(ObjectTagging.class);
        when(mockedCommons.getObjectTagging(tagsMap)).thenReturn(mockedObjectTagging);
        
        PutObject.getInstance().execute(mockedAmazonS3, mockedRequest);
        
        verify(mockedAmazonS3).putObject(mockedPutObjectRequest);
        verify(mockedPutObjectRequest).withTagging(mockedObjectTagging);
    }
    
}
