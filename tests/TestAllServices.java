package JavaFinalWinter2025.tests;

import JavaFinalWinter2025.dao.*;
import JavaFinalWinter2025.src.*;
import JavaFinalWinter2025.services.*;
import java.sql.SQLException;
import java.util.List;
import JavaFinalWinter2025.utils.DatabaseConnection;

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

    private static void testUserService() throws SQLException {
        System.out.println("\nTesting UserService...");

        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);

        String email = "user" + unique() + "@test.com";
        User user = new User(0, "User One", "password123", email, "7778889999", "789 Main St", "Member");

        userService.registerUser(user);
        System.out.println("User registered: " + user);

        List<User> users = userService.getAllUsers();
        System.out.println("All Users: " + users);

        if (!users.isEmpty()) {
            User u = users.get(0);
            User loggedIn = userService.login(u.getUserName(), "password123");
            System.out.println("Login successful: " + (loggedIn != null));
        }
    }

    private static void testMembershipService() throws SQLException {
        System.out.println("\nTesting MembershipService...");

        MembershipDAO membershipDAO = new MembershipDAO();
        MembershipService membershipService = new MembershipService(membershipDAO);

        // Ensure a member exists
        MemberDAO memberDAO = new MemberDAO();
        String email = "member" + unique() + "@test.com";
        Member member = new Member(0, "Member One", "hashedPass", email, "5556667777", "102 Main St");
        memberDAO.createMember(member);
        int memberId = memberDAO.getAllMembers().get(0).getUserId();

        Membership membership = new Membership(0, "Standard", "Basic membership", 49.99, memberId);
        membershipService.addMembership(membership);
        System.out.println("Membership added: " + membership);

        List<Membership> memberships = membershipService.getAllMemberships();
        System.out.println("All Memberships: " + memberships);

        if (!memberships.isEmpty()) {
            Membership m = memberships.get(0);
            m.setMembershipType("Updated Standard");
            membershipService.updateMembership(m);
            System.out.println("Membership updated: " + m);

            membershipService.deleteMembership(m.getMembershipID());
            System.out.println("Membership deleted: " + m.getMembershipID());
        }
    }

    private static void testWorkoutClassService() throws SQLException {
        System.out.println("\nTesting WorkoutClassService...");

        try (var conn = DatabaseConnection.getcon()) {
            WorkoutClassDAO wcDAO = new WorkoutClassDAO(conn);
            WorkoutClassService wcService = new WorkoutClassService(wcDAO);

            // Ensure a trainer exists
            TrainerDAO trainerDAO = new TrainerDAO();
            String email = "trainer" + unique() + "@test.com";
            Trainer trainer = new Trainer(0, "Trainer FK", "103 Main St", "8889990000", email, "pass");
            trainerDAO.createTrainer(trainer);
            int trainerId = trainerDAO.getAllTrainers().get(0).getUserId();

            WorkoutClass wc = new WorkoutClass(0, "Yoga", "Morning Yoga Class", trainerId);
            wcService.addWorkoutClass(wc);
            System.out.println("WorkoutClass added: " + wc);

            List<WorkoutClass> classes = wcService.getAllWorkoutClasses();
            System.out.println("All WorkoutClasses: " + classes);

            // if (!classes.isEmpty()) {
            //     WorkoutClass c = classes.get(0);
            //     c.setClassName("Updated Yoga");
            //     wcService.updateWorkoutClass(c);
            //     System.out.println("WorkoutClass updated: " + c);

            //     wcService.deleteWorkoutClass(c.getWorkoutClassID());
            //     System.out.println("WorkoutClass deleted: " + c.getWorkoutClassID());
            // }
        }
    }

    private static void testGymMerchService() throws SQLException {
        System.out.println("\nTesting GymMerchService...");

        GymMerchDAO merchDAO = new GymMerchDAO();
        GymMerchService merchService = new GymMerchService(merchDAO);

        GymMerch merch = new GymMerch(0, "T-Shirt", "Clothing", 19.99, 10);
        merchService.addMerch(merch);
        System.out.println("GymMerch added: " + merch);

        List<GymMerch> allMerch = merchService.getAllMerch();
        System.out.println("All GymMerch: " + allMerch);

        if (!allMerch.isEmpty()) {
            GymMerch m = allMerch.get(0);
            m.setMerchName("Updated T-Shirt");
            merchService.updateMerch(m);
            System.out.println("GymMerch updated: " + m);

            merchService.deleteMerch(m.getMerchID());
            System.out.println("GymMerch deleted: " + m.getMerchID());
        }
    }
}

