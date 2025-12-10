package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.src.Membership;
import JavaFinalWinter2025.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MembershipDAO
 * Data Access Object (DAO) class for performing CRUD operations on Membership records.
 * Handles creation, retrieval, update, and deletion of Membership records in the database.
 * 
 * Author: Brandon Maloney
 * Date: 2025-12-08
 */
public class MembershipDAO {

    /**
     * Creates a new membership in the database.
     *
     * @param membership The Membership object to add.
     * @return true if the membership was successfully created; false otherwise.
     */
    public boolean createMembership(Membership membership) {
        String query = "INSERT INTO Memberships (membershipType, membershipDescription, membershipCost, memberID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, membership.getMembershipType());
            ps.setString(2, membership.getMembershipDescription());
            ps.setDouble(3, membership.getMembershipCost());
            ps.setInt(4, membership.getMemberID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a membership by its ID.
     *
     * @param membershipID The ID of the membership.
     * @return Membership object if found; null otherwise.
     */
    public Membership getMembershipById(int membershipID) {
        String query = "SELECT * FROM Memberships WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, membershipID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Membership(
                        rs.getInt("membershipID"),
                        rs.getString("membershipType"),
                        rs.getString("membershipDescription"),
                        rs.getDouble("membershipCost"),
                        rs.getInt("memberID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all memberships from the database.
     *
     * @return List of Membership objects. Returns an empty list if none are found.
     */
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String query = "SELECT * FROM Memberships";
        try (Connection conn = DatabaseConnection.getcon();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                memberships.add(new Membership(
                        rs.getInt("membershipID"),
                        rs.getString("membershipType"),
                        rs.getString("membershipDescription"),
                        rs.getDouble("membershipCost"),
                        rs.getInt("memberID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberships;
    }

    /**
     * Updates an existing membership in the database.
     *
     * @param membership The Membership object with updated information.
     * @return true if the update was successful; false otherwise.
     */
    public boolean updateMembership(Membership membership) {
        String query = "UPDATE Memberships SET membershipType = ?, membershipDescription = ?, membershipCost = ?, memberID = ? WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, membership.getMembershipType());
            ps.setString(2, membership.getMembershipDescription());
            ps.setDouble(3, membership.getMembershipCost());
            ps.setInt(4, membership.getMemberID());
            ps.setInt(5, membership.getMembershipID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a membership from the database by its ID.
     *
     * @param membershipID The ID of the membership to delete.
     * @return true if the deletion was successful; false otherwise.
     */
    public boolean deleteMembership(int membershipID) {
        String query = "DELETE FROM Memberships WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getcon();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, membershipID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
