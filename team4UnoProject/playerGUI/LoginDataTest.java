package playerGUI;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LoginDataTest {
	
	@Test
	public void testGettersAndSetters() {
		// Create a LoginData object with a specific username and password
		LoginData ld = new LoginData("test", "testpassword");
		
		// Test the getters
		assertEquals("test", ld.getUsername());
		assertEquals("testpassword", ld.getPassword());
		
		// Test the setters
		ld.setUsername("newuser");
		ld.setPassword("newpassword");
		assertEquals("newuser", ld.getUsername());
		assertEquals("newpassword", ld.getPassword());
	}
}
