# Gym Management System  
## User Documentation  

**Final Sprint -- Fall 2025**

---

## 1. Overview of the Application

### What Is the Gym Management System?

The **Gym Management System** is a console-based computer application designed to help manage the daily operations of a gym. It allows different types of users **Admins**, **Trainers**, and **Members** to interact with the system based on their assigned roles.

This system helps organize:

- Gym users (Admins, Trainers, Members)
- Membership purchases
- Workout classes
- Gym merchandise

The application runs in a **text-based menu system** (no graphics), where users interact by typing choices using the keyboard.

---

### Who Is This Application For?

This system is designed for:

- **Gym Owners / Gym Admins**: To manage users, memberships, workout classes, and merchandise  
- **Trainers**: To manage workout classes and memberships  
- **Gym Members**: To view classes, purchase memberships, and view gym products  

Each user sees different menu options depending on their role after logging in.

---

### What Does the Application Do?

The Gym Management System allows users to:

- Register and log in securely  
- Manage gym memberships  
- Create and manage workout classes  
- View and manage gym merchandise  
- Store and retrieve all information from a database  
- Track gym activity using logs  

All data is saved in a **PostgreSQL database**, so information is persistent i.e not lost when the program is closed.

---

### How the System Works (Simple Explanation)

```
User --> Login/Register --> Role-Based Menu --> Perform Actions --> Data Saved to Database
```


1. A user registers or logs in  
2. The system checks their role (Admin, Trainer, or Member)  
3. A role-specific menu is displayed  
4. The user selects actions from the menu  
5. The system processes the request  
6. Information is stored or retrieved from the database  
7. Important actions are logged to a text file  

---

## User Roles and Their Capabilities

### Admin

Admins have full control over the system. They can:

- View all users and their contact details  
- Delete users  
- View all memberships  
- Track total gym revenue  
- Manage gym merchandise (add items, set prices, view stock reports)  

---

### Trainer

Trainers focus on workout-related tasks. They can:

- Create, update, and delete workout classes  
- View classes assigned to them  
- Purchase gym memberships  
- View available gym merchandise  

---

### Member

Members are gym customers. They can:

- Browse available workout classes  
- Purchase gym memberships  
- View their total membership expenses  
- View available gym merchandise  

---

## Security and Login System

- Each user has a username and password  
- Passwords are securely encrypted using **BCrypt**  
- Passwords are never stored in plain text  
- Only authorized users can access role-specific features  

---

## Database and Data Storage

The application uses a **PostgreSQL database** to store:

- User information  
- Membership details  
- Workout class data  
- Gym merchandise inventory  

This ensures:

- Data persistence  
- Accurate tracking  
- Secure storage  

---

## Console-Based Interface

- The application runs in the command line / terminal  
- Users interact using numbered menu options  
- Clear instructions guide users through each step  
- Designed to be easy to use, even for non-technical users  

---

## Logging and System Monitoring

The system includes logging, which:

- Records important events (logins, errors, actions)  
- Writes logs to a text file  
- Helps with troubleshooting and tracking system activity  

---

## Summary

The **Gym Management System** is a simple yet powerful tool that:

- Organizes gym operations  
- Supports multiple user roles  
- Secures user data  
- Stores all information in a database  
- Provides clear, menu-driven interaction  

It is designed to be **reliable**, **secure**, and **easy to use**, even for users with little or no technical background.

## 2. Explanation of All Classes and Their Interactions
This section explains all the major classes used in the **Gym Management System**, what each class represents, and how they interact with one another.

The system follows a **layered architecture** consisting of:
- **Model classes** - represent real-world gym entities
- **DAO (Data Access Object) classes** – handle database operations (CRUD)
- **Service classes** - contain logic and coordinate actions between models and DAOs

---

## 2.1. Core User Hierarchy

### User (Superclass)

The **User** class is the base (super) class for all users in the system.  
Every **Admin**, **Trainer**, and **Member** inherits from `User`.

This class represents a record in the **Users** database table and contains all shared user attributes.

