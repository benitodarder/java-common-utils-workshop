package local.tin.tests.java.common.utils.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import local.tin.tests.java.common.utils.encoding.Base64;
import javax.crypto.SecretKey;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author developer01
 */
public class AESUtilsTest {

    public static final String MESSAGE = "message";

    @Test
    public void regenerated_secretKey_matches_original() throws Exception {
        SecretKey secretKey = AESUtils.getInstance().getNewSecretKey();

        SecretKey regenerated = AESUtils.getInstance().getSecretKeyFromByteArray(secretKey.getEncoded());

        assertThat(regenerated.getEncoded(), equalTo(secretKey.getEncoded()));
        assertThat(regenerated.getAlgorithm(), equalTo(secretKey.getAlgorithm()));
        assertThat(regenerated.getFormat(), equalTo(secretKey.getFormat()));
        assertThat(regenerated.isDestroyed(), equalTo(secretKey.isDestroyed()));

    }

    @Test
    public void decryption_successfull_with_same_secret() throws AESException {
        SecretKey secretKey = AESUtils.getInstance().getNewSecretKey();
        byte[] encrypted = AESUtils.getInstance().encrypt(MESSAGE.getBytes(), secretKey);

        byte[] decrypted = AESUtils.getInstance().decrypt(encrypted, secretKey);

        assertThat(new String(decrypted), equalTo(MESSAGE));
    }

    @Test(expected = AESException.class)
    public void decryption_fails_with_different_secret() throws AESException {
        SecretKey secretKey = AESUtils.getInstance().getNewSecretKey();
        byte[] encrypted = AESUtils.getInstance().encrypt(MESSAGE.getBytes(), secretKey);
        secretKey = AESUtils.getInstance().getNewSecretKey();

        AESUtils.getInstance().decrypt(encrypted, secretKey);

    }    

    @Test
    public void decryption_successfull_with_same_secret_as_byte_array_in_decryption() throws AESException {
        SecretKey secretKey = AESUtils.getInstance().getNewSecretKey();
        byte[] encrypted = AESUtils.getInstance().encrypt(MESSAGE.getBytes(), secretKey);

        byte[] decrypted = AESUtils.getInstance().decrypt(encrypted, secretKey.getEncoded());

        assertThat(new String(decrypted), equalTo(MESSAGE));
    }    
    
    @Test
    public void decryption_successfull_with_same_secret_as_byte_array_in_both_methods() throws AESException {
        SecretKey secretKey = AESUtils.getInstance().getNewSecretKey();
        byte[] encrypted = AESUtils.getInstance().encrypt(MESSAGE.getBytes(), secretKey.getEncoded());

        byte[] decrypted = AESUtils.getInstance().decrypt(encrypted, secretKey.getEncoded());

        assertThat(new String(decrypted), equalTo(MESSAGE));
    }    
    
    @Test
    public void decryption_successfull_with_same_secret_as_byte_array_in_encryption() throws AESException {
        SecretKey secretKey = AESUtils.getInstance().getNewSecretKey();
        byte[] encrypted = AESUtils.getInstance().encrypt(MESSAGE.getBytes(), secretKey.getEncoded());

        byte[] decrypted = AESUtils.getInstance().decrypt(encrypted, secretKey);

        assertThat(new String(decrypted), equalTo(MESSAGE));
    }      
    

    @Test
    public void decryption_successfull_with_same_secret_as_string_in_both_methods_from_java_base64() throws AESException {
        SecretKey secretKey = AESUtils.getInstance().getNewSecretKey();
        String encoded = java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());
        byte[] encrypted = AESUtils.getInstance().encrypt(MESSAGE.getBytes(), encoded);

        byte[] decrypted = AESUtils.getInstance().decrypt(encrypted, encoded);

        assertThat(new String(decrypted), equalTo(MESSAGE));
    }      
    
    @Test
    public void decryption_successfull_with_same_secret_as_string_in_both_methods_from_custom_base64() throws AESException {
        byte[] encrypted = AESUtils.getInstance().encrypt(MESSAGE.getBytes(), "zvtNk3HlxV+/Yi0oeoIuag==");

        byte[] decrypted = AESUtils.getInstance().decrypt(encrypted, "zvtNk3HlxV+/Yi0oeoIuag==");

        assertThat(new String(decrypted), equalTo(MESSAGE));
    } 
    
}

