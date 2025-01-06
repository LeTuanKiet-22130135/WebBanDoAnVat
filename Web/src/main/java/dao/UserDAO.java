package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	private final Connection connection;

	public UserDAO() {
		this.connection = DBConnection.getConnection();
	}

	// Fetch all users
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String query = "SELECT id, username, isAdmin FROM user";

		try (PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setAdmin(rs.getBoolean("isAdmin")); // Populate isAdmin field
				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	// Delete a user by ID
	public boolean deleteUserById(int userId) {
		String query = "DELETE FROM user WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Update user's admin status
	public boolean updateUserRole(int userId, boolean isAdmin) {
		String query = "UPDATE user SET isAdmin = ? WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setBoolean(1, isAdmin);
			stmt.setInt(2, userId);
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Fetch a single user by ID
	public User getUserById(int userId) {
		String query = "SELECT id, username, isAdmin FROM user WHERE id = ?";
		User user = null;

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setAdmin(rs.getBoolean("isAdmin"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public int getUserIdByUsername(String username) {
		String query = "SELECT id FROM user WHERE username = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Return -1 if no user is found
	}

	public void addUserWithProfile(String username, String password, String firstName, String lastName, String email) {
		String userQuery = "INSERT INTO user (username, password, isAdmin) VALUES (?, ?, ?)";
		String profileQuery = "INSERT INTO userprofile (user_id, first_name, last_name, email) VALUES (?, ?, ?, ?)";

		try {
			connection.setAutoCommit(false); // Begin transaction
			PreparedStatement userStmt = connection.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
			// Insert user
			userStmt.setString(1, username);
			userStmt.setString(2, password); // Default password (to be updated later)
			userStmt.setBoolean(3, false); // Regular user, not admin
			userStmt.executeUpdate();

			// Get generated user ID
			ResultSet rs = userStmt.getGeneratedKeys();
			if (rs.next()) {
				int userId = rs.getInt(1);
				PreparedStatement profileStmt = connection.prepareStatement(profileQuery);
				// Insert profile
				profileStmt.setInt(1, userId);
				profileStmt.setString(2, firstName);
				profileStmt.setString(3, lastName);
				profileStmt.setString(4, email);
				profileStmt.executeUpdate();
			}

			connection.commit(); // Commit transaction
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
