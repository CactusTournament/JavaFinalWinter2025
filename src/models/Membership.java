package models;

/**
 * Membership class representing membership details in the system.
 * Fields correspond to the Memberships table in the database.
 * 
 * Fields:
 * - membershipID: Unique identifier for the membership
 * - membershipType: Type of the membership
 * - membershipDescription: Description of the membership
 * - membershipCost: Cost of the membership
 * - memberID: Identifier for the member associated with the membership
 * 
 * Author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class Membership {
    private int membershipID;
    private String membershipType;
    private String membershipDescription;
    private double membershipCost;
    private int memberID;

    /**
     * Constructor to initialize a Membership object.
     * 
     * @param membershipID Unique identifier for the membership
     * @param membershipType Type of the membership
     * @param membershipDescription Description of the membership
     * @param membershipCost Cost of the membership
     * @param memberID Identifier for the member associated with the membership
     */
    public Membership(int membershipID, String membershipType, String membershipDescription, double membershipCost, int memberID) {
        this.membershipID = membershipID;
        this.membershipType = membershipType;
        this.membershipDescription = membershipDescription;
        this.membershipCost = membershipCost;
        this.memberID = memberID;
    }

    /**
     * Getter for membershipID.
     * @return the membershipID
     */
    public int getMembershipID() {
        return membershipID;
    }

    /**
     * Setter for membershipID.
     * @param membershipID the membershipID to set
     */
    public void setMembershipID(int membershipID) {
        this.membershipID = membershipID;
    }

    /**
     * Getter for membershipType.
     * @return the membershipType
     */
    public String getMembershipType() {
        return membershipType;
    }

    /**
     * Setter for membershipType.
     * @param membershipType the membershipType to set
     */
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    /**
     * Getter for membershipDescription.
     * @return the membershipDescription
     */
    public String getMembershipDescription() {
        return membershipDescription;
    }

    /**
     * Setter for membershipDescription.
     * @param membershipDescription the membershipDescription to set
     */
    public void setMembershipDescription(String membershipDescription) {
        this.membershipDescription = membershipDescription;
    }

    /**
     * Getter for membershipCost.
     * @return the membershipCost
     */
    public double getMembershipCost() {
        return membershipCost;
    }

    /**
     * Setter for membershipCost.
     * @param membershipCost the membershipCost to set
     */
    public void setMembershipCost(double membershipCost) {
        this.membershipCost = membershipCost;
    }

    /**
     * Getter for memberID.
     * @return the memberID
     */
    public int getMemberID() {
        return memberID;
    }

    /**
     * Setter for memberID.
     * @param memberID the memberID to set
     */
    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    /**
     * Override toString method for Membership class.
     * @return String representation of the Membership object
     */
    @Override
    public String toString() {
        return "Membership{" +
                "membershipID=" + membershipID +
                ", membershipType='" + membershipType + '\'' +
                ", membershipDescription='" + membershipDescription + '\'' +
                ", membershipCost=" + membershipCost +
                ", memberID=" + memberID +
                '}';
    }
}