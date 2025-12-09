package JavaFinalWinter2025.services;


import JavaFinalWinter2025.dao.UserDAO;
import JavaFinalWinter2025.src.User;
import java.util.List;
import JavaFinalWinter2025.utils.PasswordUtil;

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
     */
    private UserDAO userDAO;

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
        user.setPasswordHash(PasswordUtil.hashPassword(user.getPasswordHash()));
        userDAO.createUser(user);
    }

    /**
     * Login a user by verifying their credentials.
     * @param username username
     * @param password password
     * @return User object if login is successful; null otherwise.
     */
    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return user; // login successful
        }
        return null; // login failed
    }

    /**
     * Retrieve all users from the database.
     * @return List of User objects.
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}