#### Responsibilities
- Store common user information  
- Provide getters and setters for user data  
- Act as the parent class for role-specific users  

#### Key Fields
- `userId` – Unique identifier for the user  
- `userName` – User’s name  
- `userAddress` – Physical address  
- `userPhoneNumber` – Contact phone number  
- `userRole` – Role of the user (Admin, Trainer, Member)  
- `passwordHash` – Securely hashed password  
- `email` – User’s email address  

#### Why This Design?

Using a superclass avoids code duplication and allows:

- Shared behavior across all user types  
- Role-based access control using `userRole`  
- Cleaner and more maintainable code  

---

### Admin (Subclass of User)

The **Admin** class represents administrators of the gym system.

`Admin --> User`

#### Responsibilities
- Manage users (view and delete users)  
- View gym memberships and total revenue  
- Manage gym merchandise (add items, set prices, view stock reports)  

#### Key Behavior
- Inherits all fields from `User`  
- Automatically sets the role to **Admin** during construction  
- Extends functionality through permissions rather than new fields  

---

### Trainer (Subclass of User)

The **Trainer** class represents gym trainers.

`Trainer → User`

#### Responsibilities
- Create, update, and delete workout classes  
- View workout classes assigned to them  
- Purchase gym memberships  
- View gym merchandise  

Like `Admin`, the `Trainer` class inherits all common user attributes and relies on role-based logic for behavior.

---

### Member (Subclass of User)

The **Member** class represents gym members/customers.

`Member → User`


#### Responsibilities
- Browse workout classes  
- Purchase gym memberships  
- View total membership expenses  
- View gym merchandise  

Members also inherit all shared user properties from `User`.

---

## 2.2 Membership Management

### Membership

The **Membership** class represents a gym membership purchased by a user.  
It maps directly to the **Memberships** table in the database.

#### Responsibilities
- Store membership details  
- Associate a membership with a specific user (member)  

#### Key Fields
- `membershipID` – Unique identifier  
- `membershipType` – Type of membership
- `membershipDescription` – Description of the membership  
- `membershipCost` – Cost of the membership  
- `memberID` – ID of the user who owns the membership  

#### Interaction
- A `User` (Member or Trainer) can purchase one or more memberships  
- Admins can view all memberships to calculate revenue  

---

## 2.3 Workout Class Management

### WorkoutClass

The **WorkoutClass** class represents a gym workout session led by a trainer.

#### Responsibilities
- Store workout class information  
- Link a workout class to a trainer  

#### Key Fields
- `workoutClassID` – Unique identifier  
- `workoutClassType` – Type of workout (e.g., Yoga, HIIT)  
- `workoutClassDescription` – Description of the class  
- `trainerID` – ID of the trainer responsible for the class  

#### Interaction
- Trainers create and manage workout classes  
- Members view available workout classes  
- Admins can monitor overall system activity  

---

## 2.4 Gym Merchandise Management

### GymMerch

The **GymMerch** class represents items sold at the gym, such as:

- Workout gear  
- Drinks  
- Food items  

#### Responsibilities
- Store merchandise details  
- Track inventory and pricing  

#### Key Fields
- `merchID` – Unique identifier  
- `merchName` – Name of the item  
- `merchType` – Category/type of item  
- `merchPrice` – Price per item  
- `quantityInStock` – Available stock  

#### Interaction
- Admins add and manage merchandise  
- Members and Trainers can view available products  

---

## 2.5 Data Access Object (DAO) Layer

The **DAO (Data Access Object) layer** is responsible for all communication with the PostgreSQL database.  
Each DAO class performs **CRUD operations** (Create, Read, Update, Delete) for a specific entity.

The DAO layer:

- Executes SQL queries  
- Converts database records into Java objects  
- Keeps database logic separate from the rest of the application  

---

### 2.5.1 UserDAO

The **UserDAO** class manages all database operations related to users, regardless of role.

#### Responsibilities
- Create and manage user records  
- Retrieve users by ID, username, or email  
- Update and delete users  
- Convert database rows into `User` objects  

#### Key Methods

