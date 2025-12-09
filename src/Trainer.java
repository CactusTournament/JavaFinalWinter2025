package JavaFinalWinter2025.src;

public class Trainer extends User {

    public Trainer(int id, String username, String passwordHash, String email, String phone, String address) {
        super(id, username, passwordHash, email, phone, address, "Trainer");
    }
}
