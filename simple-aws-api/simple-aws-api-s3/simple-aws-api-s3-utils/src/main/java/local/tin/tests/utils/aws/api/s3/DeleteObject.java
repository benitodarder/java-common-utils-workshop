package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import java.util.Date;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import local.tin.tests.utils.aws.api.model.s3.S3Request;
import local.tin.tests.utils.aws.api.model.s3.S3Response;
import org.apache.log4j.Logger;

/**
 *
 * @author developer01
 */
public class DeleteObject extends AbstractS3Request<S3Response>{

    private static final Logger LOGGER = Logger.getLogger(DeleteObject.class);

    private DeleteObject() {
    }

    public static DeleteObject getInstance() {
        return DeleteObjectHolder.INSTANCE;
    }

    private static class DeleteObjectHolder {

        private static final DeleteObject INSTANCE = new DeleteObject();
    }

    @Override
    protected S3Response execute(AmazonS3 amazonS3, S3Request request) throws S3Exception {
        S3Response s3Response = new S3Response();
        s3Response.setTimestamp(new Date());
        try {
            amazonS3.deleteObject(Commons.getInstance().getDeleteObjectRequest(request.getBucketName(), request.getKey()));
            s3Response.setSuccess(true);
            s3Response.setMessage(request.getBucketName());
        } catch (SdkClientException ex) {
            throw new S3Exception(ex);
        }
        return s3Response;
    }
}
