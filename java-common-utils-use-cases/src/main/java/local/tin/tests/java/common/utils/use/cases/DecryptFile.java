package local.tin.tests.java.common.utils.use.cases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import local.tin.tests.java.common.utils.crypto.AESException;
import local.tin.tests.java.common.utils.crypto.AESUtils;
import org.apache.log4j.Logger;


/**
 *
 * @author benitodarder
 */
public class DecryptFile {

    private static final Logger LOGGER = Logger.getLogger(DecryptFile.class);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, AESException {
        if (args.length != 3) {
            LOGGER.error("Usage: java -cp java-common-utils-use-cases.<version>-jar-with-dependencies.jar  local.tin.tests.java.common.utils.use.cases.DecryptFile <Secret key file> <Encrypted content file> <Output file>");
            System.exit(1);
        } else {
            File secretKeyFile = new File(args[0]);
            byte[] secretKey = Files.readAllBytes(secretKeyFile.toPath());
            File contentFile = new File(args[1]);
            byte[] content = Files.readAllBytes(contentFile.toPath());
            byte[] compressedContent = AESUtils.getInstance().decrypt(content, secretKey);
            File compressedFile = new File(args[2]);
            FileOutputStream fileOutputStream = new FileOutputStream(compressedFile);
            fileOutputStream.write(compressedContent);
            LOGGER.info("Decryption done...");
        }
    }

}
