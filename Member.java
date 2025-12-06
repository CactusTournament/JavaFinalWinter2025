package JavaFinalWinter2025;

public class Member extends User {
    public Member(int id, String username, String passwordHash, String email, String phone, String address) {
        super(id, username, passwordHash, email, phone, address, "Member");
    }
}
