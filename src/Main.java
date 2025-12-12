import dao.*;
import java.util.List;
import java.util.Scanner;
import models.*;
import utils.PasswordUtil;

/**
 * Main
 * Entry point for Gym Management System.
 * Handles user input, validation, and ensures passwords are hashed before DAO operations.
 *
 * Author: Brandon Maloney
 * Updated: 2025-12-12
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final UserDAO userDAO = new UserDAO();
    private static final MemberDAO memberDAO = new MemberDAO();
    private static final TrainerDAO trainerDAO = new TrainerDAO();
    private static final AdminDAO adminDAO = new AdminDAO();
    private static final WorkoutClassDAO workoutClassDAO = new WorkoutClassDAO();
    private static final GymMerchDAO gymMerchDAO = new GymMerchDAO();
    private static final MembershipDAO membershipDAO = new MembershipDAO();

    public static void main(String[] args) {
        System.out.println("Welcome to the Gym Management System!");

        boolean running = true;
        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. User CRUD");
            System.out.println("2. Trainer CRUD");
            System.out.println("3. Member CRUD");
            System.out.println("4. Admin CRUD");
            System.out.println("5. WorkoutClass CRUD");
            System.out.println("6. GymMerch CRUD");
            System.out.println("7. Membership CRUD");
            System.out.println("8. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleUserCRUD();
                case "2" -> handleTrainerCRUD();
                case "3" -> handleMemberCRUD();
                case "4" -> handleAdminCRUD();
                case "5" -> handleWorkoutClassCRUD();
                case "6" -> handleGymMerchCRUD();
                case "7" -> handleMembershipCRUD();
                case "8" -> {
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // USER CRUD
    private static void handleUserCRUD() {
        System.out.println("\nUser Options: 1-Create 2-View 3-Update 4-Delete 5-Back");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> createUser();
            case "2" -> listAllUsers();
            case "3" -> updateUser();
            case "4" -> deleteUser();
            case "5" -> { /* back to main menu */ }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void createUser() {
        // This method is now a registration endpoint that creates Admin/Trainer/Member records
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Enter role (Member/Admin/Trainer): ");
        String role = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            System.out.println("Error: Required fields cannot be empty.");
            return;
        }
        if (!email.contains("@")) {
            System.out.println("Error: Invalid email format.");
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        // Normalize role casing - keep DB values exactly as allowed
        String normalizedRole = role.trim().equalsIgnoreCase("admin") ? "Admin"
                : role.trim().equalsIgnoreCase("trainer") ? "Trainer"
                : role.trim().equalsIgnoreCase("member") ? "Member"
                : "";

        if (normalizedRole.isEmpty()) {
            System.out.println("Error: Role must be one of Admin, Trainer, or Member.");
            return;
        }

        boolean created = false;
        switch (normalizedRole) {
            case "Admin" -> {
                Admin admin = new Admin(0, username, hashedPassword, email, phone, address);
                created = adminDAO.createAdmin(admin); // make sure DAO method name matches
            }
            case "Trainer" -> {
                Trainer trainer = new Trainer(0, username, hashedPassword, email, phone, address);
                created = trainerDAO.createTrainer(trainer);
            }
            case "Member" -> {
                Member member = new Member(0, username, hashedPassword, email, phone, address);
                created = memberDAO.createMember(member);
            }
        }

        System.out.println(created ? normalizedRole + " created successfully." : "Error creating " + normalizedRole + ".");
    }


    private static void listAllUsers() {
        List<User> users = userDAO.getAllUsers();
        if (users.isEmpty()) System.out.println("No users found.");
        else users.forEach(System.out::println);
    }

    private static void updateUser() {
        System.out.print("Enter user ID to update: ");
        int userId = Integer.parseInt(scanner.nextLine());

        User existing = userDAO.getUserById(userId);
        if (existing == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("New username (blank to keep current): ");
        String username = scanner.nextLine().trim();
        System.out.print("New email (blank to keep current): ");
        String email = scanner.nextLine().trim();
        System.out.print("New phone (blank to keep current): ");
        String phone = scanner.nextLine().trim();
        System.out.print("New address (blank to keep current): ");
        String address = scanner.nextLine().trim();
        System.out.print("New role (blank to keep current): ");
        String role = scanner.nextLine().trim();
        System.out.print("New password (blank to keep current): ");
        String password = scanner.nextLine().trim();

        // Use existing values when blanks are provided. (Assumes standard getter names.)
        String finalUsername = username.isEmpty() ? existing.getUserName() : username;
        String finalEmail = email.isEmpty() ? existing.getEmail() : email;
        String finalPhone = phone.isEmpty() ? existing.getUserPhoneNumber() : phone;
        String finalAddress = address.isEmpty() ? existing.getUserAddress() : address;
        String finalRole = role.isEmpty() ? existing.getUserRole() : role;
        String finalPasswordHash = password.isEmpty() ? existing.getPasswordHash() : PasswordUtil.hashPassword(password);

        User updated = new User(existing.getUserId(), finalUsername, finalPasswordHash, finalEmail, finalPhone, finalAddress, finalRole);

        boolean updatedOk = userDAO.updateUser(updated);
        System.out.println(updatedOk ? "User updated successfully." : "Error updating user.");
    }

    private static void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        int userId = Integer.parseInt(scanner.nextLine());

        boolean deleted = userDAO.deleteUser(userId);
        System.out.println(deleted ? "User deleted successfully." : "Error deleting user.");
    }

    // TRAINER CRUD
    private static void handleTrainerCRUD() {
        System.out.println("\nTrainer Options: 1-Create 2-View 3-Update 4-Delete 5-Back");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> createTrainer();
            case "2" -> listAllTrainers();
            case "3" -> updateTrainer();
            case "4" -> deleteTrainer();
            case "5" -> { /* back */ }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void createTrainer() {
        System.out.print("Enter trainer name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Required fields cannot be empty.");
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        Trainer trainer = new Trainer(0, name, hashedPassword, email, phone, address);

        boolean created = trainerDAO.createTrainer(trainer);
        System.out.println(created ? "Trainer created successfully." : "Error creating trainer.");
    }

    private static void listAllTrainers() {
        List<Trainer> trainers = trainerDAO.getAllTrainers();
        if (trainers.isEmpty()) System.out.println("No trainers found.");
        else trainers.forEach(System.out::println);
    }

    private static void updateTrainer() {
        System.out.print("Enter trainer ID to update: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        Trainer existing = trainerDAO.getTrainerById(trainerId);
        if (existing == null) {
            System.out.println("Trainer not found.");
            return;
        }

        System.out.print("New name (blank to keep current): ");
        String name = scanner.nextLine().trim();
        System.out.print("New email (blank to keep current): ");
        String email = scanner.nextLine().trim();
        System.out.print("New phone (blank to keep current): ");
        String phone = scanner.nextLine().trim();
        System.out.print("New address (blank to keep current): ");
        String address = scanner.nextLine().trim();
        System.out.print("New password (blank to keep current): ");
        String password = scanner.nextLine().trim();

        String finalName = name.isEmpty() ? existing.getUserName() : name;
        String finalEmail = email.isEmpty() ? existing.getEmail() : email;
        String finalPhone = phone.isEmpty() ? existing.getUserPhoneNumber() : phone;
        String finalAddress = address.isEmpty() ? existing.getUserAddress() : address;
        String finalPasswordHash = password.isEmpty() ? existing.getPasswordHash() : PasswordUtil.hashPassword(password);

        Trainer updated = new Trainer(existing.getUserId(), finalName, finalPasswordHash, finalEmail, finalPhone, finalAddress);

        boolean updatedOk = trainerDAO.updateTrainer(updated);
        System.out.println(updatedOk ? "Trainer updated successfully." : "Error updating trainer.");
    }

    private static void deleteTrainer() {
        System.out.print("Enter trainer ID to delete: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        boolean deleted = trainerDAO.deleteTrainer(trainerId);
        System.out.println(deleted ? "Trainer deleted successfully." : "Error deleting trainer.");
    }

    // MEMBER CRUD
    private static void handleMemberCRUD() {
        System.out.println("\nMember Options: 1-Create 2-View 3-Update 4-Delete 5-Back");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> createMember();
            case "2" -> listAllMembers();
            case "3" -> updateMember();
            case "4" -> deleteMember();
            case "5" -> { /* back */ }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void createMember() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Required fields cannot be empty.");
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        Member member = new Member(0, name, hashedPassword, email, phone, address);

        boolean created = memberDAO.createMember(member);
        System.out.println(created ? "Member created successfully." : "Error creating member.");
    }

    private static void listAllMembers() {
        List<Member> members = memberDAO.getAllMembers();
        if (members.isEmpty()) System.out.println("No members found.");
        else members.forEach(System.out::println);
    }

    private static void updateMember() {
        System.out.print("Enter member ID to update: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        Member existing = memberDAO.getMemberById(memberId);
        if (existing == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.print("New name (blank to keep current): ");
        String name = scanner.nextLine().trim();
        System.out.print("New email (blank to keep current): ");
        String email = scanner.nextLine().trim();
        System.out.print("New phone (blank to keep current): ");
        String phone = scanner.nextLine().trim();
        System.out.print("New address (blank to keep current): ");
        String address = scanner.nextLine().trim();
        System.out.print("New password (blank to keep current): ");
        String password = scanner.nextLine().trim();

        String finalName = name.isEmpty() ? existing.getUserName() : name;
        String finalEmail = email.isEmpty() ? existing.getEmail() : email;
        String finalPhone = phone.isEmpty() ? existing.getUserPhoneNumber() : phone;
        String finalAddress = address.isEmpty() ? existing.getUserAddress() : address;
        String finalPasswordHash = password.isEmpty() ? existing.getPasswordHash() : PasswordUtil.hashPassword(password);

        Member updated = new Member(existing.getUserId(), finalName, finalPasswordHash, finalEmail, finalPhone, finalAddress);

        boolean updatedOk = memberDAO.updateMember(updated);
        System.out.println(updatedOk ? "Member updated successfully." : "Error updating member.");
    }

    private static void deleteMember() {
        System.out.print("Enter member ID to delete: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        boolean deleted = memberDAO.deleteMember(memberId);
        System.out.println(deleted ? "Member deleted successfully." : "Error deleting member.");
    }

    // ADMIN CRUD
    private static void handleAdminCRUD() {
        System.out.println("\nAdmin Options: 1-Create 2-View 3-Update 4-Delete 5-Back");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> createAdmin();
            case "2" -> listAllAdmins();
            case "3" -> updateAdmin();
            case "4" -> deleteAdmin();
            case "5" -> { /* back */ }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void createAdmin() {
        System.out.print("Enter admin name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Required fields cannot be empty.");
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        Admin admin = new Admin(0, name, hashedPassword, email, phone, address);

        boolean created = adminDAO.createAdmin(admin);
        System.out.println(created ? "Admin created successfully." : "Error creating admin.");
    }

    private static void listAllAdmins() {
        List<Admin> admins = adminDAO.getAllAdmins();
        if (admins.isEmpty()) System.out.println("No admins found.");
        else admins.forEach(System.out::println);
    }

    private static void updateAdmin() {
        System.out.print("Enter admin ID to update: ");
        int adminId = Integer.parseInt(scanner.nextLine());

        Admin existing = adminDAO.getAdminById(adminId);
        if (existing == null) {
            System.out.println("Admin not found.");
            return;
        }

        System.out.print("New name (blank to keep current): ");
        String name = scanner.nextLine().trim();
        System.out.print("New email (blank to keep current): ");
        String email = scanner.nextLine().trim();
        System.out.print("New phone (blank to keep current): ");
        String phone = scanner.nextLine().trim();
        System.out.print("New address (blank to keep current): ");
        String address = scanner.nextLine().trim();
        System.out.print("New password (blank to keep current): ");
        String password = scanner.nextLine().trim();

        String finalName = name.isEmpty() ? existing.getUserName() : name;
        String finalEmail = email.isEmpty() ? existing.getEmail() : email;
        String finalPhone = phone.isEmpty() ? existing.getUserPhoneNumber() : phone;
        String finalAddress = address.isEmpty() ? existing.getUserAddress() : address;
        String finalPasswordHash = password.isEmpty() ? existing.getPasswordHash() : PasswordUtil.hashPassword(password);

        Admin updated = new Admin(existing.getUserId(), finalName, finalPasswordHash, finalEmail, finalPhone, finalAddress);

        boolean updatedOk = adminDAO.updateAdmin(updated);
        System.out.println(updatedOk ? "Admin updated successfully." : "Error updating admin.");
    }

    private static void deleteAdmin() {
        System.out.print("Enter admin ID to delete: ");
        int adminId = Integer.parseInt(scanner.nextLine());

        boolean deleted = adminDAO.deleteAdmin(adminId);
        System.out.println(deleted ? "Admin deleted successfully." : "Error deleting admin.");
    }

    // WORKOUT CLASS CRUD
    private static void handleWorkoutClassCRUD() {
        System.out.println("\nWorkoutClass Options: 1-Create 2-View 3-Update 4-Delete 5-Back");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> createWorkoutClass();
            case "2" -> listAllWorkoutClasses();
            case "3" -> updateWorkoutClass();
            case "4" -> deleteWorkoutClass();
            case "5" -> { /* back */ }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void createWorkoutClass() {
        System.out.print("Enter class name/type: ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter trainer ID: ");
        int trainerId = Integer.parseInt(scanner.nextLine().trim());

        if (type.isEmpty()) {
            System.out.println("Error: Class name required.");
            return;
        }

        WorkoutClass workoutClass = new WorkoutClass(0, type, description, trainerId);

        boolean created = workoutClassDAO.createWorkoutClass(workoutClass);
        System.out.println(created ? "Workout class created successfully." : "Error creating workout class.");
    }

    private static void listAllWorkoutClasses() {
        List<WorkoutClass> classes = workoutClassDAO.getAllWorkoutClasses();
        if (classes.isEmpty()) System.out.println("No workout classes found.");
        else classes.forEach(System.out::println);
    }

    private static void updateWorkoutClass() {
        System.out.print("Enter class ID to update: ");
        int classId = Integer.parseInt(scanner.nextLine());

        WorkoutClass existing = workoutClassDAO.getWorkoutClassById(classId);
        if (existing == null) {
            System.out.println("Workout class not found.");
            return;
        }

        System.out.print("New name/type (blank to keep current): ");
        String type = scanner.nextLine().trim();
        System.out.print("New description (blank to keep current): ");
        String description = scanner.nextLine().trim();
        System.out.print("New trainer ID (blank to keep current): ");
        String trainerInput = scanner.nextLine().trim();

        String finalType = type.isEmpty() ? existing.getWorkoutClassType() : type;
        String finalDescription = description.isEmpty() ? existing.getWorkoutClassDescription() : description;
        int finalTrainerId = trainerInput.isEmpty() ? existing.getTrainerID() : Integer.parseInt(trainerInput);

        WorkoutClass updated = new WorkoutClass(existing.getWorkoutClassID(), finalType, finalDescription, finalTrainerId);

        boolean updatedOk = workoutClassDAO.updateWorkoutClass(updated);
        System.out.println(updatedOk ? "Workout class updated successfully." : "Error updating workout class.");
    }

    private static void deleteWorkoutClass() {
        System.out.print("Enter class ID to delete: ");
        int classId = Integer.parseInt(scanner.nextLine());

        boolean deleted = workoutClassDAO.deleteWorkoutClass(classId);
        System.out.println(deleted ? "Workout class deleted successfully." : "Error deleting workout class.");
    }

    // GYM MERCH CRUD
    private static void handleGymMerchCRUD() {
        System.out.println("\nGymMerch Options: 1-Create 2-View 3-Update 4-Delete 5-Back");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> createGymMerch();
            case "2" -> listAllGymMerch();
            case "3" -> updateGymMerch();
            case "4" -> deleteGymMerch();
            case "5" -> { /* back */ }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void createGymMerch() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter item type: ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter quantity in stock: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        GymMerch merch = new GymMerch(0, name, type, price, qty);

        boolean created = gymMerchDAO.addGymMerch(merch);
        System.out.println(created ? "Merch created successfully." : "Error creating merch.");
    }

    private static void listAllGymMerch() {
        List<GymMerch> merchList = gymMerchDAO.getAllGymMerch();
        if (merchList.isEmpty()) System.out.println("No merch found.");
        else merchList.forEach(System.out::println);
    }

    private static void updateGymMerch() {
        System.out.print("Enter merch ID to update: ");
        int merchId = Integer.parseInt(scanner.nextLine());

        GymMerch existing = gymMerchDAO.getGymMerchById(merchId);
        if (existing == null) {
            System.out.println("Merch not found.");
            return;
        }

        System.out.print("New name (blank to keep current): ");
        String name = scanner.nextLine().trim();
        System.out.print("New type (blank to keep current): ");
        String type = scanner.nextLine().trim();
        System.out.print("New price (blank to keep current): ");
        String priceInput = scanner.nextLine().trim();
        System.out.print("New quantity (blank to keep current): ");
        String qtyInput = scanner.nextLine().trim();

        String finalName = name.isEmpty() ? existing.getMerchName() : name;
        String finalType = type.isEmpty() ? existing.getMerchType() : type;
        double finalPrice = priceInput.isEmpty() ? existing.getMerchPrice() : Double.parseDouble(priceInput);
        int finalQty = qtyInput.isEmpty() ? existing.getQuantityInStock() : Integer.parseInt(qtyInput);

        GymMerch updated = new GymMerch(existing.getMerchID(), finalName, finalType, finalPrice, finalQty);

        boolean updatedOk = gymMerchDAO.updateGymMerch(updated);
        System.out.println(updatedOk ? "Merch updated successfully." : "Error updating merch.");
    }

    private static void deleteGymMerch() {
        System.out.print("Enter merch ID to delete: ");
        int merchId = Integer.parseInt(scanner.nextLine());

        boolean deleted = gymMerchDAO.deleteGymMerch(merchId);
        System.out.println(deleted ? "Merch deleted successfully." : "Error deleting merch.");
    }

    // MEMBERSHIP CRUD
    private static void handleMembershipCRUD() {
        System.out.println("\nMembership Options: 1-Create 2-View 3-Update 4-Delete 5-Back");
        String option = scanner.nextLine();

        switch (option) {
            case "1" -> createMembership();
            case "2" -> listAllMemberships();
            case "3" -> updateMembership();
            case "4" -> deleteMembership();
            case "5" -> { /* back */ }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void createMembership() {
        System.out.print("Enter membership type: ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter member ID (owner): ");
        int memberId = Integer.parseInt(scanner.nextLine().trim());

        Membership membership = new Membership(0, type, description, price, memberId);

        boolean created = membershipDAO.createMembership(membership);
        System.out.println(created ? "Membership created successfully." : "Error creating membership.");
    }

    private static void listAllMemberships() {
        List<Membership> memberships = membershipDAO.getAllMemberships();
        if (memberships.isEmpty()) System.out.println("No memberships found.");
        else memberships.forEach(System.out::println);
    }

    private static void updateMembership() {
        System.out.print("Enter membership ID to update: ");
        int membershipId = Integer.parseInt(scanner.nextLine());

        Membership existing = membershipDAO.getMembershipById(membershipId);
        if (existing == null) {
            System.out.println("Membership not found.");
            return;
        }

        System.out.print("New type (blank to keep current): ");
        String type = scanner.nextLine().trim();
        System.out.print("New description (blank to keep current): ");
        String description = scanner.nextLine().trim();
        System.out.print("New price (blank to keep current): ");
        String priceInput = scanner.nextLine().trim();
        System.out.print("New member ID (blank to keep current): ");
        String memberInput = scanner.nextLine().trim();

        String finalType = type.isEmpty() ? existing.getMembershipType() : type;
        String finalDescription = description.isEmpty() ? existing.getMembershipDescription() : description;
        double finalPrice = priceInput.isEmpty() ? existing.getMembershipCost() : Double.parseDouble(priceInput);
        int finalMemberId = memberInput.isEmpty() ? existing.getMemberID() : Integer.parseInt(memberInput);

        Membership updated = new Membership(existing.getMembershipID(), finalType, finalDescription, finalPrice, finalMemberId);

        boolean updatedOk = membershipDAO.updateMembership(updated);
        System.out.println(updatedOk ? "Membership updated successfully." : "Error updating membership.");
    }

    private static void deleteMembership() {
        System.out.print("Enter membership ID to delete: ");
        int membershipId = Integer.parseInt(scanner.nextLine());

        boolean deleted = membershipDAO.deleteMembership(membershipId);
        System.out.println(deleted ? "Membership deleted successfully." : "Error deleting membership.");
    }
}
