package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyResult;
import static local.tin.tests.utils.aws.api.iam.AbstractIAMRequestTest.mockedCommons;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
@PrepareForTest({Commons.class, DeleteAccessKey.class, DeleteAccessKeyRequest.class})
public class DeleteAccessKeyTest extends AbstractIAMRequestTest {

    private static final String REQUEST_ID = "666";
    private DeleteAccessKeyResult mockedDeleteAccessKeyResult;
    private ResponseMetadata mockedResponseMetadata;
    private DeleteAccessKeyRequest mockedDeleteAccessKeyRequest;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockedDeleteAccessKeyResult = mock(DeleteAccessKeyResult.class);
        mockedResponseMetadata = mock(ResponseMetadata.class);
        when(mockedDeleteAccessKeyResult.getSdkResponseMetadata()).thenReturn(mockedResponseMetadata);
        when(mockedResponseMetadata.getRequestId()).thenReturn(REQUEST_ID);
        mockedDeleteAccessKeyRequest = mock(DeleteAccessKeyRequest.class);
        PowerMockito.whenNew(DeleteAccessKeyRequest.class).withNoArguments().thenReturn(mockedDeleteAccessKeyRequest);
        when(mockedAmazonIdentityManagement.deleteAccessKey(mockedDeleteAccessKeyRequest)).thenReturn(mockedDeleteAccessKeyResult);

    }

    @Test
    public void execute_returns_deleteAccessKey_requestid_result() throws IAMException, Exception {

        String result = DeleteAccessKey.getInstance().execute(mockedAmazonIdentityManagement, mockedRequest);

        assertThat(result, equalTo(REQUEST_ID));
    }

    @Test
    public void execute_assigns_access_key_to_DeleteAccessKeyRequest() throws IAMException, Exception {

        String result = DeleteAccessKey.getInstance().execute(mockedAmazonIdentityManagement, mockedRequest);

        verify(mockedDeleteAccessKeyRequest).setAccessKeyId(ACCESS_KEY_ID);
    }
    
    @Test
    public void execute_does_not_generate_new_amazonIdentityManagement_from_given_request() throws Exception {


        DeleteAccessKey.getInstance().execute(mockedAmazonIdentityManagement, mockedRequest);

        verify(mockedCommons, never()).getIAM(mockedRequest);
    }      
}
