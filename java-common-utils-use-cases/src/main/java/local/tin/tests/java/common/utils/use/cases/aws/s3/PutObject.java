package local.tin.tests.java.common.utils.use.cases.aws.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import local.tin.tests.java.common.utils.use.cases.aws.iam.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.iam.AccessKey;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import local.tin.tests.utils.aws.api.model.s3.S3Exception;
import local.tin.tests.utils.aws.api.model.s3.S3Request;
import local.tin.tests.utils.aws.api.model.s3.S3Response;
import local.tin.tests.utils.file.FileUtils;
import local.tin.tests.utils.http.model.HttpCommonException;
import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class PutObject {

    public static final String PROPERTY_ACCESSKEY = "access.key";
    public static final String PROPERTY_SECRETKEY = "secret.key";
    public static final String PROPERTY_REGION = "region";
    private static final Logger LOGGER = Logger.getLogger(PutObject.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, HttpCommonException, IAMException, S3Exception {
        if (args.length != 1) {
            LOGGER.error("Usage: java -cp java-common-utils-use-cases.1.0-jar-with-dependencies.jar  local.tin.tests.java.common.utils.use.cases.aws.s3.PutObject <Properties file>");
            LOGGER.error("Properties file:");
            LOGGER.error("\taccess.key=<Access key id.>");
            LOGGER.error("\tsecret.key=<Secret key>");
            LOGGER.error("\tregion=<Region>");
            LOGGER.error("\tbucket.name=<Bucket name>");
            LOGGER.error("\tobject.key=<Object key>");
            LOGGER.error("\tfile.path=<File path to upload>");
            LOGGER.error("\ttags=<Comma separated tag/value list>");
            System.exit(1);
        } else {
            Properties properties = FileUtils.getInstance().getPropertiesFile(args[0]);
            S3Request request = new S3Request();
            request.setAccessKeyId((String) properties.get(PROPERTY_ACCESSKEY));
            request.setSecret((String) properties.get(PROPERTY_SECRETKEY));
            request.setRegion((String) properties.get(PROPERTY_REGION));
            request.setFile(getByteArray((String) properties.get("file.path")));
            request.setBucketName((String) properties.get("bucket.name"));
            request.setKey((String) properties.get("object.key"));
            request.setTimestamp(System.currentTimeMillis());
            if (properties.get("tags") != null) {
                String[] tagsAsList = ((String)properties.get("tags")).split(",");
                for (int i = 0; i < tagsAsList.length - 2; i = i + 2) {
                    request.getTags().put(tagsAsList[i], tagsAsList[i+1]);
                }
            }
            S3Response s3Response = local.tin.tests.utils.aws.api.s3.PutObject.getInstance().getResult(request);
            LOGGER.info("AWS Response: " + s3Response.getMessage());
        }
    }

    private static byte[] getByteArray(String filePath) throws FileNotFoundException, IOException {

        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        byte[] byteArray = new byte[(int) file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(byteArray);
            return byteArray;
        } finally {
            fileInputStream.close();

        }
    }

}