```
public boolean createUser(User user)
```
- Saves a new user into the database
- Used during user registration
- Password is already hashed before saving

```
public User getUserByUsername(String username)
```
- Retrieves a user based on their username
- Used during login

```
public User getUserByEmail(String email)
```
- Retrieves a user using their email address
- Helps prevent duplicate registrations

```
public User getUserById(int userId)
```
- Fetches a user by their unique ID
- Used for role-based actions and validations

```
public List<User> getAllUsers()
```
- Returns a list of all users in the system
- Mainly used by Admins

```
public boolean updateUser(User user)
```
- Updates an existing user’s information

```
public boolean deleteUser(int userId)
```
- Removes a user from the system

```
private User mapResultSetToUser(ResultSet rs)
```
- Converts a database row into a User object
- Ensures consistent object creation
- Used internally by the DAO

---

### 2.5.2 AdminDAO
The **AdminDAO class** manages database operations specifically for Admin users.

#### Responsibilities
- Create and manage Admin accounts
- Retrieve Admin-specific records

#### Key Methods
```
public boolean createAdmin(Admin admin)
public Admin getAdminById(int userId)
public Admin getAdminByEmail(String email)
public List<Admin> getAllAdmins()
public boolean updateAdmin(Admin admin)
public boolean deleteAdmin(int userId)
```

#### Notes
- Admins are stored in the same Users table
- Role-based filtering ensures only Admin records are returned

---

### 2.5.3 MemberDAO
The **MemberDAO class** handles database operations for Members.

#### Responsibilities
- Manage member records
- Retrieve member-specific data

#### Key Methods
```
public boolean createMember(Member member)
public Member getMemberById(int userId)
public Member getMemberByEmail(String email)
public List<Member> getAllMembers()
public boolean updateMember(Member member)
public boolean deleteMember(int userId)
```

### 2.5.4 TrainerDAO
The **TrainerDAO class** manages database operations for Trainers.

#### Responsibilities
- Create and manage trainer accounts
- Retrieve trainers for workout class assignments

#### Key Methods
```
public boolean createTrainer(Trainer trainer)
public Trainer getTrainerById(int userId)
public Trainer getTrainerByEmail(String email)
public List<Trainer> getAllTrainers()
public boolean updateTrainer(Trainer trainer)
public boolean deleteTrainer(int userId)
```

---

### 2.5.5 GymMerchDAO
The **GymMerchDAO class** handles all database operations related to gym merchandise.

#### Responsibilities
- Manage merchandise inventory
- Track pricing and stock levels

#### Key Methods
```
public boolean addGymMerch(GymMerch merch)
public GymMerch getGymMerchById(int merchId)
public List<GymMerch> getAllGymMerch()
public boolean updateGymMerch(GymMerch merch)
public boolean deleteGymMerch(int merchId)
```

### 2.5.6 MembershipDAO
The **MembershipDAO class** manages gym memberships.

#### Responsibilities
- Store membership purchases
- Retrieve memberships for users and admins
- Support revenue tracking

#### Key Methods
```
public boolean createMembership(Membership membership)
public Membership getMembershipById(int membershipId)
public List<Membership> getAllMemberships()
public boolean updateMembership(Membership membership)
public boolean deleteMembership(int membershipId)
```

### 2.5.7 WorkoutClassDAO
The **WorkoutClassDAO class** manages workout classes.

#### Responsibilities
- Store workout class information
- Assign trainers to classes

#### Key Methods
```
public boolean createWorkoutClass(WorkoutClass workoutClass)
public WorkoutClass getWorkoutClassById(int workoutClassId)
public List<WorkoutClass> getAllWorkoutClasses()
public boolean updateWorkoutClass(WorkoutClass workoutClass)
public boolean deleteWorkoutClass(int workoutClassId)
```

### 2.6 Service Layer
The **Service layer** contains the business logic of the system.
It acts as a bridge between the user interface and the DAO layer.

#### Services:
- Validate user input
- Enforce role-based permissions
- Call DAO methods to perform database operations

