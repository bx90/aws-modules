package util.misc;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @author bsun
 */
public class MiscUtil {
    public static byte[] encrypt(String content) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            byte[] result = cipher.doFinal(content.getBytes());
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param content
     * @return
     */
    public static String decrypt(byte[] content) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String key = "12345678";

    /**
     * Convert String to byte array
     *
     * @param str
     * @return byte array
     */
    public static byte[] StringtoByte(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String[] byteValues = str.split("%");
        byte[] bytes = new byte[byteValues.length];
        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        return bytes;
    }

    /**
     * Convert byte array to String
     *
     * @param bytes
     * @return String
     */
    public static String BytetoString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(b).append("%");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
