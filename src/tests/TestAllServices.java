package tests;

import dao.*;
import java.sql.SQLException;
import java.util.List;
import models.*;
import services.*;
import utils.DatabaseConnection;

/**
 * TestAllServices is a console-based test class for all service layers
 * in the Gym Management System. It runs tests for UserService, MembershipService,
 * WorkoutClassService, and GymMerchService.
 * 
 * Each service test covers CRUD operations and basic functionality.
 * 
 * @author Abiodun Magret
 */
public class TestAllServices {

    /**
     * Main method to run all service tests.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            testUserService();
            testMembershipService();
            testWorkoutClassService();
            testGymMerchService();
            System.out.println("All Service tests completed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a unique string based on current timestamp.
     * 
     * @return unique string
     */
    private static String unique() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * Tests the UserService CRUD operations.
     * 
     * @throws SQLException if a database error occurs
     */
    private static void testUserService() throws SQLException {
        System.out.println("\nTesting UserService...");

        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);

        String email = "user" + unique() + "@test.com";
        User user = new User(0, "User One", "password123", email, "7778889999", "789 Main St", "Member");

        // Register
        userService.registerUser(user);
        System.out.println("User registered: " + user);

        // Login
        User loggedIn = userService.login(user.getUserName(), "password123");
        System.out.println("User logged in: " + (loggedIn != null));

        // Fetch all users
        List<User> users = userService.getAllUsers();
        System.out.println("All Users: " + users);
    }


    /**
     * Tests the MembershipService CRUD operations.
     * 
     * @throws SQLException if a database error occurs
     */
    private static void testMembershipService() throws SQLException {
        System.out.println("\nTesting MembershipService...");

        MembershipDAO membershipDAO = new MembershipDAO();
        MembershipService membershipService = new MembershipService(membershipDAO);

        MemberDAO memberDAO = new MemberDAO();
        String email = "member" + unique() + "@test.com";
        Member member = new Member(0, "Member One", "hashedPass", email, "5556667777", "102 Main St");

        boolean created = memberDAO.createMember(member);
        System.out.println("Member created: " + created);

        int memberId = memberDAO.getAllMembers().get(0).getUserId();
        System.out.println("Member ID for Membership tests: " + memberId);

        Membership membership = new Membership(0, "Standard", "Basic membership", 49.99, memberId);
        membershipService.addMembership(membership);
        int membershipId = membership.getMembershipID();
        System.out.println("Membership added: " + membership);

        membershipService.getMembership(membershipId);
        System.out.println("Membership retrieved: " + membership);

        List<Membership> memberships = membershipService.getAllMemberships();
        System.out.println("All Memberships: " + memberships);

        // Update
        Membership updatedMembership = new Membership(membershipId, "Premium", "Premium membership", 79.99, memberId);
        membershipService.updateMembership(updatedMembership);
        System.out.println("Membership updated: " + updatedMembership);

        // Add and delete another membership
        Membership vipMembership = new Membership(0, "VIP", "VIP membership", 99.99, memberId);
        membershipService.addMembership(vipMembership);
        int vipId = vipMembership.getMembershipID();
        membershipService.deleteMembership(vipId);
        System.out.println("Membership deleted: " + vipId);
    }

    /**
     * Tests the WorkoutClassService CRUD operations.
     * 
     * @throws SQLException if a database error occurs
     */
    private static void testWorkoutClassService() throws SQLException {
        System.out.println("\nTesting WorkoutClassService...");

        try (var conn = DatabaseConnection.getcon()) {
            WorkoutClassDAO wcDAO = new WorkoutClassDAO(conn);
            WorkoutClassService wcService = new WorkoutClassService(wcDAO);

            TrainerDAO trainerDAO = new TrainerDAO();
            String email = "trainer" + unique() + "@test.com";
            Trainer trainer = new Trainer(0, "Trainer FK", "103 Main St", "8889990000", email, "pass");

            boolean trainerCreated = trainerDAO.createTrainer(trainer);
            System.out.println("Trainer created for WorkoutClass: " + trainerCreated);

            int trainerId = trainerDAO.getAllTrainers().get(0).getUserId();

            WorkoutClass wc = new WorkoutClass(0, "Yoga", "Morning Yoga Class", trainerId);
            wcService.createWorkoutClass(wc);
            int wcId = wc.getWorkoutClassID();
            System.out.println("WorkoutClass added: " + wc);

            wcService.getWorkoutClass(wcId);
            System.out.println("WorkoutClass retrieved: " + wc);

            List<WorkoutClass> classes = wcService.getAllWorkoutClasses();
            System.out.println("All WorkoutClasses: " + classes);

            // Update
            WorkoutClass wcUpdate = new WorkoutClass(wcId, "Pilates", "Evening Pilates Class", trainerId);
            wcService.updateWorkoutClass(wcUpdate);
            System.out.println("WorkoutClass updated: " + wcUpdate);

            // Delete
            WorkoutClass wcDelete = new WorkoutClass(0, "Spin", "Afternoon Spin Class", trainerId);
            wcService.createWorkoutClass(wcDelete);
            int wcDeleteId = wcDelete.getWorkoutClassID();
            wcService.deleteWorkoutClass(wcDeleteId);
            System.out.println("WorkoutClass deleted: " + wcDeleteId);
        }
    }

    /**
     * Tests the GymMerchService CRUD operations.
     * 
     * @throws SQLException if a database error occurs
     */
    private static void testGymMerchService() throws SQLException {
        System.out.println("\nTesting GymMerchService...");

        GymMerchDAO merchDAO = new GymMerchDAO();
        GymMerchService merchService = new GymMerchService(merchDAO);

        GymMerch merch = new GymMerch(0, "T-Shirt", "Clothing", 19.99, 10);
        merchService.addMerch(merch);
        int merchId = merchDAO.getAllGymMerch().get(0).getMerchID();
        System.out.println("GymMerch added: " + merch);

        merchService.getMerch(merchId);
        System.out.println("GymMerch retrieved: " + merch);

        List<GymMerch> allMerch = merchService.getAllMerch();
        System.out.println("All GymMerch: " + allMerch);

        // Update
        GymMerch updatedMerch = new GymMerch(merchId, "Hoodie", "Clothing", 29.99, 15);
        merchService.updateMerch(updatedMerch);
        System.out.println("GymMerch updated: " + updatedMerch);

        // Add and delete another merch
        GymMerch newMerch = new GymMerch(merchId, "Water Bottle", "Accessory", 9.99, 20);
        merchService.addMerch(newMerch);
        int newMerchId = newMerch.getMerchID();
        merchService.deleteMerch(newMerchId);
        System.out.println("GymMerch deleted: " + newMerchId);
    }
}