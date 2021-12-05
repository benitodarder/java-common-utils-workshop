package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysRequest;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import local.tin.tests.utils.aws.api.model.iam.AccessKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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
@PrepareForTest({Commons.class,ListAccessKeys.class, ListAccessKeysRequest.class})
public class ListAccessKeysTest extends AbstractIAMRequestTest {

    private ListAccessKeysResult mockedListAccessKeysResult;
    private List<AccessKeyMetadata> accessKeyMetadatas;
    private ListAccessKeysRequest mockedListAccessKeysRequest;


    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockedListAccessKeysResult = mock(ListAccessKeysResult.class);
        accessKeyMetadatas = new ArrayList<>();
        mockedListAccessKeysRequest = mock(ListAccessKeysRequest.class);
        PowerMockito.whenNew(ListAccessKeysRequest.class).withNoArguments().thenReturn(mockedListAccessKeysRequest);
        when(mockedAmazonIdentityManagement.listAccessKeys(mockedListAccessKeysRequest)).thenReturn(mockedListAccessKeysResult);
        when(mockedListAccessKeysResult.getAccessKeyMetadata()).thenReturn(accessKeyMetadatas);
        when(mockedListAccessKeysResult.isTruncated()).thenReturn(Boolean.TRUE);        
    }

    @Test
    public void execute_does_not_generate_new_amazonIdentityManagement_from_given_request() throws Exception {


        ListAccessKeys.getInstance().execute(mockedAmazonIdentityManagement, mockedRequest);

        verify(mockedCommons, never()).getIAM(mockedRequest);
    }

    @Test
    public void execute_returns_result_from_aws() throws Exception {
        AccessKeyMetadata accessKeyMetadata = mock(AccessKeyMetadata.class);
        when(accessKeyMetadata.getAccessKeyId()).thenReturn(ACCESS_KEY_ID + ACCESS_KEY_ID);
        when(accessKeyMetadata.getCreateDate()).thenReturn(new Date(SAMPLE_DATE));
        when(accessKeyMetadata.getStatus()).thenReturn(STATUS);
        when(accessKeyMetadata.getUserName()).thenReturn(USER_NAME);
        accessKeyMetadatas.add(accessKeyMetadata);

        List<AccessKey> result = ListAccessKeys.getInstance().execute(mockedAmazonIdentityManagement, mockedRequest);

        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getAccessKeyId(), equalTo(ACCESS_KEY_ID + ACCESS_KEY_ID));
        assertThat(result.get(0).getCreationDate().getTime(), equalTo(SAMPLE_DATE));
        assertThat(result.get(0).getUserName(), equalTo(USER_NAME));
        assertThat(result.get(0).getStatus(), equalTo(STATUS));
    }

    @Test
    public void execute_does_not_assign_user_name() throws Exception {


        ListAccessKeys.getInstance().execute(mockedAmazonIdentityManagement, mockedRequest);

        verify(mockedListAccessKeysRequest, never()).withUserName(anyString());
    }
    
}
