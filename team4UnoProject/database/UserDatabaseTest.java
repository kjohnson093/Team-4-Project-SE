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
			userDatabase.createNewAccount("howdy", "partner");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		userDatabase.addScore("howdy");
		
		// Check that the user's score is 1.
		HashMap<String, Integer> scoreboardData = userDatabase.displayScorebaord();
		assertEquals(Integer.valueOf(1), scoreboardData.get("howdy"));
		try {
			deleteTestUser("howdy");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddScoreExistingUser() throws SQLException {
		// Add a score for an existing user.
		
		try {
			userDatabase.createNewAccount("macaroni", "andcheese");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		userDatabase.addScore("macaroni");
		
		// Add another score for the same user.
		userDatabase.addScore("macaroni");
		
		// Check that the user's score is 2.
		HashMap<String, Integer> scoreboardData = userDatabase.displayScorebaord();
		assertEquals(Integer.valueOf(2), scoreboardData.get("macaroni"));
		
		try {
			deleteTestUser("macaroni");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDisplayScoreboard() throws SQLException {
		try {
			userDatabase.createNewAccount("rick", "testpassword");
			userDatabase.createNewAccount("morty", "test2password");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Add scores for two users.
		userDatabase.addScore("rick");
		userDatabase.addScore("morty");
		
		// Check that the scoreboard data is correct.
		HashMap<String, Integer> scoreboardData = userDatabase.displayScorebaord();
		assertEquals(Integer.valueOf(1), scoreboardData.get("rick"));
		assertEquals(Integer.valueOf(1), scoreboardData.get("morty"));
		
		try {
			deleteTestUser("rick");
			deleteTestUser("morty");
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
