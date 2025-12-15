package tests;

import dao.*;
import java.sql.SQLException;
import java.util.List;
import models.*;

/**
 * TestAllDAO class to test all DAO functionalities.
 * Author: Brandon Maloney
 * Date: 2025-12-08
 */
public class TestAllDAO {

    /**
     * Default constructor for TestAllDAO.
     */
    public TestAllDAO() {
        // No initialization required for now
    }

    /**
     * Main method to run all DAO tests.
     * 
     * @param args Command line arguments
     * 
     */
    public static void main(String[] args) {
        try { testTrainerDAO(); } catch (SQLException e) { System.err.println("TrainerDAO test failed: " + e.getMessage()); }
        try { testAdminDAO(); } catch (SQLException e) { System.err.println("AdminDAO test failed: " + e.getMessage()); }
        try { testUserDAO(); } catch (SQLException e) { System.err.println("UserDAO test failed: " + e.getMessage()); }
        try { testMemberDAO(); } catch (SQLException e) { System.err.println("MemberDAO test failed: " + e.getMessage()); }
        try { testGymMerchDAO(); } catch (SQLException e) { System.err.println("GymMerchDAO test failed: " + e.getMessage()); }
        try { testMembershipDAO(); } catch (SQLException e) { System.err.println("MembershipDAO test failed: " + e.getMessage()); }
        try { testWorkoutClassDAO(); } catch (SQLException e) { System.err.println("WorkoutClassDAO test failed: " + e.getMessage()); }

        // Optional cleanup for test users
        if (args.length > 0 && args[0].equalsIgnoreCase("--cleanup")) {
            cleanupTestUsers();
        }

        System.out.println("All DAO tests completed.");
    }

    /**
     * Generate a unique email for testing.
     * @param prefix The prefix for the email.
     * @return A unique email string.
     */
    private static String uniqueEmail(String prefix) {
        return prefix + System.currentTimeMillis() + "@test.com";
    }

    /**
     * Generate a unique phone number for testing.
     * @return A unique phone number string.
     */
    private static String uniquePhone() {
        return String.valueOf(1000000000 + System.currentTimeMillis() % 9000000000L);
    }

