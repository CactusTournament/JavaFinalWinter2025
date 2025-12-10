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

        userService.login(user.getUserName(), user.getPasswordHash());
        System.out.println("User logged in: " + user.getUserName());

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

        // Ensures a member exists
        MemberDAO memberDAO = new MemberDAO();
        String email = "member" + unique() + "@test.com";
        Member member = new Member(0, "Member One", "hashedPass", email, "5556667777", "102 Main St");
        memberDAO.createMember(member);
        int memberId = memberDAO.getAllMembers().get(0).getUserId();

        Membership membership = new Membership(0, "Standard", "Basic membership", 49.99, memberId);
        Membership membership2 = new Membership(0, "Premium", "Premium membership", 79.99, memberId);
        Membership membership3 = new Membership(0, "VIP", "VIP membership", 99.99, memberId);

        membershipService.addMembership(membership);
        System.out.println("Membership added: " + membership);

        membershipService.getMembership(membership.getMembershipID());
        System.out.println("Membership retrieved: " + membership);

        List<Membership> memberships = membershipService.getAllMemberships();
        System.out.println("All Memberships: " + memberships);

        membershipService.updateMembership(membership2);
        System.out.println("Membership updated: " + membership2);

        membershipService.addMembership(membership3);
        System.out.println("Membership added: " + membership);

        membershipService.deleteMembership(membership3.getMembershipID());
        System.out.println("Membership deleted: " + membership3.getMembershipID());

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

            // Ensures a trainer exists
            TrainerDAO trainerDAO = new TrainerDAO();
            String email = "trainer" + unique() + "@test.com";
            Trainer trainer = new Trainer(0, "Trainer FK", "103 Main St", "8889990000", email, "pass");
            trainerDAO.createTrainer(trainer);
            int trainerId = trainerDAO.getAllTrainers().get(0).getUserId();

            WorkoutClass wc = new WorkoutClass(0, "Yoga", "Morning Yoga Class", trainerId);
            WorkoutClass wcToUpdate = new WorkoutClass(0, "Pilates", "Evening Pilates Class", trainerId);
            WorkoutClass wcToDelete = new WorkoutClass(0, "Spin", "Afternoon Spin Class", trainerId);

            wcService.addWorkoutClass(wc);
            System.out.println("WorkoutClass added: " + wc);

            wcService.getWorkoutClass(wc.getWorkoutClassID());
            System.out.println("WorkoutClass retrieved: " + wc);

            List<WorkoutClass> classes = wcService.getAllWorkoutClasses();
            System.out.println("All WorkoutClasses: " + classes);

            wcService.updateWorkoutClass(wcToUpdate);
            System.out.println("WorkoutClass updated: " + wcToUpdate);

            wcService.addWorkoutClass(wcToDelete);
            System.out.println("WorkoutClass added: " + wcToDelete);
            wcService.deleteWorkoutClass(wcToDelete.getWorkoutClassID());
            System.out.println("WorkoutClass deleted: " + wcToDelete.getWorkoutClassID());

            if (!classes.isEmpty()) {
                WorkoutClass c = classes.get(0);
                c.setWorkoutClassType("Updated Yoga");
                wcService.updateWorkoutClass(c);
                System.out.println("WorkoutClass updated: " + c);

                wcService.deleteWorkoutClass(c.getWorkoutClassID());
                System.out.println("WorkoutClass deleted: " + c.getWorkoutClassID());
            }
        }
    }

    private static void testGymMerchService() throws SQLException {
        System.out.println("\nTesting GymMerchService...");

        GymMerchDAO merchDAO = new GymMerchDAO();
        GymMerchService merchService = new GymMerchService(merchDAO);

        GymMerch merch = new GymMerch(0, "T-Shirt", "Clothing", 19.99, 10);
        GymMerch merchToUpdate = new GymMerch(0, "Hoodie", "Clothing", 39.99, 5);
        GymMerch merchToDelete = new GymMerch(0, "Water Bottle", "Accessory", 9.99, 20);

        merchService.addMerch(merch);
        System.out.println("GymMerch added: " + merch);

        merchService.getMerch(merch.getMerchID());
        System.out.println("GymMerch retrieved: " + merch);

        List<GymMerch> allMerch = merchService.getAllMerch();
        System.out.println("All GymMerch: " + allMerch);

        merchService.updateMerch(merchToUpdate);
        System.out.println("GymMerch updated: " + merchToUpdate);

        merchService.addMerch(merchToDelete);
        System.out.println("GymMerch added: " + merchToDelete);
        merchService.deleteMerch(merchToDelete.getMerchID());
        System.out.println("GymMerch deleted: " + merchToDelete.getMerchID());

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

// private static void test
