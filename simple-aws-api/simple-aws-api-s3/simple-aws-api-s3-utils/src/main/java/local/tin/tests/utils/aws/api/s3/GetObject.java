package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.Tag;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import local.tin.tests.utils.aws.api.model.s3.S3Request;
import local.tin.tests.utils.aws.api.model.s3.S3Response;
import org.apache.log4j.Logger;

/**
 *
 * @author developer01
 */
public class GetObject extends AbstractS3Request<S3Response>{

    public static final int BUFFER_SIZE = 1024;
    private static final Logger LOGGER = Logger.getLogger(GetObject.class);

    private GetObject() {
    }

    public static GetObject getInstance() {
        return GetObjectHolder.INSTANCE;
    }

    private static class GetObjectHolder {

        private static final GetObject INSTANCE = new GetObject();
    }

    @Override
    protected S3Response execute(AmazonS3 amazonS3, S3Request request) throws S3Exception {
        S3Response s3Response = new S3Response();
        s3Response.setTimestamp(new Date());
        ByteArrayOutputStream fos = null;
        try {
            S3Object s3Object = amazonS3.getObject(Commons.getInstance().getGetObjectRequest(request.getBucketName(), request.getKey()));
            S3ObjectInputStream s3is = s3Object.getObjectContent();
            fos = new ByteArrayOutputStream();
            byte[] readBuf = new byte[BUFFER_SIZE];
            int readLen = 0;
            while ((readLen = s3is.read(readBuf)) > 0) {
                fos.write(readBuf, 0, readLen);
            }

            if (s3Object.getTaggingCount() != null && s3Object.getTaggingCount() > 0) {
		GetObjectTaggingResult getObjectTaggingResult = amazonS3.getObjectTagging(Commons.getInstance().getGetObjectTaggingRequest(request.getBucketName(), request.getKey()));                
                Map<String, String> tags = new HashMap<>();
                for (Tag tag : getObjectTaggingResult.getTagSet()) {
                    tags.put(tag.getKey(), tag.getValue());
                }
                s3Response.setTags(tags);
            }
            s3Response.setFile(fos.toByteArray());
            s3Response.setSuccess(true);
        } catch (SdkClientException | IOException ex) {
            throw new S3Exception(ex);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    LOGGER.warn("Some ByteArrayOutputStream stubbornly refused to close on finally");
                }
            }
        }
        return s3Response;
    }
}
