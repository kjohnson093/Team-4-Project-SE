package database;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UserDatabaseTest {
	private UserDatabase userDatabase;
	private Connection conn;

	@Before
	public void setUp() throws Exception {
		String url = "jdbc:mysql://localhost:3306/student_space";
		String user = "root";
		String password = null;
		conn = (Connection) DriverManager.getConnection(url, user, password);
		userDatabase = new UserDatabase();
	}

	@Test
	public void testVerifyAccount() throws Exception { // Create a test
		userDatabase.createNewAccount("testuser", "testpassword");

		// Verify the account.
		assertTrue(userDatabase.verifyAccount("testuser", "testpassword"));
		assertFalse(userDatabase.verifyAccount("testuser", "wrongpassword"));

		// Delete the test user.
		deleteTestUser("testuser");
	}

	@Test
	public void testCreateNewAccount() throws Exception {
		// Create a test user.
			assertTrue(userDatabase.createNewAccount("testuser", "testpassword"));

		// Try to create the same user again (should fail).
			assertFalse(userDatabase.createNewAccount("testuser", "testpassword"));
			
		// Delete the test user.
			deleteTestUser("testuser");
	}

	@Test
	public void testAddScoreNewUser() throws SQLException {
		// Add a score for a new user.
		try {
			userDatabase.createNewAccount("newUser", "password123");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		userDatabase.addScore("newUser");
		
		// Check that the user's score is 1.
		HashMap<String, Integer> scoreboardData = userDatabase.displayScorebaord();
		assertEquals(Integer.valueOf(1), scoreboardData.get("newUser"));
		try {
			deleteTestUser("newUser");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddScoreExistingUser() throws SQLException {
		// Add a score for an existing user.
		
		try {
			userDatabase.createNewAccount("user1", "password");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		userDatabase.addScore("user1");
		
		// Add another score for the same user.
		userDatabase.addScore("user1");
		
		// Check that the user's score is 2.
		HashMap<String, Integer> scoreboardData = userDatabase.displayScorebaord();
		assertEquals(Integer.valueOf(2), scoreboardData.get("user1"));
		
		try {
			deleteTestUser("user1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDisplayScoreboard() throws SQLException {
		try {
			userDatabase.createNewAccount("testUser1", "testpassword");
			userDatabase.createNewAccount("testUser2", "test2password");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Add scores for two users.
		userDatabase.addScore("testUser1");
		userDatabase.addScore("testUser2");
		
		// Check that the scoreboard data is correct.
		HashMap<String, Integer> scoreboardData = userDatabase.displayScorebaord();
		assertEquals(Integer.valueOf(1), scoreboardData.get("testUser1"));
		assertEquals(Integer.valueOf(1), scoreboardData.get("testUser2"));
		
		try {
			deleteTestUser("testUser1");
			deleteTestUser("testUser2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void deleteTestUser(String username) throws Exception {
		// Delete the user from the database.
		Statement statement = (Statement) conn.createStatement();
		statement.executeUpdate("Delete From scoreboard where username = '" + username + "'");
		statement.executeUpdate("DELETE FROM users WHERE username='" + username + "'");
		statement.close();
	}

}
