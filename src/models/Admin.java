package models;

/**
 * Admin class representing an administrator in the system.
 * 
 * Author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class Admin extends User {
    /**
     * Constructor to initialize an Admin object.
     * 
     * @param id           Unique identifier for the admin.
     * @param username     Username of the admin.
     * @param passwordHash Hashed password of the admin.
     * @param email        Email address of the admin.
     * @param phone        Phone number of the admin.
     * @param address      Physical address of the admin.
     */
    public Admin(int id, String username, String passwordHash, String email, String phone, String address) {
        super(id, username, passwordHash, email, phone, address, "Admin");
    }
}