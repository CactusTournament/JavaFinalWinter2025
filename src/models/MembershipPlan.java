package models;

/**
 * MembershipPlan represents a purchasable membership offering stored in the database.
 * Plans are catalog entries (e.g. Basic/Standard/Premium) that users can select when
 * purchasing a membership. Each plan has a type, description and a price.
 *
 * Author: Brandon Maloney
 * Date: 2025-12-17
 */
public class MembershipPlan {
    private int planId;
    private String planType;
    private String planDescription;
    private double planPrice;

    public MembershipPlan(int planId, String planType, String planDescription, double planPrice) {
        this.planId = planId;
        this.planType = planType;
        this.planDescription = planDescription;
        this.planPrice = planPrice;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public double getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(double planPrice) {
        this.planPrice = planPrice;
    }

    @Override
    public String toString() {
        return "MembershipPlan{" +
                "planId=" + planId +
                ", planType='" + planType + '\'' +
                ", planDescription='" + planDescription + '\'' +
                ", planPrice=" + planPrice +
                '}';
    }
}
