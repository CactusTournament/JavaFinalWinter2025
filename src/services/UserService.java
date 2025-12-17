package services;

import dao.*;
import java.util.List;
import java.util.logging.Logger;
import models.User;
import utils.LoggerUtil;
import utils.PasswordUtil;

/**
 * UserService
 * Service class for handling user-related operations such as registration and
 * login.
 * Utilizes UserDAO for database interactions and PasswordUtil for password
 * hashing.
 * 
 * Author: Abiodun Magret Oyedele
 * Updated: 2025-12-15
 */
public class UserService {

    /** UserDAO instance for database operations */
    private UserDAO userDAO;

    /** Logger for logging events */
    private static final Logger logger = LoggerUtil.getLogger();

    // DAOs for specific user roles
    private TrainerDAO trainerDAO = new TrainerDAO();
    private MemberDAO memberDAO = new MemberDAO();
    private AdminDAO adminDAO = new AdminDAO();

    /**
     * Constructor to initialize UserService with a UserDAO instance.
     * 
     * @param userDAO The UserDAO instance to use.
     */
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Normalize role input to match expected casing.
     * 
     * @param role input role string
     * @return normalized role or null if invalid
     */
    private String normalizeRole(String role) {
        if (role == null) return null;
    
        return switch (role.trim().toLowerCase()) {
            case "admin" -> "Admin";
            case "trainer" -> "Trainer";
            case "member" -> "Member";
            default -> null;
        };
    }
    
    /**
     * Login a user by verifying their credentials.
     * 
     * @param username username
     * @param password password
     * @return User object if login is successful; null otherwise.
     */
    public User login(String username, String password) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                logger.info("Login successful for user: " + user.getUserName());
                return user;
            }
            logger.info("Login failed for username: " + username);
            return null;
        } catch (Exception e) {
            logger.severe("Logging failed during login attempt: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieve all users from the database.
     * 
     * @return List of User objects.
     */
    public List<User> getAllUsers() {
        try {
            List<User> users = userDAO.getAllUsers();
            if (users != null) {
                logger.info("Retrieved " + users.size() + " users from the database: " + users);
                return users;
            } else {
                logger.info("No users found in the database.");
                return null;
            }
        } catch (Exception e) {
            logger.severe("Logging failed during retrieving all users: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieve a Trainer by user ID.
     * A Trainer is a User with userRole = 'Trainer'.
     * 
     * @param userId trainer user ID
     * @return User if found and role is Trainer
     * @throws RuntimeException if not found or not a trainer
     */
    public User getTrainerById(int userId) {
        User user = userDAO.getUserById(userId);

        if (user != null && "Trainer".equals(user.getUserRole())) {
            return user;
        }

        throw new RuntimeException("Trainer not found");
    }

    /**
     * Retrieve a Member by user ID.
     * A Member is a User with userRole = 'Member'.
     * 
     * @param userId member user ID
     * @return User if found and role is Member
     * @throws RuntimeException if not found or not a member
     */
    public User getMemberById(int userId) {
        User user = userDAO.getUserById(userId);

        if (user != null && "Member".equals(user.getUserRole())) {
            return user;
        }

        throw new RuntimeException("Member not found");
    }

    /**
     * Retrieve a User by their email.
     * 
     * @param email The email of the user to retrieve.
     * @return The User object if found, null otherwise.
     */
    public User getUserByEmail(String email) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                logger.info("User retrieved successfully by email: " + email);
            } else {
                logger.info("No user found with email: " + email);
            }
            return user;
        } catch (Exception e) {
            logger.severe("Logging failed during retrieving user by email: " + e.getMessage());
            return null;
        }
    }
}
