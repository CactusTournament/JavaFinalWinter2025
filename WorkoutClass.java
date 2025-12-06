package JavaFinalWinter2025;

// CREATE TABLE IF NOT EXISTS WorkoutClasses (
//     workoutClassID SERIAL PRIMARY KEY,
//     workoutClassType TEXT NOT NULL,
//     workoutClassDescription TEXT,
//     trainerID INT,
//     FOREIGN KEY (trainerID) REFERENCES Users(userId)
// );

/**
 * WorkoutClass class representing workout class details in the system.
 * Fields correspond to the WorkoutClasses table in the database.
 * 
 * Fields:
 * - workoutClassID: Unique identifier for the workout class
 * - workoutClassType: Type of the workout class
 * - workoutClassDescription: Description of the workout class
 * - trainerID: Identifier for the trainer associated with the workout class
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class WorkoutClass {
    private int workoutClassID;
    private String workoutClassType;
    private String workoutClassDescription;
    private int trainerID;

    /**
     * Constructor to initialize a WorkoutClass object.
     * 
     * @param workoutClassID Unique identifier for the workout class
     * @param workoutClassType Type of the workout class
     * @param workoutClassDescription Description of the workout class
     * @param trainerID Identifier for the trainer associated with the workout class
     */
    public WorkoutClass(int workoutClassID, String workoutClassType, String workoutClassDescription, int trainerID) {
        this.workoutClassID = workoutClassID;
        this.workoutClassType = workoutClassType;
        this.workoutClassDescription = workoutClassDescription;
        this.trainerID = trainerID;
    }

    /**
     * Getter for workoutClassID.
     * @return the workoutClassID
     */
    public int getWorkoutClassID() {
        return workoutClassID;
    }

    /**
     * Setter for workoutClassID.
     * @param workoutClassID the workoutClassID to set
     */
    public void setWorkoutClassID(int workoutClassID) {
        this.workoutClassID = workoutClassID;
    }

    /**
     * Getter for workoutClassType.
     * @return the workoutClassType
     */
    public String getWorkoutClassType() {
        return workoutClassType;
    }

    /**
     * Setter for workoutClassType.
     * @param workoutClassType the workoutClassType to set
     */
    public void setWorkoutClassType(String workoutClassType) {
        this.workoutClassType = workoutClassType;
    }

    /**
     * Getter for workoutClassDescription.
     * @return the workoutClassDescription
     */
    public String getWorkoutClassDescription() {
        return workoutClassDescription;
    }

    /**
     * Setter for workoutClassDescription.
     * @param workoutClassDescription the workoutClassDescription to set
     */
    public void setWorkoutClassDescription(String workoutClassDescription) {
        this.workoutClassDescription = workoutClassDescription;
    }

    /**
     * Getter for trainerID.
     * @return the trainerID
     */
    public int getTrainerID() {
        return trainerID;
    }

    /**
     * Setter for trainerID.
     * @param trainerID the trainerID to set
     */
    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    /**
     * Override toString method for better representation of WorkoutClass object.
     * @return String representation of the WorkoutClass object
     */
    @Override
    public String toString() {
        return "WorkoutClass{" +
                "workoutClassID=" + workoutClassID +
                ", workoutClassType='" + workoutClassType + '\'' +
                ", workoutClassDescription='" + workoutClassDescription + '\'' +
                ", trainerID=" + trainerID +
                '}';
    }

}