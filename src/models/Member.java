package models;

/**
 * Member class representing a member in the system.
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class Member extends User {
    /**
     * Constructor to initialize a Member object.
     * 
     * @param id           Unique identifier for the member.
     * @param username     Username of the member.
     * @param passwordHash Hashed password of the member.
     * @param email        Email address of the member.
     * @param phone        Phone number of the member.
     * @param address      Physical address of the member.
     */
    public Member(int id, String username, String passwordHash, String email, String phone, String address) {
        super(id, username, passwordHash, email, phone, address, "Member");
    }
}