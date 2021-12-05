package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyRequest;
import com.amazonaws.services.identitymanagement.model.DeleteAccessKeyResult;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import org.apache.log4j.Logger;

/**
 *
 * @author developer01
 */
public class DeleteAccessKey extends AbstractIAMRequest<String>{

    private static final Logger LOGGER = Logger.getLogger(DeleteAccessKey.class);

    private DeleteAccessKey() {
    }

    public static DeleteAccessKey getInstance() {
        return DeleteAccessKeyHolder.INSTANCE;
    }

    /**
     * Deletes the access key corresponding to:
     * 
     * <ul>
     * <li>User name</li>
     * <li>Access key</li>
     * <li>Secret</li>
     * <li>Region</li>
     * </ul>
     * @param iam as AmazonIdentityManagement
     * @param request as local.tin.tests.aws.api.iam.model.Request
     * @return String with the corresponding request Id.
     * @throws IAMException 
     */    
    @Override
    protected String execute(AmazonIdentityManagement iam, Request request) throws IAMException {
            DeleteAccessKeyRequest deleteAccessKey = new DeleteAccessKeyRequest();
            deleteAccessKey.setAccessKeyId(request.getAccessKeyId());
            DeleteAccessKeyResult deleteAccessKeyResult = iam.deleteAccessKey(deleteAccessKey);            
            return deleteAccessKeyResult.getSdkResponseMetadata().getRequestId();
    }

    private static class DeleteAccessKeyHolder {

        private static final DeleteAccessKey INSTANCE = new DeleteAccessKey();
    }


}
