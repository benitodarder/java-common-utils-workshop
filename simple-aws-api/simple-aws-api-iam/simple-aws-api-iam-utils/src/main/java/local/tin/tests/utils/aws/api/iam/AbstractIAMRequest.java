package local.tin.tests.utils.aws.api.iam;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import org.apache.log4j.Logger;

/**
 *
 * @author developer01
 * @param <K>
 */
public abstract class AbstractIAMRequest<K> {

    private static final Logger LOGGER = Logger.getLogger(AbstractIAMRequest.class);

    protected abstract K execute(AmazonIdentityManagement iam, Request request) throws IAMException;

    protected Logger getLogger() {
        return LOGGER;
    }
    
    /**
     * Generates the corresponding IAM with provided credentials and calls the
     * remote AWS IAM service.
     * 
     * Catches Throwable to log error and throw custom IAMException.
     * 
     * @param request as local.tin.tests.aws.api.model.Request
     * @return K 
     * @throws IAMException 
     */
    public K getResult(Request request) throws IAMException {
        try {
            return execute(Commons.getInstance().getIAM(request), request);
        } catch (Throwable t) {
            LOGGER.error(t);
            throw new IAMException(t);
        }
    }

}
