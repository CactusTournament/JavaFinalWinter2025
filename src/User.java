package JavaFinalWinter2025.src;

/**
 * User class representing a user in the system.
 * Fields correspond to the Users table in the database.
 * 
 * Fields:
 * - userId: Unique identifier for the user
 * - userName: Name of the user
 * - userAddress: Address of the user
 * - userPhoneNumber: Phone number of the user
 * - userRole: Role of the user ('Admin', 'Trainer', 'Member')
 * - passwordHash: Hashed password for security
 * - email: Email address of the user
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class User {
    private int userId;
    private String userName;
    private String userAddress;
    private String userPhoneNumber;
    private String userRole; // 'Admin', 'Trainer', 'Member'
    private String passwordHash;
    private String email;

    /**
     * Constructor to initialize a User object.
     * 
     * @param userId Unique identifier for the user
     * @param userName Name of the user
     * @param passwordHash Hashed password for security
     * @param email Email address of the user
     * @param userPhoneNumber Phone number of the user
     * @param userAddress Address of the user
     * @param userRole Role of the user ('Admin', 'Trainer', 'Member')
     */
    public User(int userId, String userName,  String passwordHash, String email, String userPhoneNumber, String userAddress, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhoneNumber = userPhoneNumber;
        this.userRole = userRole;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    /**
     * Getter for userId.
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for userId.
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for userName.
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for userName.
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for userAddress.
     * @return the userAddress
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * Setter for userAddress.
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * Getter for userPhoneNumber.
     * @return the userPhoneNumber
     */
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    /**
     * Setter for userPhoneNumber.
     * @param userPhoneNumber the userPhoneNumber to set
     */
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    /**
     * Getter for userRole.
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Setter for userRole.
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * Getter for passwordHash.
     * @return the passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Setter for passwordHash.
     * @param passwordHash the passwordHash to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Getter for email.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Override toString method for better representation.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userRole='" + userRole + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}