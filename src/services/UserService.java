package services;

import dao.*;
import java.util.List;
import java.util.logging.Logger;

import models.Admin;
import models.Trainer;
import models.User;
import models.Member;
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
     * Registers a new user by hashing their password and storing their details.
     *
     * @param username user's username
     * @param email    user's email
     * @param phone    user's phone number
     * @param address  user's address
     * @param role     user's role (Admin, Trainer, Member)
     * @param password user's raw password
     * @return the created User, or null if registration fails
     */
    // public User registerUser(String username,
    //         String email,
    //         String phone,
    //         String address,
    //         String role,
    //         String password) {

    //     try {
    //         String normalizedRole = normalizeRole(role);
    //         if (normalizedRole == null) {
    //             System.out.println("Error: Role must be Admin, Trainer, or Member.");
    //             return null;
    //         }

    //         String hashedPassword = PasswordUtil.hashPassword(password);
    //         User user = null;
    //         boolean created = false;

    //         switch (normalizedRole) {
    //             case "Admin" -> {
    //                 Admin admin = new Admin(0, username, hashedPassword, email, phone, address);
    //                 created = adminDAO.createAdmin(admin);
    //                 user = admin;
    //             }
    //             case "Trainer" -> {
    //                 Trainer trainer = new Trainer(0, username, hashedPassword, email, phone, address);
    //                 created = trainerDAO.createTrainer(trainer);
    //                 user = trainer;
    //             }
    //             case "Member" -> {
    //                 Member member = new Member(0, username, hashedPassword, email, phone, address);
    //                 created = memberDAO.createMember(member);
    //                 user = member;
    //             }
    //         }

    //         if (!created) {
    //             System.out.println("Error: User registration failed.");
    //             return null;
    //         }

    //         logger.info("User registered successfully: " + username);
    //         return user;

    //     } catch (Exception e) {
    //         logger.severe("User registration failed: " + e.getMessage());
    //         throw new RuntimeException("User registration failed", e);
    //     }
    // }

    /**
     * Registers a new user by hashing their password and storing their details.
     *
     * @param user User object containing user details
     */
    public void registerUser(User user) {
        user.setPasswordHash(PasswordUtil.hashPassword(user.getPasswordHash()));
        userDAO.createUser(user);
    }


    // String hashedPassword = PasswordUtil.hashPassword(user.getPasswordHash());
    // String username = user.getUserName();
    // String email = user.getEmail();
    // String phone = user.getUserPhoneNumber();
    // String address = user.getUserAddress();
    // String role = user.getUserRole();

    // // Normalize role casing - keep DB values exactly as allowed
    // String normalizedRole = role.trim().equalsIgnoreCase("admin") ? "Admin"
    // : role.trim().equalsIgnoreCase("trainer") ? "Trainer"
    // : role.trim().equalsIgnoreCase("member") ? "Member"
    // : "";

    // if (normalizedRole.isEmpty()) {
    // System.out.println("Error: Role must be one of Admin, Trainer, or Member.");
    // return;
    // }

    // boolean created = false;
    // switch (normalizedRole) {
    // case "Admin" -> {
    // Admin admin = new Admin(0, username, hashedPassword, email, phone, address);
    // created = adminDAO.createAdmin(admin); // make sure DAO method name matches
    // }
    // case "Trainer" -> {
    // Trainer trainer = new Trainer(0, username, hashedPassword, email, phone,
    // address);
    // created = trainerDAO.createTrainer(trainer);
    // }
    // case "Member" -> {
    // Member member = new Member(0, username, hashedPassword, email, phone,
    // address);
    // created = memberDAO.createMember(member);
    // }
    // }
    // if (!created) {
    // System.out.println("Error: User registration failed.");
    // return;
    // }

    // logger.info("User registered successfully: " + user.getUserName());
    // } catch (Exception e) {
    // logger.severe("Logging failed during user registration: " + e.getMessage());
    // }
    // }

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
