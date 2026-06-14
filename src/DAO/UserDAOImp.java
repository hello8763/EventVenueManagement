package DAO;

import Databases.mysqlDBConnection;
import Model.User;
import Model.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO {

    @Override
    public String[] authenticateUser(String email, String password) {
        String query = "SELECT u.userId, p.role FROM User u JOIN Profile p ON u.userId = p.userId WHERE u.email = ? AND u.passwordHash = ? AND u.isActive = 1";
        try (Connection conn = mysqlDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new String[]{ String.valueOf(rs.getInt("userId")), rs.getString("role") };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean registerCustomer(User user, Profile profile) {
        String userSql = "INSERT INTO User (email, passwordHash, isActive) VALUES (?, ?, ?)";
        String profileSql = "INSERT INTO Profile (userId, fullName, phoneNumber, role, address) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = mysqlDBConnection.getConnection();
            conn.setAutoCommit(false); // Begin Transaction

            int newUserId = -1;
            try (PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, user.getEmail());
                userStmt.setString(2, user.getPasswordHash());
                userStmt.setBoolean(3, true);
                userStmt.executeUpdate();

                try (ResultSet rs = userStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        newUserId = rs.getInt(1);
                    }
                }
            }

            if (newUserId == -1) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement profileStmt = conn.prepareStatement(profileSql)) {
                profileStmt.setInt(1, newUserId);
                profileStmt.setString(2, profile.getFullName());
                profileStmt.setString(3, profile.getPhoneNumber());
                profileStmt.setString(4, "Customer");
                profileStmt.setString(5, profile.getAddress());
                profileStmt.executeUpdate();
            }

            conn.commit(); // Commit Transaction
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    // Add this implementation at the bottom of the class:
    @Override
    public List<Object[]> getAllUsersWithProfiles() {
        List<Object[]> list = new ArrayList<>();
        // JOIN the User and Profile tables to get the full picture
        String sql = "SELECT u.userId, u.email, p.fullName, p.phoneNumber, p.role, p.address " +
                     "FROM User u JOIN Profile p ON u.userId = p.userId";
                     
        try (Connection con = mysqlDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("userId"),
                    rs.getString("fullName"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("role"),
                    rs.getString("address")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}