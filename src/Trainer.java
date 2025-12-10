package JavaFinalWinter2025.src;

/**
 * Trainer class representing a trainer in the system.
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class Trainer extends User {

    /**
     * Constructor to initialize a Trainer object.
     * 
     * @param id           Unique identifier for the trainer.
     * @param username     Username of the trainer.
     * @param passwordHash Hashed password of the trainer.
     * @param email        Email address of the trainer.
     * @param phone        Phone number of the trainer.
     * @param address      Physical address of the trainer.
     */
    public Trainer(int id, String username, String passwordHash, String email, String phone, String address) {
        super(id, username, passwordHash, email, phone, address, "Trainer");
    }
}
