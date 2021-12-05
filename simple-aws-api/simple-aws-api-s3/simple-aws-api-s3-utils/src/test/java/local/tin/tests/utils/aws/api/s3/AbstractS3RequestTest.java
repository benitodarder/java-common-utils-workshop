package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.services.s3.AmazonS3;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import local.tin.tests.utils.aws.api.model.s3.S3Request;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@PrepareForTest({Commons.class, Logger.class})
public class AbstractS3RequestTest {

    protected static final int SAMPLE_READ_LENGTH = 2;        
    protected static final String TAG_VALUE = "tag value";
    protected static final String TAG_KEY = "tag key";        
    protected static final String BUCKET_NAME = "Bucket name";
    protected static final String KEY = "key";
    protected static final String EXECUTED = "executed";    
    protected static final long SAMPLE_DATE = 118431000000l;
    protected static final String USER_NAME = "user name";
    protected static final String SECRET = "secret";
    protected static final String REGION = "region";
    protected static final String ACCESS_KEY_ID = "access key id";
    protected static final String STATUS = "STATUS";
    protected static Commons mockedCommons;
    protected static Logger mockedLogger;
    protected AmazonS3 mockedAmazonS3;
    protected S3Request mockedRequest;
    private AbstractS3Request abstractIAMMethod;
    

    @BeforeClass
    public static void setUpClass() {
        mockedCommons = mock(Commons.class);
        mockedLogger = mock(Logger.class);
    }

    @Before
    public void setUp() throws Exception {
        mockedRequest = mock(S3Request.class);
        when(mockedRequest.getAccessKeyId()).thenReturn(ACCESS_KEY_ID);
        when(mockedRequest.getRegion()).thenReturn(REGION);
        when(mockedRequest.getSecret()).thenReturn(SECRET);
        when(mockedRequest.getUserName()).thenReturn(USER_NAME);
        when(mockedRequest.getKey()).thenReturn(KEY);
        when(mockedRequest.getBucketName()).thenReturn(BUCKET_NAME);
        PowerMockito.mockStatic(Logger.class);
        when(Logger.getLogger(AbstractS3Request.class)).thenReturn(mockedLogger);
        PowerMockito.mockStatic(Commons.class);
        when(Commons.getInstance()).thenReturn(mockedCommons);
        mockedAmazonS3 = mock(AmazonS3.class);
        abstractIAMMethod = new AbstractS3MethodWrapper();
    }

    @Test
    public void getResult_gets_iam_from_commons() throws Exception {
        when(mockedCommons.getAmazonS3(mockedRequest)).thenReturn(mockedAmazonS3);

        abstractIAMMethod.getResult(mockedRequest);

        verify(mockedCommons).getAmazonS3(mockedRequest);
    }


    @Test
    public void getResult_returns_execute_response() throws Exception {
        when(mockedCommons.getAmazonS3(mockedRequest)).thenReturn(mockedAmazonS3);

        String string = (String) abstractIAMMethod.getResult(mockedRequest);

        assertThat(string, equalTo(EXECUTED));
    }

    
    @Test(expected = S3Exception.class)
    public void getResult_logs_exception() throws Exception {
        S3Exception mockedS3Exception = mock(S3Exception.class);
        when(mockedCommons.getAmazonS3(mockedRequest)).thenThrow(mockedS3Exception);

        String string = (String) abstractIAMMethod.getResult(mockedRequest);

        verify(mockedLogger).error(mockedS3Exception);
    }    
}


class AbstractS3MethodWrapper extends AbstractS3Request<String> {

    @Override
    protected String execute(AmazonS3 iam, S3Request request) throws S3Exception {
        return AbstractS3RequestTest.EXECUTED;
    }
    
    
    
}