package JavaFinalWinter2025;

import JavaFinalWinter2025.dao.*;
import JavaFinalWinter2025.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestAllDAO {

    public static void main(String[] args) {
        try {
            testTrainerDAO();
        } catch (SQLException e) {
            System.err.println("TrainerDAO test failed: " + e.getMessage());
        }

        try {
            testAdminDAO();
        } catch (SQLException e) {
            System.err.println("AdminDAO test failed: " + e.getMessage());
        }

        try {
            testUserDAO();
        } catch (SQLException e) {
            System.err.println("UserDAO test failed: " + e.getMessage());
        }

        try {
            testMemberDAO();
        } catch (SQLException e) {
            System.err.println("MemberDAO test failed: " + e.getMessage());
        }

        try {
            testGymMerchDAO();
        } catch (SQLException e) {
            System.err.println("GymMerchDAO test failed: " + e.getMessage());
        }

        try {
            testMembershipDAO();
        } catch (SQLException e) {
            System.err.println("MembershipDAO test failed: " + e.getMessage());
        }

        try {
            testWorkoutClassDAO();
        } catch (SQLException e) {
            System.err.println("WorkoutClassDAO test failed: " + e.getMessage());
        }

        System.out.println("All DAO tests completed.");
    }


    private static String unique() {
        return String.valueOf(System.currentTimeMillis());
    }

    private static void testTrainerDAO() throws SQLException {
        System.out.println("\nTesting TrainerDAO...");
        TrainerDAO dao = new TrainerDAO();
        String email = "trainer" + unique() + "@test.com";
        Trainer trainer = new Trainer(0, "Trainer One", "123 Main St", "1112223333", email, "password123");
        boolean created = dao.createTrainer(trainer);
        System.out.println("Trainer created: " + created);

        List<Trainer> trainers = dao.getAllTrainers();
        System.out.println("All Trainers: " + trainers);

        if (!trainers.isEmpty()) {
            Trainer t = trainers.get(0);
            t.setUserName("Updated Trainer");
            boolean updated = dao.updateTrainer(t);
            System.out.println("Trainer updated: " + updated);

            boolean deleted = dao.deleteTrainer(t.getUserId());
            System.out.println("Trainer deleted: " + deleted);
        }
    }

    private static void testAdminDAO() throws SQLException {
        System.out.println("\nTesting AdminDAO...");
        AdminDAO dao = new AdminDAO();
        String email = "admin" + unique() + "@test.com";
        Admin admin = new Admin(0, "Admin One", "passwordHash", email, "4445556666", "456 Main St");
        boolean created = dao.createAdmin(admin);
        System.out.println("Admin created: " + created);

        List<Admin> admins = dao.getAllAdmins();
        System.out.println("All Admins: " + admins);

        if (!admins.isEmpty()) {
            Admin a = admins.get(0);
            a.setUserName("Updated Admin");
            boolean updated = dao.updateAdmin(a);
            System.out.println("Admin updated: " + updated);

            boolean deleted = dao.deleteAdmin(a.getUserId());
            System.out.println("Admin deleted: " + deleted);
        }
    }

    private static void testUserDAO() throws SQLException {
        System.out.println("\nTesting UserDAO...");
        UserDAO dao = new UserDAO();
        String email = "user" + unique() + "@test.com";
        User user = new User(0, "User One", "hashedPass", email, "7778889999", "789 Main St", "Member");
        boolean created = dao.createUser(user);
        System.out.println("User created: " + created);

        List<User> users = dao.getAllUsers();
        System.out.println("All Users: " + users);

        if (!users.isEmpty()) {
            User u = users.get(0);
            u.setUserName("Updated User");
            boolean updated = dao.updateUser(u);
            System.out.println("User updated: " + updated);

            boolean deleted = dao.deleteUser(u.getUserId());
            System.out.println("User deleted: " + deleted);
        }
    }

    private static void testMemberDAO() throws SQLException {
        System.out.println("\nTesting MemberDAO...");
        MemberDAO dao = new MemberDAO();
        String email = "member" + unique() + "@test.com";
        Member member = new Member(0, "Member One", "hashedPass", email, "2223334444", "101 Main St");
        boolean created = dao.createMember(member);
        System.out.println("Member created: " + created);

        List<Member> members = dao.getAllMembers();
        System.out.println("All Members: " + members);

        if (!members.isEmpty()) {
            Member m = members.get(0);
            m.setUserName("Updated Member");
            boolean updated = dao.updateMember(m);
            System.out.println("Member updated: " + updated);

            boolean deleted = dao.deleteMember(m.getUserId());
            System.out.println("Member deleted: " + deleted);
        }
    }

    private static void testGymMerchDAO() throws SQLException {
        System.out.println("\nTesting GymMerchDAO...");
        GymMerchDAO dao = new GymMerchDAO();
        GymMerch merch = new GymMerch(0, "T-Shirt", "Clothing", 19.99, 10);
        boolean created = dao.addGymMerch(merch);
        System.out.println("GymMerch created: " + created);

        List<GymMerch> merchList = dao.getAllGymMerch();
        System.out.println("All GymMerch: " + merchList);

        if (!merchList.isEmpty()) {
            GymMerch g = merchList.get(0);
            g.setMerchName("Updated T-Shirt");
            boolean updated = dao.updateGymMerch(g);
            System.out.println("GymMerch updated: " + updated);

            boolean deleted = dao.deleteGymMerch(g.getMerchID());
            System.out.println("GymMerch deleted: " + deleted);
        }
    }

    private static void testMembershipDAO() throws SQLException {
        System.out.println("\nTesting MembershipDAO...");
        MembershipDAO dao = new MembershipDAO();

        // Ensure a member exists for FK
        MemberDAO memberDAO = new MemberDAO();
        String email = "member" + unique() + "@test.com";
        Member member = new Member(0, "Member FK", "hashedPass", email, "5556667777", "102 Main St");
        memberDAO.createMember(member);
        int memberId = memberDAO.getAllMembers().get(0).getUserId();

        Membership membership = new Membership(0, "Standard", "Basic membership", 49.99, memberId);
        boolean created = dao.createMembership(membership);
        System.out.println("Membership created: " + created);

        List<Membership> memberships = dao.getAllMemberships();
        System.out.println("All Memberships: " + memberships);

        if (!memberships.isEmpty()) {
            Membership m = memberships.get(0);
            m.setMembershipType("Updated Standard");
            boolean updated = dao.updateMembership(m);
            System.out.println("Membership updated: " + updated);

            boolean deleted = dao.deleteMembership(m.getMembershipID());
            System.out.println("Membership deleted: " + deleted);
        }
    }

    private static void testWorkoutClassDAO() throws SQLException {
        System.out.println("\nTesting WorkoutClassDAO...");
        try (Connection conn = DatabaseConnection.getcon()) {
            WorkoutClassDAO dao = new WorkoutClassDAO(conn);

            // Ensure a trainer exists for FK
            TrainerDAO trainerDAO = new TrainerDAO();
            String email = "trainer" + unique() + "@test.com";
            Trainer trainer = new Trainer(0, "Trainer FK", "103 Main St", "8889990000", email, "pass");
            trainerDAO.createTrainer(trainer);
            int trainerId = trainerDAO.getAllTrainers().get(0).getUserId();

            WorkoutClass wc = new WorkoutClass(0, "Yoga", "Morning Yoga Class", trainerId);
            dao.addWorkoutClass(wc);
            List<WorkoutClass> classes = dao.getAllWorkoutClasses();
            System.out.println("All WorkoutClasses: " + classes);
        }
    }
}
