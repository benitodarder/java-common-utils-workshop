package local.tin.tests.java.common.utils.use.cases.aws.iam;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import local.tin.tests.utils.aws.api.model.Request;
import local.tin.tests.utils.aws.api.model.iam.AccessKey;
import local.tin.tests.utils.aws.api.model.iam.IAMException;
import local.tin.tests.utils.file.FileUtils;
import local.tin.tests.utils.http.model.HttpCommonException;
import java.util.logging.Logger;

/**
 *
 * @author benitodarder
 */
public class ListAccessKeys {

    public static final String PROPERTY_ACCESSKEY = "access.key";
    public static final String PROPERTY_SECRETKEY = "secret.key";
    public static final String PROPERTY_REGION = "region";
    private static final Logger LOGGER = Logger.getLogger(ListAccessKeys.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, HttpCommonException, IAMException {
        if (args.length != 1) {
            LOGGER.log(Level.SEVERE, "Usage: java -cp java-common-utils-use-cases.1.0-jar-with-dependencies.jar  local.tin.tests.java.common.utils.use.cases.aws.iam.ListAccessKeys <Properties file>");
            LOGGER.log(Level.SEVERE, "Properties file:");
            LOGGER.log(Level.SEVERE, "\taccess.key=<Access key id.>");
            LOGGER.log(Level.SEVERE, "\tsecret.key=<Secret key>");
            LOGGER.log(Level.SEVERE, "\tregion=<Region>");
            System.exit(1);
        } else {
            Properties properties = FileUtils.getInstance().getPropertiesFile(args[0]);
            Request request = new Request();
            request.setAccessKeyId((String) properties.get(PROPERTY_ACCESSKEY));
            request.setSecret((String) properties.get(PROPERTY_SECRETKEY));
            request.setRegion((String) properties.get(PROPERTY_REGION));
            List<AccessKey> accessKeys = local.tin.tests.utils.aws.api.iam.ListAccessKeys.getInstance().getResult(request);
            for (AccessKey accessKey : accessKeys) {
                LOGGER.log(Level.INFO, "Access key id:{0}", accessKey.getAccessKeyId());
                LOGGER.log(Level.INFO, "Secret: {0}", accessKey.getSecret());
                LOGGER.log(Level.INFO, "Status: {0}", accessKey.getStatus());
                LOGGER.log(Level.INFO, "User name:{0}", accessKey.getUserName());
            }
        }
    }

}
