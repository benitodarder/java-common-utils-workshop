package local.tin.tests.java.common.utils.use.cases;

import org.apache.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Usage: java -cp java-common-utils-use-cases.1.0-jar-with-dependencies.jar  <Class and arguments>");
        LOGGER.info("Classes:");
        LOGGER.info("\tlocal.tin.tests.java.common.utils.use.cases.HttpClient <Properties file>");
    }

}
