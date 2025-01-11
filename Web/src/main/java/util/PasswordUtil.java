package util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordUtil {

    private static final int SALT_LENGTH = 16; // 16 bytes = 128 bits
    private static final String ALGORITHM = "SHA-512";
    private static final int iterations = 1;

    public static String hashPassword(String password) throws Exception {
        // Generate random salt
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        // Hash the password with salt and iterations
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.reset();
        md.update(salt);

        byte[] hashedPassword = password.getBytes("UTF-8");
        for (int i = 0; i < iterations; i++) {
            hashedPassword = md.digest(hashedPassword);
        }

        // Convert salt and hash to hex
        String encodedSalt = bytesToHex(salt);
        String encodedHash = bytesToHex(hashedPassword);

        // Return in salt$iterationCount$hashedPassword format
        return encodedSalt + "$" + iterations + "$" + encodedHash;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
