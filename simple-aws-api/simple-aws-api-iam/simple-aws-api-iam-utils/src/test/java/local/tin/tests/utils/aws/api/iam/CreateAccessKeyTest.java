package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyResult;
import java.util.Date;
import local.tin.tests.utils.aws.api.model.iam.AccessKey;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import static local.tin.tests.utils.aws.api.iam.AbstractIAMRequestTest.mockedCommons;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
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
public class CreateAccessKeyTest extends AbstractIAMRequestTest {
    
    private static final String REQUEST_ID = "666";
    private CreateAccessKeyResult mockedCreateAccessKeyResult;
    private ResponseMetadata mockedResponseMetadata;
    private com.amazonaws.services.identitymanagement.model.AccessKey mockedAccessKey;
    
    
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockedCreateAccessKeyResult = mock(CreateAccessKeyResult.class);
        mockedAccessKey = mock(com.amazonaws.services.identitymanagement.model.AccessKey.class);
        when(mockedAccessKey.getAccessKeyId()).thenReturn(ACCESS_KEY_ID);
        when(mockedAccessKey.getCreateDate()).thenReturn(new Date(SAMPLE_DATE));
        when(mockedAccessKey.getSecretAccessKey()).thenReturn(SECRET);
        when(mockedAccessKey.getStatus()).thenReturn(STATUS);
        when(mockedAccessKey.getUserName()).thenReturn(USER_NAME);
        when(mockedCreateAccessKeyResult.getAccessKey()).thenReturn(mockedAccessKey);
        mockedResponseMetadata = mock(ResponseMetadata.class);
        when(mockedCreateAccessKeyResult.getSdkResponseMetadata()).thenReturn(mockedResponseMetadata);
        when(mockedResponseMetadata.getRequestId()).thenReturn(REQUEST_ID);
        when(mockedAmazonIdentityManagement.createAccessKey()).thenReturn(mockedCreateAccessKeyResult);        
    }

    @Test
    public void execute_returns_new_AccessKey() throws IAMException {
        
        AccessKey result = CreateAccessKey.getInstance().execute(mockedAmazonIdentityManagement, mockedRequest);
        
        assertThat(result.getAccessKeyId(), equalTo(ACCESS_KEY_ID));
        assertThat(result.getSecret(), equalTo(SECRET));
        assertThat(result.getStatus(), equalTo(STATUS));
        assertThat(result.getUserName(), equalTo(USER_NAME));
        assertThat(result.getCreationDate().getTime(), equalTo(SAMPLE_DATE));
    }
    
    @Test
    public void execute_does_not_generate_new_amazonIdentityManagement_from_given_request() throws Exception {


        CreateAccessKey.getInstance().execute(mockedAmazonIdentityManagement, mockedRequest);

        verify(mockedCommons, never()).getIAM(mockedRequest);
    }    
}