### 2.6.1 GymMerchService
#### Responsibilities
- Manage gym merchandise operations
- Ensure only Admins can modify merchandise

#### Key Methods
```
addMerch(GymMerch merch)
getMerch(int id)
List<GymMerch> getAllMerch()
updateMerch(GymMerch merch)
deleteMerch(int id)
```

### 2.6.2 MembershipService
#### Responsibilities
- Handle membership purchases
- Track membership expenses and revenue

#### Key Methods
```
createMembership(Membership membership)
getMembership(int id)
List<Membership> getAllMemberships()
updateMembership(Membership membership)
deleteMembership(int id)
```

### 2.6.3 UserService
#### Responsibilities
Handle user registration and authentication
Coordinate user-related operations

#### Key Methods
`registerUser(User user)`
- Registers a new user
- Ensures username and email uniqueness
- Hashes passwords before saving

`login(String username, String password)`
- Authenticates users
- Verifies password using BCrypt
- Returns the logged-in user

`List<User> getAllUsers()`
- Retrieves all users
- Restricted to Admins

`User getTrainerById(int userId)`
- Retrieves a trainer by ID
- Used when assigning workout classes

### 2.6.4 WorkoutClassService
#### Responsibilities
- Manage workout class operations
- Enforce trainer-specific permissions

#### Key Methods
```
createWorkoutClass(WorkoutClass workoutClass)
getWorkoutClass(int id)
List<WorkoutClass> getAllWorkoutClasses()
updateWorkoutClass(WorkoutClass workoutClass)
deleteWorkoutClass(int id)
```

### 2.7 DAO and Service Interaction Flow
```
User Action
   |
Service Layer (Business Rules)
   |
DAO Layer (Database Access)
   |
PostgreSQL Database
```

This separation ensures:
- Clean code structure
- Better maintainability
- Easier debugging and testing

### 2.8 Testing Layer (tests/)
The testing layer is used to verify that the DAO and Service layers are functioning correctly before running the full application.

Testing is performed using standalone test classes, executed from the command line.

### 2.8.1 TestAllDAO.java
This test file validates all DAO classes by directly testing database operations.

#### Purpose
- Ensure CRUD operations work correctly
- Verify database connectivity
- Confirm SQL queries return expected results

#### What It Tests
- UserDAO
- AdminDAO
- TrainerDAO
- MemberDAO
- WorkoutClassDAO
- GymMerchDAO
- MembershipDAO

Each DAO is tested independently to confirm:
- Records can be created
- Records can be retrieved
- Records can be updated
- Records can be deleted

### 2.8.2 TestAllServices.java
This test file validates the Service layer, ensuring business logic is correctly applied.

#### Purpose
- Confirm services correctly call DAO methods
- Validate role-based and business rules
- Ensure data flows properly from Services → DAOs → Database

#### What It Tests
- UserService
- MembershipService
- WorkoutClassService
- GymMerchService

### 2.8.3 Running the Tests
- Compile All Files
```
javac -d classes -cp "lib/*" $(find . -name "*.java")
```

- Run DAO Tests
```
java -cp "classes:lib/*" JavaFinalWinter2025.tests.TestAllDAO
```

- Run Service Tests
```
java -cp "classes:lib/*" JavaFinalWinter2025.tests.TestAllServices
```


These commands ensure:
- All Java files compile correctly
- DAO and Service layers behave as expected before running the main system

### 2.9 Utility Classes (utils/)
The utils package contains shared helper classes used across the application to support database connectivity, logging, and security.

### 2.9.1 DatabaseConnection
This class manages connections to the PostgreSQL database.

#### Responsibilities
- Load database credentials securely using environment variables
- Establish database connections
- Provide a reusable connection method for DAO classes

#### Key Method
- public static Connection getcon()
- Loads database credentials from .env
- Registers the PostgreSQL driver
- Returns an active database connection

#### Why This Matters
- Centralizes database connection logic
- Prevents repeated connection code
- Improves security by avoiding hardcoded credentials

### 2.9.2 Database Schema
The application uses the following database tables:

