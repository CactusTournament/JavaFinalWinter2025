import dao.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import models.*;
import services.*;
import utils.PasswordUtil;

/**
 * Main
 * Entry point for Gym Management System.
 * Handles user input, validation, and ensures passwords are hashed before DAO
 * operations.
 *
 * Author: Brandon Maloney
 * Updated by: Abiodun Magret Oyedele
 * Date: 2025-12-12
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static User currentUser = null; // Controls when user is logged in

    private static final UserDAO userDAO = new UserDAO();
    private static final MemberDAO memberDAO = new MemberDAO();
    private static final TrainerDAO trainerDAO = new TrainerDAO();
    private static final AdminDAO adminDAO = new AdminDAO();
    private static final WorkoutClassDAO workoutClassDAO = new WorkoutClassDAO();
    private static final GymMerchDAO gymMerchDAO = new GymMerchDAO();
    private static final MembershipDAO membershipDAO = new MembershipDAO();

    private static final MembershipService membershipService = new MembershipService(membershipDAO);
    private static final GymMerchService gymMerchService = new GymMerchService(gymMerchDAO);
    private static final WorkoutClassService workoutClassService = new WorkoutClassService(workoutClassDAO);
    private static final UserService userService = new UserService(userDAO);

        // Membership plans are stored in the DB and accessed via MembershipService

    /**
     * Default constructor for Main class.
     */
    public Main() {
        // No initialization required for now
    }

    /**
     * Main method - entry point of the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Gym Management System!");

        while (currentUser == null) {
            showAuthMenu();
        }

        boolean running = true;
        while (running) {
            if (currentUser == null) {
                showAuthMenu();
                if (currentUser == null) {
                    continue;
                }
            }
            
            System.out.println("currentUser Role: " + currentUser.getUserRole());
            String role = currentUser.getUserRole();
            
            if (role == null || role.trim().isEmpty()) {
                System.out.println("Error: User role is null or empty. Logging out.");
                currentUser = null;
                continue;
            }
            
            switch (role.trim().toLowerCase()) {
                case "admin" -> showAdminMenu();
                case "trainer" -> showTrainerMenu();
                case "member" -> showMemberMenu();
                default -> {
                    System.out.println("Unknown role: '" + role + "'. Logging out.");
                    currentUser = null;
                }
            }
        }
    }

    /**
     * Displays the authentication menu for login or registration.
     * Presents options to the user and routes to the appropriate handler
     * (login, register, or exit).
     *
     * <p>This method reads from {@code System.in} using the shared
     * {@code scanner} and does not return a value; it will call other
     * methods that may change the {@code currentUser} state.</p>
     */
    private static void showAuthMenu() {
        System.out.println("\n1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> loginUser();
            case "2" -> createUser();
            case "3" -> System.exit(0);
            default -> System.out.println("Invalid option.");
        }
    }

    /**
     * Displays the admin menu and handles admin actions.
     *
     * <p>Admin-specific actions (user/membership/merch/trainer/member
     * management) are dispatched from this menu. Selecting logout will
     * clear {@code currentUser} and return control to the main loop.</p>
     */
    private static void showAdminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Manage Users i.e ListAllUsers/UpdateUser/DeleteUser");
            System.out.println(
                    "2. Manage Memberships i.e CreateMemberships/ListAllMemberships/UpdateMembership/DeleteMembership/viewTotalRevenue");
            System.out.println(
                    "3. Manage Gym Merchandise i.e CreateGymMerch/ListAllGymMerch/UpdateGymMerch/DeleteGymMerch/PrintMerchStockReport");
            System.out.println("4. Manage Trainers i.e CreateTrainer/ListAllTrainers/UpdateTrainer/DeleteTrainer");
            System.out.println("5. Manage Members i.e CreateMember/ListAllMembers/UpdateMember/DeleteMember");
            System.out.println("6. Logout");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleUserCRUD();
                case "2" -> handleMembershipCRUD();
                case "3" -> handleGymMerchCRUD();
                case "4" -> handleTrainerCRUD();
                case "5" -> handleMemberCRUD();
                case "6" -> {
                    logout();
                    back = true;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Displays the trainer menu and handles trainer actions.
     *
     * <p>Trainer actions include managing workout classes, viewing
     * merchandise and purchasing memberships. Selecting logout will
     * clear {@code currentUser} and return control to the main loop.</p>
     */
    private static void showTrainerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- TRAINER MENU ---");
            System.out.println(
                    "1. Manage Workout Classes i.e CreateWorkoutClass/ListMyClasses/UpdateWorkoutClass/DeleteWorkoutClass");
            System.out.println("2. View All Workout Classes");
            System.out.println("3. Purchase Membership");
            System.out.println("4. View Gym Merchandise");
            System.out.println("5. View My Membership Expenses");
            System.out.println("6. Logout");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleWorkoutClassCRUD();
                case "2" -> listAllWorkoutClasses();
                case "3" -> purchaseMembership();
                case "4" -> listAllGymMerch();
                case "5" -> viewMemberExpenses();
                case "6" -> {
                    logout();
                    back = true;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Displays the member menu and handles member actions.
     *
     * <p>Member actions include browsing classes, purchasing memberships
     * and viewing expenses. Selecting logout will clear
     * {@code currentUser} and return control to the main loop.</p>
     */
    private static void showMemberMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MEMBER MENU ---");
            System.out.println("1. Browse Workout Classes");
            System.out.println("2. Purchase Membership");
            System.out.println("3. View My Membership Expenses");
            System.out.println("4. View Gym Merchandise");
            System.out.println("5. Logout");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> listAllWorkoutClasses();
                case "2" -> purchaseMembership();
                case "3" -> viewMemberExpenses();
                case "4" -> listAllGymMerch();
                case "5" -> {
                    logout();
                    back = true;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Logs out the current user.
     *
     * <p>This clears {@code currentUser} so the main loop will prompt the
     * authentication menu again.</p>
     */
    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }

    /**
     * Prompts for username and password and attempts to authenticate.
     *
     * <p>On successful authentication this method sets {@code currentUser}
     * to the authenticated {@code User}. If authentication fails it prints
     * an error message and leaves {@code currentUser} unchanged.</p>
     *
     * @see services.UserService#login(String, String)
     */
    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Username and password cannot be empty.");
            return;
        }

        try {
            User user = userService.login(username, password);
            if (user == null) {
                System.out.println("Error: User not found or invalid password.");
                return;
            }
            // currentUser = userDAO.getUserByUsername(username);
            currentUser = user;
            System.out.println("Login successful. Welcome, " + currentUser.getUserName() + " User ID: " + currentUser.getUserId() + "!");
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    /**
     * USER CRUD
     * Handles CRUD operations for Users.
     */
    private static void handleUserCRUD() {
        boolean back = false;
        while (!back) {
            System.out.println("\nUser Options: 1-List Users 2-Update User 3-Delete User 4-Back");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> listAllUsers();
                case "2" -> updateUser();
                case "3" -> deleteUser();
                case "4" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Creates a new user (Admin/Trainer/Member) after validating input and hashing
     * password.
     */
    private static void createUser() {
        // This method is now a registration endpoint that creates Admin/Trainer/Member
        // records
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

        // User registeredUser = userService.registerUser(username, email, phone,
        // address, role, password);

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

        System.out.println(created ? normalizedRole + " created successfully. Please Login with your credentials."
                : "Error creating " + normalizedRole + ".");
    }

    /**
     * Lists all users in the system.
     */
    private static void listAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty())
            System.out.println("No users found.");
        else
            users.forEach(System.out::println);
    }

    /**
     * Updates an existing user after validating input and hashing password if
     * changed.
     */
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

        // Use existing values when blanks are provided. (Assumes standard getter
        // names.)
        String finalUsername = username.isEmpty() ? existing.getUserName() : username;
        String finalEmail = email.isEmpty() ? existing.getEmail() : email;
        String finalPhone = phone.isEmpty() ? existing.getUserPhoneNumber() : phone;
        String finalAddress = address.isEmpty() ? existing.getUserAddress() : address;
        String finalRole = role.isEmpty() ? existing.getUserRole() : role;
        String finalPasswordHash = password.isEmpty() ? existing.getPasswordHash()
                : PasswordUtil.hashPassword(password);

        User updated = new User(existing.getUserId(), finalUsername, finalPasswordHash, finalEmail, finalPhone,
                finalAddress, finalRole);

        boolean updatedOk = userDAO.updateUser(updated);
        System.out.println(updatedOk ? "User updated successfully." : "Error updating user.");
    }

    /**
     * Deletes a user by ID.
     */
    private static void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        int userId = Integer.parseInt(scanner.nextLine());

        boolean deleted = userDAO.deleteUser(userId);
        System.out.println(deleted ? "User deleted successfully." : "Error deleting user.");
    }

    /**
     * TRAINER CRUD
     */
    private static void handleTrainerCRUD() {
        boolean back = false;
        while (!back) {
            System.out.println(
                    "\nTrainer Options: 1-Create Trainer 2-List All Trainer 3-Update Trainer 4-Delete Trainer 5-Back");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> createTrainer();
                case "2" -> listAllTrainers();
                case "3" -> updateTrainer();
                case "4" -> deleteTrainer();
                case "5" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Creates a new trainer after validating input and hashing password.
     */
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

    /**
     * Lists all trainers in the system.
     */
    private static void listAllTrainers() {
        List<Trainer> trainers = trainerDAO.getAllTrainers();
        if (trainers.isEmpty())
            System.out.println("No trainers found.");
        else
            trainers.forEach(System.out::println);
    }

    /**
     * Updates an existing trainer after validating input and hashing password if
     * changed.
     */
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
        String finalPasswordHash = password.isEmpty() ? existing.getPasswordHash()
                : PasswordUtil.hashPassword(password);

        Trainer updated = new Trainer(existing.getUserId(), finalName, finalPasswordHash, finalEmail, finalPhone,
                finalAddress);

        boolean updatedOk = trainerDAO.updateTrainer(updated);
        System.out.println(updatedOk ? "Trainer updated successfully." : "Error updating trainer.");
    }

    /**
     * Deletes a trainer by ID.
     */
    private static void deleteTrainer() {
        System.out.print("Enter trainer ID to delete: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        boolean deleted = trainerDAO.deleteTrainer(trainerId);
        System.out.println(deleted ? "Trainer deleted successfully." : "Error deleting trainer.");
    }

    /**
     * MEMBER CRUD
     */
    private static void handleMemberCRUD() {
        boolean back = false;
        while (!back) {
            System.out.println(
                    "\nMember Options: 1-Create Member 2-List All Member 3-Update Member 4-Delete Member 5-Back");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> createMember();
                case "2" -> listAllMembers();
                case "3" -> updateMember();
                case "4" -> deleteMember();
                case "5" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Creates a new member after validating input and hashing password.
     */
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

    /**
     * Lists all members in the system.
     */
    private static void listAllMembers() {
        List<Member> members = memberDAO.getAllMembers();
        if (members.isEmpty())
            System.out.println("No members found.");
        else
            members.forEach(System.out::println);
    }

    /**
     * Updates an existing member after validating input and hashing password if
     * changed.
     */
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
        String finalPasswordHash = password.isEmpty() ? existing.getPasswordHash()
                : PasswordUtil.hashPassword(password);

        Member updated = new Member(existing.getUserId(), finalName, finalPasswordHash, finalEmail, finalPhone,
                finalAddress);

        boolean updatedOk = memberDAO.updateMember(updated);
        System.out.println(updatedOk ? "Member updated successfully." : "Error updating member.");
    }

    /**
     * Deletes a member by ID.
     */
    private static void deleteMember() {
        System.out.print("Enter member ID to delete: ");
        int memberId = Integer.parseInt(scanner.nextLine());

        boolean deleted = memberDAO.deleteMember(memberId);
        System.out.println(deleted ? "Member deleted successfully." : "Error deleting member.");
    }

    /**
     * WORKOUT CLASS CRUD
     */
    private static void handleWorkoutClassCRUD() {
        boolean back = false;
        while (!back) {
            System.out.println("\nWorkoutClass Options: 1-Create 2-View 3-Update 4-Delete 5-Back");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> createWorkoutClass();
                case "2" -> listTrainerClasses();
                case "3" -> updateWorkoutClass();
                case "4" -> deleteWorkoutClass();
                case "5" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Creates a new workout class after validating input.
     */
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

    /**
     * Lists all workout classes in the system.
     */
    private static void listAllWorkoutClasses() {
        List<WorkoutClass> classes = workoutClassDAO.getAllWorkoutClasses();
        if (classes.isEmpty())
            System.out.println("No workout classes found.");
        else
            classes.forEach(System.out::println);
    }

    /**
     * Get Trainer workout classes
     */
    private static void listTrainerClasses() {
        System.out.print("Enter your Trainer ID: "); // I NEED TO GET THE TRINAER ID MYSELF
        int trainerId = Integer.parseInt(scanner.nextLine());

        try {
            List<WorkoutClass> classes = workoutClassService.getWorkoutClassesByTrainerId(trainerId);
            if (classes.isEmpty()) {
                System.out.println("No classes found for this trainer.");
            } else {
                classes.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving classes: " + e.getMessage());
        }
    }

    /**
     * Updates an existing workout class after validating input.
     */
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

        WorkoutClass updated = new WorkoutClass(existing.getWorkoutClassID(), finalType, finalDescription,
                finalTrainerId);

        boolean updatedOk = workoutClassDAO.updateWorkoutClass(updated);
        System.out.println(updatedOk ? "Workout class updated successfully." : "Error updating workout class.");
    }

    /**
     * Deletes a workout class by ID.
     */
    private static void deleteWorkoutClass() {
        System.out.print("Enter class ID to delete: ");
        int classId = Integer.parseInt(scanner.nextLine());

        boolean deleted = workoutClassDAO.deleteWorkoutClass(classId);
        System.out.println(deleted ? "Workout class deleted successfully." : "Error deleting workout class.");
    }

    /**
     * GYM MERCH CRUD
     */
    private static void handleGymMerchCRUD() {
        boolean back = false;
        while (!back) {
            System.out.println(
                    "\nGymMerch Options: 1-Create GymMerch 2-List All GymMerch 3-Update GymMerch 4-Delete GymMerch 5-PrintMerchStockReport 6-Back");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> createGymMerch();
                case "2" -> listAllGymMerch();
                case "3" -> updateGymMerch();
                case "4" -> deleteGymMerch();
                case "5" -> printMerchStockReport();
                case "6" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Creates a new gym merchandise item after validating input.
     */
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

    /**
     * Lists all gym merchandise items in the system.
     */
    private static void listAllGymMerch() {
        List<GymMerch> merchList = gymMerchDAO.getAllGymMerch();
        if (merchList.isEmpty())
            System.out.println("No merch found.");
        else
            merchList.forEach(System.out::println);
    }

    /**
     * Updates an existing gym merchandise item after validating input.
     */
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

    /**
     * Deletes a gym merchandise item by ID.
     */
    private static void deleteGymMerch() {
        System.out.print("Enter merch ID to delete: ");
        int merchId = Integer.parseInt(scanner.nextLine());

        boolean deleted = gymMerchDAO.deleteGymMerch(merchId);
        System.out.println(deleted ? "Merch deleted successfully." : "Error deleting merch.");
    }

    /**
     * Prints a report of all gym merchandise stock with total values.
     */
    private static void printMerchStockReport() {
        List<GymMerch> merchList = gymMerchService.getAllMerch();

        if (merchList == null || merchList.isEmpty()) {
            System.out.println("No merchandise in stock.");
            return;
        }

        System.out.println("\n--- GYM MERCH STOCK REPORT ---");
        System.out.printf("%-5s %-20s %-10s %-10s %-10s%n",
                "ID", "Name", "Price", "Quantity", "Value");

        for (GymMerch merch : merchList) {
            double value = merch.getMerchPrice() * merch.getQuantityInStock();
            System.out.printf("%-5d %-20s %-10.2f %-10d %-10.2f%n",
                    merch.getMerchID(),
                    merch.getMerchName(),
                    merch.getMerchPrice(),
                    merch.getQuantityInStock(),
                    value);
        }
    }

    /**
     * MEMBERSHIP CRUD
     */
    private static void handleMembershipCRUD() {
        boolean back = false;
        while (!back) {
            System.out.println(
                    "\nMembership Options: 1-Create Membership 2-List All Memberships 3-Update Membership 4-Delete MEmebership 5-View Total Revenue 6-View Member Expenses 7-Back");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> createMembership();
                case "2" -> listAllMemberships();
                case "3" -> updateMembership();
                case "4" -> deleteMembership();
                case "5" -> viewTotalRevenue();
                case "6" -> viewMemberExpenses();
                case "7" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Creates a new membership after validating input.
     */
    private static void createMembership() {
        System.out.print("Enter membership type(Standard, Premium, VIP): ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter member ID (owner): ");
        int memberId = Integer.parseInt(scanner.nextLine().trim());

        Membership membership = new Membership(0, type, description, price, memberId);

        Membership membershipCreated = membershipService.addMembership(membership);
        if (membershipCreated != null) {
            System.out.println("Membership created successfully: " + membershipCreated);
        } else {
            System.out.println("Error creating membership.");
        }
    }

    /**
     * Purchase flow for logged-in users. Presents predefined membership
     * options (type/description/price) and signs up the current user for the
     * selected membership.
     */
    private static void purchaseMembership() {
        if (currentUser == null) {
            System.out.println("You must be logged in to purchase a membership.");
            return;
        }
        List<MembershipPlan> plans = membershipService.getAvailablePlans();
        if (plans == null || plans.isEmpty()) {
            System.out.println("No membership plans available at this time.");
            return;
        }

        System.out.println("\nAvailable Membership Plans:");
        for (int i = 0; i < plans.size(); i++) {
            MembershipPlan p = plans.get(i);
            System.out.printf("%d) %s - %s ($%.2f)%n", i + 1, p.getPlanType(), p.getPlanDescription(), p.getPlanPrice());
        }
        System.out.println("0) Cancel");
        System.out.print("Select a membership: ");

        String input = scanner.nextLine().trim();
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection.");
            return;
        }

        if (choice == 0) {
            System.out.println("Membership purchase cancelled.");
            return;
        }

        if (choice < 1 || choice > plans.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        MembershipPlan selected = plans.get(choice - 1);
        Membership created = membershipService.purchasePlan(selected.getPlanId(), currentUser.getUserId());
        if (created != null) {
            System.out.println("Successfully purchased membership: " + created.getMembershipType() + " ($" + created.getMembershipCost() + ")");
        } else {
            System.out.println("Error processing membership purchase.");
        }
    }

    /**
     * Lists all memberships in the system.
     */
    private static void listAllMemberships() {
        List<Membership> memberships = membershipService.getAllMemberships();
        if (memberships.isEmpty())
            System.out.println("No memberships found.");
        else
            memberships.forEach(System.out::println);
    }

    /**
     * Updates an existing membership after validating input.
     */
    private static void updateMembership() {
        System.out.print("Enter membership ID to update: ");
        int membershipId = Integer.parseInt(scanner.nextLine());

        Membership existing = membershipService.getMembership(membershipId);
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

        Membership updated = new Membership(existing.getMembershipID(), finalType, finalDescription, finalPrice,
                finalMemberId);

        boolean updatedOk = membershipService.updateMembership(updated);
        System.out.println(updatedOk ? "Membership updated successfully." : "Error updating membership.");
    }

    /**
     * Deletes a membership by ID.
     */
    private static void deleteMembership() {
        System.out.print("Enter membership ID to delete: ");
        int membershipId = Integer.parseInt(scanner.nextLine());

        boolean deleted = membershipService.deleteMembership(membershipId);
        System.out.println(deleted ? "Membership deleted successfully." : "Error deleting membership.");
    }

    /**
     * Views total revenue from memberships.
     */
    private static void viewTotalRevenue() {
        double total = membershipService.viewTotalRevenue();
        System.out.println("Total Revenue from Memberships: $" + total);
    }

    /**
     * Views the current member's total expenses on memberships.
     */
    private static void viewMemberExpenses() {
        if (!currentUser.getUserRole().equalsIgnoreCase("Member")) {
            System.out.println("Access denied. Members only.");
            return;
        }

        double total = membershipService.calculateMemberExpenses(currentUser.getUserId());

        System.out.println("Your total membership expenses: $" + total);
    }
}
