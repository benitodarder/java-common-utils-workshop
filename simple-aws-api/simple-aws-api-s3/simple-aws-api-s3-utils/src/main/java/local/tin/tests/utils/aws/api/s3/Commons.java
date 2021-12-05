package local.tin.tests.utils.aws.api.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingRequest;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import org.apache.log4j.Logger;

/**
 *
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
     * Returns the corresponding AmazonS3 given the following details:
     *
     * <ul>
     * <li>Access key</li>
     * <li>Secret</li>
     * <li>Region</li>
     * </ul>
     *
     * Located in a separated class in order to ease unit testing as
     * AmazonS3ClientBuilder is a final class.
     *
     * @param request as local.tin.tests.aws.api.model.Request
     * @return AmazonS3
     * @throws local.tin.tests.utils.aws.api.model.s3.S3Exception
     */
    public AmazonS3 getAmazonS3(Request request) throws S3Exception {
        try {
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(request.getAccessKeyId(), request.getSecret());
            AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
            return AmazonS3ClientBuilder.standard().withCredentials(awsStaticCredentialsProvider).withRegion(request.getRegion()).build();
        } catch (Throwable t) {
            throw new S3Exception(t);
        }
    }

    /**
     * Returns a new DeleteObjectRequest with given arguments.
     *
     * @param bucketName as String
     * @param key as String
     * @return DeleteObjectRequest
     */
    public DeleteObjectRequest getDeleteObjectRequest(String bucketName, String key) {
        return new DeleteObjectRequest(bucketName, key);
    }

    public GetObjectRequest getGetObjectRequest(String bucketName, String key) {
        return new GetObjectRequest(bucketName, key);
    }

    public GetObjectTaggingRequest getGetObjectTaggingRequest(String bucketName, String key) {
        return new GetObjectTaggingRequest(bucketName, key);
    }

    public PutObjectRequest getPutObjectRequest(String bucketName, String key, File file) {
        return new PutObjectRequest(bucketName, key, file);
    }

    /**
     * Converts the given tags map to an ObjectTagging.
     * 
     * @param tagsMap as HashMap
     * @return ObjectTagging
     */
    public ObjectTagging getObjectTagging(Map<String, String> tagsMap) {
        List<Tag> listTags = new ArrayList<>();
        for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
            listTags.add(new Tag(entry.getKey(), entry.getValue()));
        }
        return new ObjectTagging(listTags);
    }
}