#### Users Table
Users
- userId (PK)
- userName
- userAddress
- userPhoneNumber
- userRole
- passwordHash
- email

#### Memberships Table
Memberships
- membershipID (PK)
- membershipType
- membershipDescription
- membershipCost
- memberID (FK → Users.userId)

#### WorkoutClasses Table
WorkoutClasses
- workoutClassID (PK)
- workoutClassType
- workoutClassDescription
- trainerID (FK → Users.userId)

#### GymMerch Table
GymMerch
- merchID (PK)
- merchName
- merchType
- merchPrice
- quantityInStock

### 2.9.3 LoggerUtil
This class handles application logging.

#### Responsibilities
- Log system events and errors
- Write logs to a text file
- Support debugging and auditing

#### Logging Details
- Log file: gym_log.txt
- Logging level: INFO
- Format: Simple text format

#### Why Logging Is Important
- Tracks system activity
- Helps diagnose errors
- Provides accountability for system actions

### 2.9.4 PasswordUtil
This utility class handles password security using BCrypt.

#### Responsibilities
- Hash passwords before storage
- Verify passwords during login

#### Key Methods
- hashPassword(String plainTextPassword)
- Generates a secure bcrypt hash
- Uses a safe workload level
- verifyPassword(String plainTextPassword, String storedHash)
- Compares entered password with stored hash
- Prevents plain-text password exposure

#### Security Benefits
- Passwords are never stored in plain text
- Protects against common security attacks

### 2.10 Main Application Entry Point (Main.java)
The Main class is the entry point of the Gym Management System.

### 2.10.1 Responsibilities of Main
- Display the main menu
- Capture user input via the console
- Validate user input
- Call appropriate DAO methods
- Hash passwords before saving
- Control program flow

### 2.10.2 Console-Based Menu System
The application provides a menu-driven interface, including:
- User CRUD
- Admin CRUD
- Trainer CRUD
- Member CRUD
- Workout Class CRUD
- Gym Merchandise CRUD
- Membership CRUD

Each menu:
- Prompts the user for input
- Performs validation
- Executes the selected operation
- Displays success or error messages

### 2.10.3 Role-Aware User Creation
During user creation:
- Passwords are hashed using PasswordUtil
- Roles are normalized (Admin, Trainer, Member)

The correct DAO is used based on role

This ensures:
- Correct role assignment
- Secure password storage
- Clean separation of responsibilities

### 2.11 DAO and Service Interaction Flow
```
User Action
   |
Service Layer (Business Rules)
   |
DAO Layer (Database Access)
   |
PostgreSQL Database
```

This separation ensures:
- Clean code structure
- Better maintainability
- Easier debugging and testing

## 3. Class Diagram (UML-Style)
This section provides a high-level UML-style class diagram that shows the relationships between the main classes in the Gym Management System.

The diagram focuses on:
- Inheritance relationships                          
- Associations between entities
- Separation of concerns (Models, DAOs, Services, Utils)

### 3.1 High-Level System Architecture
```
+------------------+
|      Main        |
+------------------+
        |
        v
+------------------+
|   Service Layer  |
+------------------+
        |
        v
+------------------+
|     DAO Layer    |
+------------------+
        |
        v
+------------------+
| PostgreSQL DB    |
+------------------+
```

### 3.3 Core Model Classes
##### User
```
+----------------------------------+
| User                             |
+----------------------------------+
| - userId : int                   |
| - userName : String              |
| - userAddress : String           |
| - userPhoneNumber : String       |
| - userRole : String              |
| - passwordHash : String          |
| - email : String                 |
+----------------------------------+
```

##### Admin
```
+----------------------------------+
| Admin                            |
+----------------------------------+
| (inherits from User)             |
+----------------------------------+
```

##### Trainer
```
+----------------------------------+
| Trainer                          |
+----------------------------------+
| (inherits from User)             |
+----------------------------------+
```

##### Member
```
+----------------------------------+
| Member                           |
+----------------------------------+
| (inherits from User)             |
+----------------------------------+
```

