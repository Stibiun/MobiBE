package com.mobi5.webapp.crypto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptoUtil {

    private static final Logger logger = LogManager.getLogger();
    private static final char[] toHex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] passPhrase = "Ncc!7*1@aWarg3nS1gma".toCharArray();
    private static final byte[] salt = "c@rAlh0C*b9c3t@".getBytes();
    private static final SecretKey secret;

    static {
        SecretKey tempSecret = null;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passPhrase, salt, 65536, 128);//,
            SecretKey specSecret = factory.generateSecret(spec);
            tempSecret = new SecretKeySpec(specSecret.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | RuntimeException ex) {
            logger.error("CRITICAL INITIALIZATION ERROR!!!!", ex);
        }
        secret = tempSecret;
    }

    public static String getEncryptedData(String data) {
        String encodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            byte[] ciphertext = cipher.doFinal(data.getBytes("UTF-8"));
            encodedBytes = Base64.encodeBytes(ciphertext);
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | RuntimeException ex) {
            logger.error("Fail encrypiting data", ex);
        }
        return encodedBytes;
    }

    public static String getDecryptedData(String data) {
        String decodedData = null;
        try {
            byte[] decodedBytes = Base64.decode(data);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] ciphertext = cipher.doFinal(decodedBytes);
            decodedData = new String(ciphertext, "UTF-8");
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | RuntimeException ex) {
            logger.error("Fail decrypiting data", ex);
        }
        return decodedData;
    }

    public static String toHexString(byte b[]) {
        int pos = 0;
        char[] c = new char[b.length * 2];
        for (int i = 0; i < b.length; i++) {
            c[pos++] = toHex[(b[i] >> 4) & 0x0F];
            c[pos++] = toHex[b[i] & 0x0f];
        }
        return new String(c);
    }

    public static String H(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return toHexString(digest.digest(data.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            // shouldn't happen
            throw new RuntimeException("Failed to instantiate an MD5 algorithm", ex);
        }
    }

    public static String KD(String secret, String data) {
        return H(secret + ":" + data);
    }
}
