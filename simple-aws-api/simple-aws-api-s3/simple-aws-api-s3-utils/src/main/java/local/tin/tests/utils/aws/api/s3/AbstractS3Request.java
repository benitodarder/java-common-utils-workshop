package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.services.s3.AmazonS3;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import local.tin.tests.utils.aws.api.model.s3.S3Request;
import org.apache.log4j.Logger;

/**
 *
 * @author developer01
 */
public abstract class AbstractS3Request<K> {

    private static final Logger LOGGER = Logger.getLogger(AbstractS3Request.class);

    protected abstract K execute(AmazonS3 iam, S3Request request) throws S3Exception;

    protected Logger getLogger() {
        return LOGGER;
    }    
    
    /**
     * Generates the corresponding S3 with provided credentials and calls the
     * remote AWS S3 service.
     * 
     * Catches Throwable to log error and throw custom S3Exception.
     * 
     * @param request as local.tin.tests.aws.api.s3.model.S3Request
     * @return K 
     * @throws local.tin.tests.utils.aws.api.model.s3.S3Exception 
     */
    public K getResult(S3Request request) throws S3Exception {
        try {
            return execute(Commons.getInstance().getAmazonS3(request), request);
        } catch (Throwable t) {
            LOGGER.error(t);
            throw new S3Exception(t);
        }
    }    
}
