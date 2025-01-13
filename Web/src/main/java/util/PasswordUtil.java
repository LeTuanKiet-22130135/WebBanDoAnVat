package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    
    public static boolean comparePassword(String password, String storedHash) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	// Split the stored hash into salt, iteration count, and hashed password
        String[] parts = storedHash.split("\\$");
        
        String encodedSalt = parts[0];
        int storedIterations = Integer.parseInt(parts[1]);
        String storedHashValue = parts[2];

        // Convert the salt back from hex
        byte[] salt = hexToBytes(encodedSalt);

        // Hash the input password with the stored salt and iteration count
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.reset();
        md.update(salt);

        byte[] hashedPassword = password.getBytes("UTF-8");
        for (int i = 0; i < storedIterations; i++) {
            hashedPassword = md.digest(hashedPassword);
        }

        // Convert the generated hash to hex
        String generatedHash = bytesToHex(hashedPassword);

        // Compare the generated hash with the stored hash
        return generatedHash.equals(storedHashValue);
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
    
    public static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                   + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }
}
