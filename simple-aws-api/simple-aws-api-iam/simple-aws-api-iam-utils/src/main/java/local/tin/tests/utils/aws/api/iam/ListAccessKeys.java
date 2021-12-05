package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AccessKeyMetadata;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysRequest;
import com.amazonaws.services.identitymanagement.model.ListAccessKeysResult;
import java.util.ArrayList;
import java.util.List;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.iam.AccessKey;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import org.apache.log4j.Logger;

/**
 *
 * @author developer01
 */
public class ListAccessKeys extends AbstractIAMRequest<List<AccessKey>> {

    private static final Logger LOGGER = Logger.getLogger(ListAccessKeys.class);

    private ListAccessKeys() {
    }

    public static ListAccessKeys getInstance() {
        return ListAccessKeysHolder.INSTANCE;
    }

    /**
     * List all available access keys given the credentials:
     *
     * <ul>
     * <li>User name</li>
     * <li>Access key</li>
     * <li>Secret</li>
     * <li>Region</li>
     * </ul>
     *
     * @param iam as AmazonIdentityManagement
     * @param request as local.tin.tests.aws.api.iam.model.Request
     * @return List of AccessKey
     * @throws IAMException
     */
    @Override
    protected List<AccessKey> execute(AmazonIdentityManagement iam, Request request) throws IAMException {
        List<AccessKey> accessKeys = new ArrayList<>();
        ListAccessKeysRequest listAccessKeysRequest = new ListAccessKeysRequest();
        ListAccessKeysResult response = iam.listAccessKeys(listAccessKeysRequest);
        for (AccessKeyMetadata metadata : response.getAccessKeyMetadata()) {
            AccessKey accessKey = new AccessKey();
            accessKey.setUserName(metadata.getUserName());
            accessKey.setAccessKeyId(metadata.getAccessKeyId());
            accessKey.setStatus(metadata.getStatus());
            accessKey.setCreationDate(metadata.getCreateDate());
            accessKeys.add(accessKey);
        }
        return accessKeys;
    }

    private static class ListAccessKeysHolder {

        private static final ListAccessKeys INSTANCE = new ListAccessKeys();
    }
}
