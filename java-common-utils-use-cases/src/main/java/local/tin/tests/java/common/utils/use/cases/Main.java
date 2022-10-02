package local.tin.tests.java.common.utils.use.cases;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benitodarder
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "Usage: java -cp java-common-utils-use-cases.1.0-jar-with-dependencies.jar  <Class and arguments>");
        LOGGER.log(Level.INFO, "Classes:");
        LOGGER.log(Level.INFO, "\tlocal.tin.tests.java.common.utils.use.cases.http.HttpClient <Properties file>");
        LOGGER.log(Level.INFO, "\tlocal.tin.tests.java.common.utils.use.cases.crypto.EncryptFile <Secret key file> <Content file> <Output file>");
        LOGGER.log(Level.INFO, "\tlocal.tin.tests.java.common.utils.use.cases.crypto.DecryptFile <Secret key file> <encrypted content file> <Output file>");
        LOGGER.log(Level.INFO, "\tlocal.tin.tests.java.common.utils.use.cases.aws.iam.ListAccessKeys <Properties file>");
        LOGGER.log(Level.INFO, "\tlocal.tin.tests.java.common.utils.use.cases.aws.s3.GetObject <Properties file>");
        LOGGER.log(Level.INFO, "\tlocal.tin.tests.java.common.utils.use.cases.aws.s3.PutObject <Properties file>");
        LOGGER.log(Level.INFO, "\tlocal.tin.tests.java.common.utils.use.cases.http.Get <Properties file>");
        LOGGER.log(Level.INFO, "\tlocal.tin.tests.java.common.utils.use.cases.http.Post <Properties file>");

    }

}
