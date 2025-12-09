package JavaFinalWinter2025.src;

/**
 * Admin class representing an administrator in the system.
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class Admin extends User {
    public Admin(int id, String username, String passwordHash, String email, String phone, String address) {
        super(id, username, passwordHash, email, phone, address, "Admin");
    }
}
