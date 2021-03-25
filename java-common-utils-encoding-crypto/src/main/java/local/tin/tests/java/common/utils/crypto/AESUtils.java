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


/**
 *
 * @author benitodarder
 */
    
public class AESUtils {

    public static final int ENCRYPTION_BITS = 128;
    public static final String ENCRYPTION_TYPE = "AES";

    private AESUtils() {
    }

    public static AESUtils getInstance() {
        return AESUtilsHolder.INSTANCE;
    }

    private static class AESUtilsHolder {

        private static final AESUtils INSTANCE = new AESUtils();
    }

    /**
     * Generates a new secret key to store
     *
     * @return SecretKey
     * @throws local.tin.tests.java.common.utils.crypto.AESException
     */
    public SecretKey getNewSecretKey() throws AESException {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ENCRYPTION_TYPE);
            generator.init(ENCRYPTION_BITS);
            return generator.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            throw new AESException(ex);
        }
    }

    /**
     * Regenerates the secret key from the byte array.
     *
     * @param secretKeyArray as byte[]
     * @return SecretKey
     */
    public SecretKey getSecretKeyFromByteArray(byte[] secretKeyArray) {
        return new SecretKeySpec(secretKeyArray, 0, secretKeyArray.length, ENCRYPTION_TYPE);
    }

    /**
     * Encrypts given byte[] with the corresponding secret.
     *
     * @param message as byte[]
     * @param secretKey as SecretKey
     * @return byte[]
     * @throws local.tin.tests.java.common.utils.crypto.AESException
     */
    public byte[] encrypt(byte[] message, SecretKey secretKey) throws AESException {
        try {
            Cipher aesCipher = Cipher.getInstance(ENCRYPTION_TYPE);
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return aesCipher.doFinal(message);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            throw new AESException(ex);
        }
    }

    /**
     * Decrypts the given encrypted message with the corresponding secret.
     *
     * @param encryptedMessage as byte[]
     * @param secretKey as SecretKey
     * @return byte[]
     * @throws local.tin.tests.java.common.utils.crypto.AESException
     */
    public byte[] decrypt(byte[] encryptedMessage, SecretKey secretKey) throws AESException {
        try {
            Cipher aesCipher = Cipher.getInstance(ENCRYPTION_TYPE);
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
            return aesCipher.doFinal(encryptedMessage);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            throw new AESException(ex);
        }
    }

    /**
     * Encrypts given byte[] with the corresponding secret as byte[].
     *
     * @param message as byte[]
     * @param secretKeyArray
     * @return byte[]
     * @throws local.tin.tests.java.common.utils.crypto.AESException
     */
    public byte[] encrypt(byte[] message, byte[] secretKeyArray) throws AESException {
        return encrypt(message, getSecretKeyFromByteArray(secretKeyArray));
    }

    /**
     * Decrypts given byte[] with the corresponding secret as byte[].
     *
     * @param message as byte[]
     * @param secretKeyArray
     * @return byte[]
     * @throws local.tin.tests.java.common.utils.crypto.AESException
     */
    public byte[] decrypt(byte[] message, byte[] secretKeyArray) throws AESException {
        return decrypt(message, getSecretKeyFromByteArray(secretKeyArray));
    }

    /**
     * Encrypts given byte[] with the corresponding secret as Base64 encoded
     * String.
     *
     * @param message as byte[]
     * @param secretKeyString as Base64 encoded String
     * @return byte[]
     * @throws local.tin.tests.java.common.utils.crypto.AESException
     */
    public byte[] encrypt(byte[] message, String secretKeyString) throws AESException {
        byte[] secretKeyArray = Base64.getInstance().decode(secretKeyString);
        return encrypt(message, getSecretKeyFromByteArray(secretKeyArray));
    }

    /**
     * Decrypts given byte[] with the corresponding secret as byte[].
     *
     * @param message as byte[]
     * @param secretKeyString as Base64 encoded String
     * @return byte[]
     * @throws local.tin.tests.java.common.utils.crypto.AESException
     */
    public byte[] decrypt(byte[] message, String secretKeyString) throws AESException {
        byte[] secretKeyArray = Base64.getInstance().decode(secretKeyString);
        return decrypt(message, getSecretKeyFromByteArray(secretKeyArray));
    }
}


