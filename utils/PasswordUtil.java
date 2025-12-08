package JavaFinalWinter2025.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * PasswordUtil
 * Utility class for hashing and verifying passwords using bcrypt.
 *
 * Author: Brandon Maloney
 * Date: 2025-12-07
 */
public class PasswordUtil {

    // Higher = safer but slower; 10–12 is recommended for assignments
    private static final int WORKLOAD = 12;

    /**
     * Hash password using bcrypt with internally generated salt.
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(WORKLOAD));
    }

    /**
     * Verify plaintext password against hashed one.
     */
    public static boolean verifyPassword(String plainTextPassword, String storedHash) {
        if (storedHash == null || !storedHash.startsWith("$2")) {
            throw new IllegalArgumentException("Invalid bcrypt hash format");
        }

        return BCrypt.checkpw(plainTextPassword, storedHash);
    }
}
