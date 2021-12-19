package local.tin.tests.java.common.utils.use.cases.aws.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
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
public class GetObject {

    public static final String PROPERTY_ACCESSKEY = "access.key";
    public static final String PROPERTY_SECRETKEY = "secret.key";
    public static final String PROPERTY_REGION = "region";
    private static final Logger LOGGER = Logger.getLogger(GetObject.class);

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws local.tin.tests.utils.http.model.HttpCommonException
     * @throws local.tin.tests.utils.aws.api.model.iam.IAMException
     * @throws local.tin.tests.utils.aws.api.model.s3.S3Exception
     */
    public static void main(String[] args) throws IOException, HttpCommonException, IAMException, S3Exception {
        if (args.length != 1) {
            LOGGER.error("Usage: java -cp java-common-utils-use-cases.1.0-jar-with-dependencies.jar  local.tin.tests.java.common.utils.use.cases.aws.s3.GetObject <Properties file>");
            LOGGER.error("Properties file:");
            LOGGER.error("\taccess.key=<Access key id.>");
            LOGGER.error("\tsecret.key=<Secret key>");
            LOGGER.error("\tregion=<Region>");
            LOGGER.error("\tbucket.name=<Bucket name>");
            LOGGER.error("\tobject.key=<Object key>");
            LOGGER.error("\tfile.path=<File path to save>");
            System.exit(1);
        } else {
            Properties properties = FileUtils.getInstance().getPropertiesFile(args[0]);
            S3Request request = new S3Request();
            request.setAccessKeyId((String) properties.get(PROPERTY_ACCESSKEY));
            request.setSecret((String) properties.get(PROPERTY_SECRETKEY));
            request.setRegion((String) properties.get(PROPERTY_REGION));
            request.setBucketName((String) properties.get("bucket.name"));
            request.setKey((String) properties.get("object.key"));
            request.setTimestamp(System.currentTimeMillis());
            if (properties.get("tags") != null) {
                String[] tagsAsList = ((String) properties.get("tags")).split(",");
                for (int i = 0; i < tagsAsList.length - 2; i = i + 2) {
                    request.getTags().put(tagsAsList[i], tagsAsList[i + 1]);
                }
            }
            S3Response s3Response = local.tin.tests.utils.aws.api.s3.GetObject.getInstance().getResult(request);
            LOGGER.info("AWS Response: " + s3Response.getMessage());
            if (s3Response.getFile() != null) {
                Path path = Paths.get((String) properties.get("file.path"));
                Files.write(path, s3Response.getFile());
                LOGGER.info("Object saved.");
            } else {
                LOGGER.info("No object to save.");
            }

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
