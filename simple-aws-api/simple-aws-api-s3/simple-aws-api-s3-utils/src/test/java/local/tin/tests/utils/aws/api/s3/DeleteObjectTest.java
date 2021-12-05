package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author developer01
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Commons.class})
public class DeleteObjectTest extends AbstractS3RequestTest {
    
    private DeleteObjectRequest mockedDeleteObjectRequest;
    
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockedDeleteObjectRequest = mock(DeleteObjectRequest.class);
        when(mockedCommons.getDeleteObjectRequest(BUCKET_NAME, KEY)).thenReturn(mockedDeleteObjectRequest);
    }
    
    @Test
    public void execute_calls_amazon_s3_client_delete() throws S3Exception {
        
        DeleteObject.getInstance().execute(mockedAmazonS3, mockedRequest);
        
        verify(mockedAmazonS3).deleteObject(mockedDeleteObjectRequest);
    }

    
    @Test
    public void execute_does_not_generate_new_amazon_s3() throws S3Exception {
        
        DeleteObject.getInstance().execute(mockedAmazonS3, mockedRequest);
        
        verify(mockedCommons, never()).getAmazonS3(mockedRequest);
    }    
}