##### Membership
```
+----------------------------------+
| Membership                       |
+----------------------------------+
| - membershipID : int             |
| - membershipType : String        |
| - membershipDescription : String |
| - membershipCost : double        |
| - memberID : int                 |
+----------------------------------+
```

#### WorkoutClass
```
+----------------------------------+
| WorkoutClass                     |
+----------------------------------+
| - workoutClassID : int           |
| - workoutClassType : String      |
| - workoutClassDescription : String|
| - trainerID : int                |
+----------------------------------+
```


##### GymMerch
```
+----------------------------------+
| GymMerch                         |
+----------------------------------+
| - merchID : int                  |
| - merchName : String             |
| - merchType : String             |
| - merchPrice : double            |
| - quantityInStock : int          |
+----------------------------------+
```

### 3.4 DAO Layer UML
Each DAO class manages database operations for its corresponding model.

```
+--------------------+      +------------------+
| UserDAO            | ---> | User             |
+--------------------+      +------------------+
| + createUser()     |
| + getUserById()    |
| + getAllUsers()    |
| + updateUser()     |
| + deleteUser()     |
+--------------------+


+--------------------+      +------------------+
| AdminDAO           | ---> | Admin            |
+--------------------+      +------------------+


+--------------------+      +------------------+
| TrainerDAO         | ---> | Trainer          |
+--------------------+      +------------------+


+--------------------+      +------------------+
| MemberDAO          | ---> | Member           |
+--------------------+      +------------------+


+--------------------+      +------------------+
| MembershipDAO      | ---> | Membership       |
+--------------------+      +------------------+


+--------------------+      +------------------+
| WorkoutClassDAO    | ---> | WorkoutClass     |
+--------------------+      +------------------+


+--------------------+      +------------------+
| GymMerchDAO        | ---> | GymMerch         |
+--------------------+      +------------------+
```

### 3.5 Service Layer UML
Service classes sit between the UI and DAOs, enforcing business rules.
```
+------------------------+
| UserService            |
+------------------------+
| + registerUser()       |
| + login()              |
| + getAllUsers()        |
+------------------------+
            |
            v
+------------------------+
| UserDAO                |
+------------------------+

+------------------------+
| MembershipService      |
+------------------------+
| + createMembership()   |
| + deleteMembership()   |
+------------------------+
            |
            v
+------------------------+
| MembershipDAO          |
+------------------------+

+------------------------+
| WorkoutClassService    |
+------------------------+
| + createWorkoutClass() |
| + deleteWorkoutClass() |
+------------------------+
            |
            v
+------------------------+
| WorkoutClassDAO        |
+------------------------+

+------------------------+
| GymMerchService        |
+------------------------+
| + addMerch()           |
| + deleteMerch()        |
+------------------------+
            |
            v
+------------------------+
| GymMerchDAO            |
+------------------------+
```

### 3.6 Utility Classes UML
```
+------------------------+
| DatabaseConnection     |
+------------------------+
| + getcon()             |
+------------------------+

+------------------------+
| PasswordUtil           |
+------------------------+
| + hashPassword()       |
| + verifyPassword()     |
+------------------------+

+------------------------+
| LoggerUtil             |
+------------------------+
| + getLogger()          |
+------------------------+
```

### 3.7 Summary of Class Relationships
```
User
  ^
  |
Admin / Trainer / Member

Member 1 -------- * Membership
Trainer 1 ------- * WorkoutClass

Service Layer ---> DAO Layer ---> Database
Main ------------> Service / DAO
```

## 4. Instructions on How to Start and Use the System
This section provides step-by-step instructions for compiling, running, and interacting with the Gym Management System.

### 4.1 Prerequisites
Before starting the system, ensure the following are installed:
- Java JDK 11 or later
- PostgreSQL (with database and tables created)
- Terminal / Command Line Interface
- BCrypt library (included in lib/ folder)
Environment variables for database credentials set in .env file:
    - DB_URL – PostgreSQL connection URL
    - DB_USER – Database username
    - DB_PASSWORD – Database password

