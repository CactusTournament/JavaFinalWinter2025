package models;

/**
 * GymMerch class representing gym merchandise details in the system.
 * Fields correspond to the GymMerch table in the database.
 * 
 * Fields:
 * - merchID: Unique identifier for the merchandise
 * - merchName: Name of the merchandise
 * - merchType: Type of the merchandise
 * - merchPrice: Price of the merchandise
 * - quantityInStock: Quantity of the merchandise in stock
 * 
 * 
 * Author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class GymMerch {
    private int merchID;
    private String merchName;
    private String merchType;
    private double merchPrice;
    private int quantityInStock;

    /**
     * Constructor to initialize a GymMerch object.
     * 
     * @param merchID Unique identifier for the merchandise
     * @param merchName Name of the merchandise
     * @param merchType Type of the merchandise
     * @param merchPrice Price of the merchandise
     * @param quantityInStock Quantity of the merchandise in stock
     */
    public GymMerch(int merchID, String merchName, String merchType, double merchPrice, int quantityInStock) {
        this.merchID = merchID;
        this.merchName = merchName;
        this.merchType = merchType;
        this.merchPrice = merchPrice;
        this.quantityInStock = quantityInStock;
    }

    /**
     * Getter for merchID.
     * @return the merchID
     */
    public int getMerchID() {
        return merchID;
    }

    /**
     * Setter for merchID.
     * @param merchID the merchID to set
     */
    public void setMerchID(int merchID) {
        this.merchID = merchID;
    }

    /**
     * Getter for merchName.
     * @return the merchName
     */
    public String getMerchName() {
        return merchName;
    }

    /**
     * Setter for merchName.
     * @param merchName the merchName to set
     */
    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    /**
     * Getter for merchType.
     * @return the merchType
     */
    public String getMerchType() {
        return merchType;
    }

    /**
     * Setter for merchType.
     * @param merchType the merchType to set
     */
    public void setMerchType(String merchType) {
        this.merchType = merchType;
    }

    /**
     * Getter for merchPrice.
     * @return the merchPrice
     */
    public double getMerchPrice() {
        return merchPrice;
    }

    /**
     * Setter for merchPrice.
     * @param merchPrice the merchPrice to set
     */
    public void setMerchPrice(double merchPrice) {
        this.merchPrice = merchPrice;
    }

    /**
     * Getter for quantityInStock.
     * @return the quantityInStock
     */
    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * Setter for quantityInStock.
     * @param quantityInStock the quantityInStock to set
     */
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    /**
     * Override toString method for better representation of GymMerch object.
     * @return String representation of the GymMerch object
     */
    @Override
    public String toString() {
        return "GymMerch{" +
                "merchID=" + merchID +
                ", merchName='" + merchName + '\'' +
                ", merchType='" + merchType + '\'' +
                ", merchPrice=" + merchPrice +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}