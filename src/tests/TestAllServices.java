package tests;

import dao.*;
import java.sql.SQLException;
import java.util.List;
import models.*;
import services.*;
import utils.DatabaseConnection;

public class TestAllServices {

    public static void main(String[] args) throws SQLException {
        testUserService();
        testMembershipService();
        testWorkoutClassService();
        testGymMerchService();
        System.out.println("All Service tests completed.");
    }

    private static String unique() {
        return String.valueOf(System.currentTimeMillis());
    }

    // ------------------------------
    // USER SERVICE TEST
    // ------------------------------
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

    // ------------------------------
    // MEMBERSHIP SERVICE TEST
    // ------------------------------
    private static void testMembershipService() throws SQLException {
        System.out.println("\nTesting MembershipService...");

        MembershipDAO membershipDAO = new MembershipDAO();
        MembershipService membershipService = new MembershipService(membershipDAO);

        MemberDAO memberDAO = new MemberDAO();
        String email = "member" + unique() + "@test.com";
        Member member = new Member(0, "Member One", "hashedPass", email, "5556667777", "102 Main St");

        boolean created = memberDAO.createMember(member);
        System.out.println("Member created: " + created);

        // Get member ID dynamically
        int memberId = memberDAO.getAllMembers().stream()
                .filter(m -> m.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Member not found"))
                .getUserId();

        Membership membership = new Membership(0, "Standard", "Basic membership", 49.99, memberId);
        membershipService.addMembership(membership);
        int membershipId = membership.getMembershipID();
        System.out.println("Membership added: " + membership);

        membershipService.getMembership(membershipId);
        System.out.println("Membership retrieved: " + membership);

        List<Membership> memberships = membershipService.getAllMemberships();
        System.out.println("All Memberships: " + memberships);

        // Update dynamically
        Membership updatedMembership = new Membership(membershipId, "Premium", "Premium membership", 79.99, memberId);
        membershipService.updateMembership(updatedMembership);
        System.out.println("Membership updated: " + updatedMembership);

        // Add and delete another membership dynamically
        Membership vipMembership = new Membership(0, "VIP", "VIP membership", 99.99, memberId);
        membershipService.addMembership(vipMembership);
        int vipId = vipMembership.getMembershipID();
        membershipService.deleteMembership(vipId);
        System.out.println("Membership deleted: " + vipId);
    }

    // ------------------------------
    // WORKOUT CLASS SERVICE TEST
    // ------------------------------
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

            // int trainerId = trainerDAO.getAllTrainers().stream()
            //         .filter(t -> t.getEmail().equals(email))
            //         .findFirst()
            //         .orElseThrow(() -> new RuntimeException("Trainer not found"))
            //         .getUserId();

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

    // ------------------------------
    // GYM MERCH SERVICE TEST
    // ------------------------------
    private static void testGymMerchService() throws SQLException {
        System.out.println("\nTesting GymMerchService...");

        GymMerchDAO merchDAO = new GymMerchDAO();
        GymMerchService merchService = new GymMerchService(merchDAO);

        GymMerch merch = new GymMerch(0, "T-Shirt", "Clothing", 19.99, 10);
        merchService.addMerch(merch);
        int merchId = merch.getMerchID();
        System.out.println("GymMerch added: " + merch);

        merchService.getMerch(merchId);
        System.out.println("GymMerch retrieved: " + merch);

        List<GymMerch> allMerch = merchService.getAllMerch();
        System.out.println("All GymMerch: " + allMerch);

        // Update
        GymMerch merchUpdate = new GymMerch(merchId, "Hoodie", "Clothing", 39.99, 5);
        merchService.updateMerch(merchUpdate);
        System.out.println("GymMerch updated: " + merchUpdate);

        // Add and delete another merch
        GymMerch merchDelete = new GymMerch(0, "Water Bottle", "Accessory", 9.99, 20);
        merchService.addMerch(merchDelete);
        int merchDeleteId = merchDelete.getMerchID();
        merchService.deleteMerch(merchDeleteId);
        System.out.println("GymMerch deleted: " + merchDeleteId);
    }
}
