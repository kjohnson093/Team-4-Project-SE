package database;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
//hgkhgkj
public class UserDatabase {
	// Private data fields for storing the file streams.
	FileInputStream fis;
	FileOutputStream fos;
	private Connection conn;
	
	public UserDatabase() {
		Properties prop = new Properties();
		try {
			fis = new FileInputStream("database/db.properties");
			prop.load(fis);
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("pass");
			conn = DriverManager.getConnection(url, user, password);
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method for verifying a username and password.
	public boolean verifyAccount(String username, String password) throws Exception {
		// Read the database file.
			// Create a Statement to execute a SELECT query.
			Statement statement = conn.createStatement();
			// Execute the query and get the results.
			ResultSet resultSet = statement.executeQuery("SELECT username FROM users WHERE username = '" + username + "' AND password = AES_ENCRYPT('" + password + "', 'key')");
				if (resultSet.next())
				{
					return true;
				}
				else 
				{
					return false;
				} 
		}

	// Method for creating a new account.
	public boolean createNewAccount(String username, String password) throws Exception{
			// Create a Statement to execute a SELECT query.
			Statement statement = conn.createStatement();
			// Execute the query and get the results.
			ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
			if (!resultSet.next()) 
			{
				//sql query to encrypt the password
				String encryptedPassword = "AES_ENCRYPT('" + password + "', 'key')";
				//sql query to insert the user into the table.
				String sql = "INSERT INTO users (username, password) VALUES ('" + username + "', " + encryptedPassword + ")";
				statement.executeUpdate(sql);
				return true;
	        } else 
	        {
				return false;
		
	        }

		}
	//adds the user to the scoreboard table or updates the users score in the table
	public void addScore(String username) throws SQLException {
	   
	        Statement statement = conn.createStatement();   
	        
	        // Check if username already exists in the table
	        String check = "SELECT * FROM scoreboard WHERE username = '" + username + "'";
	        ResultSet rs = statement.executeQuery(check);
	        if (!rs.next()) {
	            // If username does not exist, insert a new row with the given username and score of 1
	            String insert = "INSERT INTO scoreboard(username, score) VALUES ('" + username + "', 1)";
	            statement.executeUpdate(insert);
	        } else {
	            // If username exists, update the score
	            String update = "UPDATE scoreboard SET score = score + 1 WHERE username = '" + username + "'";
	            statement.executeUpdate(update);
	        }
	        
	        // Close the result set, statement and connection.
	        rs.close();
	        statement.close();
	       
	    } 
	//change to arraylist?
	public HashMap<String, Integer> displayScorebaord() {
		// Create a new HashMap for the database.
		HashMap<String, Integer> scoreboard = new HashMap<String, Integer>();
		try {
			// Create a Statement to execute a SELECT query.
			Statement statement = conn.createStatement();

			// Execute the query and get the results.
			ResultSet resultSet = statement.executeQuery("SELECT username, score FROM scoreboard");

			// Loop through every row in the result set.
			while (resultSet.next()) {
				// Get the username and password for this row using column names.
				String username = resultSet.getString("username");
				int score = resultSet.getInt("score");

				// Save the username and password in the HashMap.
			scoreboard.put(username, score);
			}
			
			// Close the statement, result set, and connection.
			statement.close();
			resultSet.close();
			
		}
		// If an exception occurs, print the stack trace.
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return scoreboard;
	
	}
}




