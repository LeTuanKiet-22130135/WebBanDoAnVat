package newdao;

import newmodel.User;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for User entities
 * Handles database operations related to users
 */
public class UserDAO {

    /**
     * Checks if a user exists in the database
     * 
     * @param username The username to check
     * @return true if the user exists, false otherwise
     */
    public boolean checkUser(String username) {
        String query = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if a record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Checks if the provided password matches the stored hashed password for a user
     * 
     * @param username The username of the user
     * @param password The plain text password to check
     * @return true if the password is correct, false otherwise
     */
    public boolean checkPassword(String username, String password) {
        String query = "SELECT hashed_password FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("hashed_password");
                    return PasswordUtil.comparePassword(password, hashedPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets a user by username
     * 
     * @param username The username to search for
     * @return The User object if found, null otherwise
     */
    public User getUserByUsername(String username) {
        String query = "SELECT id, username, hashed_password, email, status FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setHashedPassword(rs.getString("hashed_password"));
                    user.setEmail(rs.getString("email"));
                    user.setStatus(rs.getInt("status"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
