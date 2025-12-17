package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.MembershipPlan;
import utils.DatabaseConnection;

/**
 * DAO for MembershipPlan catalog entries. Exposes methods to list available plans
 * and fetch a single plan by id.
 *
 * Note: this class expects a database table named `MembershipPlans` with columns:
 * planId (auto-increment), planType, planDescription, planPrice.
 */
public class MembershipPlanDAO {

    public MembershipPlanDAO() {
        // no-op
    }

    /**
     * Returns all membership plans from the catalog.
     */
    public List<MembershipPlan> getAllPlans() {
        List<MembershipPlan> plans = new ArrayList<>();
        String sql = "SELECT * FROM MembershipPlans";
        try (Connection conn = DatabaseConnection.getcon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                plans.add(new MembershipPlan(
                        rs.getInt("planId"),
                        rs.getString("planType"),
                        rs.getString("planDescription"),
                        rs.getDouble("planPrice")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }

    /**
     * Fetch a plan by id.
     */
    public MembershipPlan getPlanById(int planId) {
        String sql = "SELECT * FROM MembershipPlans WHERE planId = ?";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, planId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new MembershipPlan(
                        rs.getInt("planId"),
                        rs.getString("planType"),
                        rs.getString("planDescription"),
                        rs.getDouble("planPrice")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
