package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import org.apache.log4j.Logger;

/**
 * The
 * @author developer01
 */
public class Commons {
    
    private static final Logger LOGGER = Logger.getLogger(Commons.class);

    private Commons() {
    }

    public static Commons getInstance() {
        return CommonsHolder.INSTANCE;
    }

    private static class CommonsHolder {

        private static final Commons INSTANCE = new Commons();
    }

    /**
     * Returns the corresponding Amazon Identity Management given the following
     * details:
     * 
     * <ul>
     * <li>Access key</li>
     * <li>Secret</li>
     * <li>Region</li>
     * </ul>
     * 
     * Located in a separated class in order to ease unit testing as 
     * AmazonIdentityManagementClientBuilder is a final class.
     * 
     * @param request as local.tin.tests.aws.api.iam.model.Request
     * @return AmazonIdentityManagement
     * @throws IAMException 
     */
    public AmazonIdentityManagement getIAM(Request request) throws IAMException {
        try {
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(request.getAccessKeyId(), request.getSecret());
            AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
            return AmazonIdentityManagementClientBuilder.standard().withCredentials(awsStaticCredentialsProvider).withRegion(request.getRegion()).build();
        } catch (Throwable t) {
            LOGGER.error(t);
            throw new IAMException(t);
        }
    }
}
