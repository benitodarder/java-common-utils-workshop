package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyResult;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.iam.AccessKey;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import org.apache.log4j.Logger;

/**
 *
 * @author developer01
 */
public class CreateAccessKey extends AbstractIAMRequest<AccessKey>{

    private static final Logger LOGGER = Logger.getLogger(CreateAccessKey.class);

    private CreateAccessKey() {
    }

    public static CreateAccessKey getInstance() {
        return ListAccessKeysHolder.INSTANCE;
    }

    @Override
    protected AccessKey execute(AmazonIdentityManagement iam, Request request) throws IAMException {
        CreateAccessKeyResult createAccountAliasResult =  iam.createAccessKey();
        AccessKey accessKey = new AccessKey();
        accessKey.setAccessKeyId(createAccountAliasResult.getAccessKey().getAccessKeyId());
        accessKey.setCreationDate(createAccountAliasResult.getAccessKey().getCreateDate());
        accessKey.setSecret(createAccountAliasResult.getAccessKey().getSecretAccessKey());
        accessKey.setStatus(createAccountAliasResult.getAccessKey().getStatus());
        accessKey.setUserName(createAccountAliasResult.getAccessKey().getUserName());
        return accessKey;
    }

    private static class ListAccessKeysHolder {

        private static final CreateAccessKey INSTANCE = new CreateAccessKey();
    }



}