### 4.2 Setup Instructions
1. Clone or download the project folder to your local machine.
2. Open the terminal and navigate to the project root directory.
3. Compile all Java files:
```
javac -d classes -cp "lib/*" $(find . -name "*.java")
```
4. Verify database connection using the DatabaseConnection utility:
```
java -cp "classes:lib/*" JavaFinalWinter2025.utils.DatabaseConnection
```
- This ensures that the system can connect to your PostgreSQL database.

### 4.3 Running the System
1. Launch the main application:
```
java -cp "classes:lib/*" JavaFinalWinter2025.src.Main
```

2. Main Menu Display:
The console will display a menu with options to:
- Register a new user
- Login as existing user
- Exit the application

3. Login / Register:
- Register: Create a new user account (Admin, Trainer, Member)
- Login: Enter your username and password

4. Role-Based Menu:
After login, the system displays menu options based on your role:
- Admin: Manage users, memberships, workout classes, merchandise
- Trainer: Manage workout classes, view memberships and merchandise
- Member: Browse classes, purchase memberships, view merchandise

5. Select Actions:
- Type the number corresponding to your desired action
- Follow prompts to complete actions (e.g., adding a membership, updating a class)

6. Exiting the System:
- Choose the Exit option from any menu to safely close the application
- All data is stored automatically in the database

### 4.4 Tips for Using the System
- Navigation: Always follow numbered menu prompts; typing invalid options will display an error message.
- Security: Passwords are encrypted using BCrypt—do not attempt to edit database password fields manually.
- Data Consistency: Always complete actions fully; partially entered information may cause errors.
- Logs: System events are recorded in gym_log.txt for auditing and troubleshooting.

### 4.6 Example Session Flow
CHANGE THIS WHEN WORKING
```
Welcome to Gym Management System
1. Register
2. Login
3. Exit
Enter choice: 2

Enter username: trainer1
Enter password: *****

--- Trainer Menu ---
1. View Workout Classes
2. Create Workout Class
3. Update Workout Class
4. Purchase Membership
5. View Merchandise
6. Logout

Enter choice: 2
Enter class type: Yoga
Enter description: Morning Yoga for beginners
Class created successfully!

Enter choice: 6
Logging out...
```



## 5. Individual Report
### 5.1 Contributions
#### Models Layer
Developed the foundational data models that represent gym entities:
- User.java: Base user model with authentication and contact information
- Admin.java: Admin-specific functionality extending User class
- Trainer.java: Trainer model for fitness instructors
- Member.java: Member model for gym customers
- Membership.java: Membership model with tiered pricing
- WorkoutClass.java: Workout class model with scheduling capabilities
- GymMerch.java: Merchandise model for inventory management

#### Services Layer
Implemented business logic services that coordinate application operations:
- GymMerchService.java: Service layer for managing GymMerch operations. 
- WorkoutClassService.java: Service layer for managing WorkoutClass operations.
- UserService.java: Service class for handling user-related operations such as registration and login.
- MembershipService.java: Service class for handling membership-related operations.


#### Utilities
- Created core utilities for database connectivity and logging:
- DatabaseConnection.java: DatabaseConnection class to manage database connections.
- LoggerUtil.java: Centralized logging system for system monitoring

#### Pull Requests & Branches
Contributed through multiple feature branches and pull requests:
- magret/databaseconnection: Database connection infrastructure
- magret/classes: Core model implementations
- magret/services: Business logic service layer
- magret/readme: Project documentation and setup instructions

5.2 Team Contributions
- Participated in code reviews and PR
- Collaborated on API design and database schema decisions
- Assisted in debugging and troubleshooting team code
- Contributed to project documentation and deployment guides

5.3 Challenges Faced
1. Environment Configuration
**Challenge**: Importing and configuring the dotenv library for environment variable management was initially problematic.

2. Project Structure Understanding
**Challenge**: Initially struggled with understanding the project architecture, leading to some disorganized code placement.

3. Service Testing Difficulties
**Challenge**: Testing services proved difficult due to complex dependencies and database state management.
