package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import local.tin.tests.utils.aws.api.model.s3.S3Response;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author developer01
 */
public class GetObjectTest extends AbstractS3RequestTest {



    private static final byte[] BUFFER = new byte[GetObject.BUFFER_SIZE];
    private GetObjectRequest mockedGetObjectRequest;
    private GetObjectTaggingRequest mockedGetObjectTaggingRequest;
    private S3Object mockedS3Object;
    private S3ObjectInputStream mockedS3ObjectInputStream;
    private GetObjectTaggingResult mockedGetObjectTaggingResult;
    private List<Tag> tags;
    private Tag mockedTag01;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mockedGetObjectRequest = mock(GetObjectRequest.class);
        when(mockedCommons.getGetObjectRequest(BUCKET_NAME, KEY)).thenReturn(mockedGetObjectRequest);
        mockedGetObjectTaggingRequest = mock(GetObjectTaggingRequest.class);
        when(mockedCommons.getGetObjectTaggingRequest(BUCKET_NAME, KEY)).thenReturn(mockedGetObjectTaggingRequest);
        mockedS3Object = mock(S3Object.class);
        when(mockedAmazonS3.getObject(mockedGetObjectRequest)).thenReturn(mockedS3Object);
        mockedS3ObjectInputStream = mock(S3ObjectInputStream.class);
        when(mockedS3Object.getObjectContent()).thenReturn(mockedS3ObjectInputStream);
        when(mockedS3ObjectInputStream.read(any(byte[].class))).thenReturn(0);       
    }
    
    @Test
    public void execute_returns_expected_result_when_empty_object_content_and_no_tags() throws IOException, S3Exception {

        
        S3Response result = GetObject.getInstance().execute(mockedAmazonS3, mockedRequest);
        
        assertThat(result.getFile().length, equalTo(0));
        assertThat(result.getTags().isEmpty(), equalTo(true));
    }
    
    @Test
    public void execute_returns_expected_result_when_object_content_and_no_tags() throws IOException, S3Exception {
        when(mockedS3ObjectInputStream.read(any(byte[].class))).thenReturn(SAMPLE_READ_LENGTH).thenReturn(0); 
        
        S3Response result = GetObject.getInstance().execute(mockedAmazonS3, mockedRequest);
        
        assertThat(result.getFile().length, equalTo(SAMPLE_READ_LENGTH));
        assertThat(result.getTags().isEmpty(), equalTo(true));
    }    

    @Test
    public void execute_returns_expected_result_when_empty_object_content_with_tags() throws IOException, S3Exception {
        when(mockedS3Object.getTaggingCount()).thenReturn(1);
        mockedGetObjectTaggingResult = mock(GetObjectTaggingResult.class);
        when(mockedAmazonS3.getObjectTagging(mockedGetObjectTaggingRequest)).thenReturn(mockedGetObjectTaggingResult);
        mockedTag01 = mock(Tag.class);
        when(mockedTag01.getKey()).thenReturn(TAG_KEY);
        when(mockedTag01.getValue()).thenReturn(TAG_VALUE);
        tags = new ArrayList<>();
        tags.add(mockedTag01);
        when(mockedGetObjectTaggingResult.getTagSet()).thenReturn(tags);
        
        S3Response result = GetObject.getInstance().execute(mockedAmazonS3, mockedRequest);
        
        assertThat(result.getFile().length, equalTo(0));
        assertThat(result.getTags().size(), equalTo(1));
        assertThat(result.getTags().get(TAG_KEY), equalTo(TAG_VALUE));
    }    
}
