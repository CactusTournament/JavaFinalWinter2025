package JavaFinalWinter2025.services;


import JavaFinalWinter2025.dao.UserDAO;
import JavaFinalWinter2025.src.User;
import java.util.List;
import JavaFinalWinter2025.utils.PasswordUtil;
import JavaFinalWinter2025.utils.LoggerUtil;
import java.util.logging.Logger;

/**
 * UserService
 * Service class for handling user-related operations such as registration and login.
 * Utilizes UserDAO for database interactions and PasswordUtil for password hashing.
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-09
 */
public class UserService {
    /**
     * UserDAO instance for database operations.
     * Logger for logging events.
     */
    private UserDAO userDAO;
    private static final Logger logger = LoggerUtil.getLogger();

    /**
     * Constructor to initialize UserService with a UserDAO instance.
     * 
     * @param userDAO The UserDAO instance to use.
     */
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Register a new user by hashing their password and storing their details.
     * @param user The User object containing user details.
     */
    public void registerUser(User user) {
        try{
            user.setPasswordHash(PasswordUtil.hashPassword(user.getPasswordHash()));
            userDAO.createUser(user);

            logger.info("User registered successfully: " + user.getUserName());
        } catch (Exception e) {
            logger.severe("Logging failed during user registration: " + e.getMessage());
        }
    }

    /**
     * Login a user by verifying their credentials.
     * @param username username
     * @param password password
     * @return User object if login is successful; null otherwise.
     */
    public User login(String username, String password) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                logger.info("Login successful for user: " + user.getUserName());
                return user; // login successful
            }
            logger.info("Login failed for username: " + username);
            return null; // login failed
        } catch (Exception e) {
            logger.severe("Logging failed during login attempt: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieve all users from the database.
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
}

