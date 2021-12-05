package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import local.tin.tests.utils.aws.api.model.s3.S3Request;
import local.tin.tests.utils.aws.api.model.s3.S3Response;
import org.apache.log4j.Logger;

/**
 *
 * @author developer01
 */
public class PutObject extends AbstractS3Request<S3Response>{

    private static final Logger LOGGER = Logger.getLogger(PutObject.class);
    
    private PutObject() {
    }

    public static PutObject getInstance() {
        return PutObjectHolder.INSTANCE;
    }

    private static class PutObjectHolder {

        private static final PutObject INSTANCE = new PutObject();
    }

    @Override
    protected S3Response execute(AmazonS3 amazonS3, S3Request request) throws S3Exception {
        S3Response s3Response = new S3Response();
        s3Response.setTimestamp(new Date());
        try {

            File file = File.createTempFile(request.getKey(), request.getBucketName());
            file.deleteOnExit();
            Files.write(file.toPath(), request.getFile());
            PutObjectRequest putObjectRequest = Commons.getInstance().getPutObjectRequest(request.getBucketName(), request.getKey(), file);

            putObjectRequest.withTagging(Commons.getInstance().getObjectTagging(request.getTags()));

            amazonS3.putObject(putObjectRequest);
            s3Response.setSuccess(true);
            s3Response.setMessage(request.getBucketName());
        } catch (SdkClientException | IOException ex) {
            throw new S3Exception(ex);
        }
        return s3Response;
    }
}
