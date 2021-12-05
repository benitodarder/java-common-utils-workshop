package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
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
public class AbstractIAMRequestTest {

    protected static final String EXECUTED = "executed";    
    protected static final long SAMPLE_DATE = 118431000000l;
    protected static final String USER_NAME = "user name";
    protected static final String SECRET = "secret";
    protected static final String REGION = "region";
    protected static final String ACCESS_KEY_ID = "access key id";
    protected static final String STATUS = "STATUS";
    protected static Commons mockedCommons;
    protected static Logger mockedLogger;
    protected AmazonIdentityManagement mockedAmazonIdentityManagement;
    protected Request mockedRequest;
    private AbstractIAMRequest abstractIAMMethod;
    

    @BeforeClass
    public static void setUpClass() {
        mockedCommons = mock(Commons.class);
        mockedLogger = mock(Logger.class);
    }

    @Before
    public void setUp() throws Exception {
        mockedRequest = mock(Request.class);
        when(mockedRequest.getAccessKeyId()).thenReturn(ACCESS_KEY_ID);
        when(mockedRequest.getRegion()).thenReturn(REGION);
        when(mockedRequest.getSecret()).thenReturn(SECRET);
        when(mockedRequest.getUserName()).thenReturn(USER_NAME);
        PowerMockito.mockStatic(Logger.class);
        when(Logger.getLogger(AbstractIAMRequest.class)).thenReturn(mockedLogger);
        PowerMockito.mockStatic(Commons.class);
        when(Commons.getInstance()).thenReturn(mockedCommons);
        mockedAmazonIdentityManagement = mock(AmazonIdentityManagement.class);
        abstractIAMMethod = new AbstractIAMMethodWrapper();
    }

    @Test
    public void getResult_gets_iam_from_commons() throws Exception {
        when(mockedCommons.getIAM(mockedRequest)).thenReturn(mockedAmazonIdentityManagement);

        abstractIAMMethod.getResult(mockedRequest);

        verify(mockedCommons).getIAM(mockedRequest);
    }


    @Test
    public void getResult_returns_execute_response() throws Exception {
        when(mockedCommons.getIAM(mockedRequest)).thenReturn(mockedAmazonIdentityManagement);

        String string = (String) abstractIAMMethod.getResult(mockedRequest);

        assertThat(string, equalTo(EXECUTED));
    }

    
    @Test(expected = IAMException.class)
    public void getResult_logs_exception() throws Exception {
        IAMException mockedIAMException = mock(IAMException.class);
        when(mockedCommons.getIAM(mockedRequest)).thenThrow(mockedIAMException);

        String string = (String) abstractIAMMethod.getResult(mockedRequest);

        verify(mockedLogger).error(mockedIAMException);
    }    
}


class AbstractIAMMethodWrapper extends AbstractIAMRequest<String> {

    @Override
    protected String execute(AmazonIdentityManagement iam, Request request) throws IAMException {
        return AbstractIAMRequestTest.EXECUTED;
    }
    
    
    
}