    /**
     * Test TrainerDAO functionalities.
     * @throws SQLException if a database access error occurs.
     * 
     */
    private static void testTrainerDAO() throws SQLException {
        System.out.println("\nTesting TrainerDAO...");
        TrainerDAO dao = new TrainerDAO();
        String email = uniqueEmail("trainer");
        String phone = uniquePhone();
        Trainer trainer = new Trainer(0, "Trainer One", "123 Main St", phone, email, "password123");
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

    /**
     * Test AdminDAO functionalities.
     * @throws SQLException if a database access error occurs.
     */
    private static void testAdminDAO() throws SQLException {
        System.out.println("\nTesting AdminDAO...");
        AdminDAO dao = new AdminDAO();
        String email = uniqueEmail("admin");
        String phone = uniquePhone();
        Admin admin = new Admin(0, "Admin One", "passwordHash", email, phone, "456 Main St");
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

    /**
     * Test UserDAO functionalities.
     * @throws SQLException if a database access error occurs.
     */
    private static void testUserDAO() throws SQLException {
        System.out.println("\nTesting UserDAO...");
        UserDAO dao = new UserDAO();
        String email = uniqueEmail("user");
        String phone = uniquePhone();
        User user = new User(0, "User One", "hashedPass", email, phone, "789 Main St", "Member");
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

    /**
     * Test MemberDAO functionalities.
     * @throws SQLException if a database access error occurs.
     */
    private static void testMemberDAO() throws SQLException {
        System.out.println("\nTesting MemberDAO...");
        MemberDAO dao = new MemberDAO();
        String email = uniqueEmail("member");
        String phone = uniquePhone();
        Member member = new Member(0, "Member One", "hashedPass", email, phone, "101 Main St");
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

    /**
     * Test GymMerchDAO functionalities.
     * @throws SQLException if a database access error occurs.
     */
    private static void testGymMerchDAO() throws SQLException {
        System.out.println("\nTesting GymMerchDAO...");
        GymMerchDAO dao = new GymMerchDAO();

        GymMerch merch = new GymMerch(0, "T-Shirt", "Clothing", 19.99, 10);
        boolean created = dao.addGymMerch(merch);
        System.out.println("GymMerch created: " + created);

        List<GymMerch> merchList = dao.getAllGymMerch();
        GymMerch createdMerch = merchList.get(merchList.size() - 1);

        createdMerch.setMerchName("Updated T-Shirt");
        boolean updated = dao.updateGymMerch(createdMerch);
        System.out.println("GymMerch updated: " + updated);

        boolean deleted = dao.deleteGymMerch(createdMerch.getMerchID());
        System.out.println("GymMerch deleted: " + deleted);
    }

    /**
     * Test MembershipDAO functionalities.
     * @throws SQLException if a database access error occurs.
     */
    private static void testMembershipDAO() throws SQLException {
        System.out.println("\nTesting MembershipDAO...");
        MembershipDAO dao = new MembershipDAO();
        MemberDAO memberDAO = new MemberDAO();

        String memberEmail = uniqueEmail("memberMS");
        String memberPhone = uniquePhone();
        Member tempMember = new Member(0, "Temp Member", "hashedPass", memberEmail, memberPhone, "456 Gym St");
        memberDAO.createMember(tempMember);

        List<Member> members = memberDAO.getAllMembers();
        int memberId = members.get(members.size() - 1).getUserId();

        Membership membership = new Membership(0, "Premium", "All access membership", 99.99, memberId);
        boolean created = dao.createMembership(membership);
        System.out.println("Membership created: " + created);

        dao.deleteMembership(membership.getMembershipID());
        memberDAO.deleteMember(memberId);
    }

    /**
     * Test WorkoutClassDAO functionalities.
     * @throws SQLException if a database access error occurs.
     */
    private static void testWorkoutClassDAO() throws SQLException {
        System.out.println("\nTesting WorkoutClassDAO...");

        TrainerDAO trainerDAO = new TrainerDAO();
        String trainerEmail = uniqueEmail("trainerWC");
        String trainerPhone = uniquePhone();
        Trainer tempTrainer = new Trainer(0, "Temp Trainer", "123 Gym St", trainerPhone, trainerEmail, "password123");
        boolean trainerCreated = trainerDAO.createTrainer(tempTrainer);
        System.out.println("Trainer created for WorkoutClass: " + trainerCreated);

        List<Trainer> trainers = trainerDAO.getAllTrainers();
        int trainerId = trainers.get(trainers.size() - 1).getUserId();

        WorkoutClassDAO dao = new WorkoutClassDAO();
        WorkoutClass workout = new WorkoutClass(0, "Yoga", "Morning session", trainerId);

        boolean created = dao.createWorkoutClass(workout);
        System.out.println("WorkoutClass added: " + workout + " | Success: " + created);

        WorkoutClass retrieved = dao.getWorkoutClassById(workout.getWorkoutClassID());
        System.out.println("WorkoutClass retrieved: " + retrieved);

        workout.setWorkoutClassDescription("Updated description");
        boolean updated = dao.updateWorkoutClass(workout);
        System.out.println("WorkoutClass updated: " + workout + " | Success: " + updated);

        boolean deleted = dao.deleteWorkoutClass(workout.getWorkoutClassID());
        System.out.println("WorkoutClass deleted with ID: " + workout.getWorkoutClassID() + " | Success: " + deleted);

        boolean trainerDeleted = trainerDAO.deleteTrainer(trainerId);
        System.out.println("Trainer deleted after test: " + trainerDeleted);
    }

    /**
     * Cleanup test users created during testing.
     */
    private static void cleanupTestUsers() {
        try {
            UserDAO dao = new UserDAO();
            List<User> users = dao.getAllUsers();
            for (User u : users) {
                if (u.getEmail().contains("@test.com")) {
                    boolean deleted = dao.deleteUser(u.getUserId());
                    System.out.println("Deleted test user: " + u + " | Success: " + deleted);
                }
            }
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
}
