package JavaFinalWinter2025.dao;

import JavaFinalWinter2025.Membership;
import JavaFinalWinter2025.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAO {

    // Create a membership
    public boolean createMembership(Membership membership) {
        String query = "INSERT INTO Memberships (membershipType, membershipDescription, membershipCost, memberID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
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

    // Get a membership by ID
    public Membership getMembershipById(int membershipID) {
        String query = "SELECT * FROM Memberships WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
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

    // Get all memberships
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String query = "SELECT * FROM Memberships";
        try (Connection conn = DatabaseConnection.getConnection();
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

    // Update a membership
    public boolean updateMembership(Membership membership) {
        String query = "UPDATE Memberships SET membershipType = ?, membershipDescription = ?, membershipCost = ?, memberID = ? WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
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

    // Delete a membership by ID
    public boolean deleteMembership(int membershipID) {
        String query = "DELETE FROM Memberships WHERE membershipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, membershipID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
