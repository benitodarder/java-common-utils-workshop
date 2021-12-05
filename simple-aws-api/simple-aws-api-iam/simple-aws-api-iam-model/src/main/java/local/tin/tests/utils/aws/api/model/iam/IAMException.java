package local.tin.tests.utils.aws.api.model.iam;

import local.tin.tests.utils.aws.api.model.AWSAPIException;

/**
 *
 * @author developer01
 */
public class IAMException extends AWSAPIException {

    public IAMException(String message) {
        super(message);
    }

    public IAMException(String message, Throwable cause) {
        super(message, cause);
    }

    public IAMException(Throwable cause) {
        super(cause);
    }
    
    
}
