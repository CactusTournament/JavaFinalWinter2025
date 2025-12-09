package JavaFinalWinter2025;

import JavaFinalWinter2025.dao.UserDAO;
import JavaFinalWinter2025.src.User;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        List<User> users = dao.getAllUsers();

        System.out.println("Users retrieved: " + users.size());

        for (User u : users) {
            System.out.println(u);
        }
    }
}
