package utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * PasswordUtil
 * Utility class for hashing and verifying passwords using bcrypt.
 *
 * Author: Brandon Maloney
 * Date: 2025-12-07
 */
public class PasswordUtil {

    /**
     * Default private constructor to prevent instantiation.
     */
    private PasswordUtil() {
        // Private constructor to prevent instantiation
    }

    // Higher = safer but slower; 10–12 is recommended for assignments
    private static final int WORKLOAD = 12;

    /**
     * Hash password using bcrypt with internally generated salt.
     * @param plainTextPassword plaintext password to be hashed
     * 
     * @return hashed password
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(WORKLOAD));
    }

    /**
     * Verify plaintext password against hashed one.
     * @param plainTextPassword plaintext password to verify
     * @param storedHash hashed password from storage
     * 
     * @return true if match, false otherwise
     */
    public static boolean verifyPassword(String plainTextPassword, String storedHash) {
        if (storedHash == null || !storedHash.startsWith("$2")) {
            throw new IllegalArgumentException("Invalid bcrypt hash format");
        }

        return BCrypt.checkpw(plainTextPassword, storedHash);
    }
}