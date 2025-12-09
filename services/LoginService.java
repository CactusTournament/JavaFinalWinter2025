package JavaFinalWinter2025.services;

import JavaFinalWinter2025.dao.MemberDAO;

public class LoginService {

    private MemberDAO memberDAO;

    public LoginService() {
        this.memberDAO = new MemberDAO();  // Initialize MemberDAO
    }

    // Method to handle user login
    public boolean loginUser(String email, String password) {
        return memberDAO.login(email, password);  // Use MemberDAO's login method
    }
}
