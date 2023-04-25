package playerGUI;

import org.junit.*;

import playerClient.PlayerClient;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanelTest {
	private LoginPanel panel;
	private LoginControl lc;
	private static PlayerClient client;
	private String username;
	private String password;
	private JButton submitButton;
	private JButton cancelButton;
	private boolean submitClick = false;
	private boolean cancelClick = false;
	@Before
	public void setUp() {
		// Create a new LoginPanel and a LoginControl for testing
		lc = new LoginControl(panel, client);
		panel = new LoginPanel(lc);
	}

	@Test
	public void testButtonClick() {
		cancelButton = panel.getCancelButton();
		cancelButton.addActionListener(lc);
		submitButton = panel.getSubmitButton();
		submitButton.addActionListener(lc);
		
		String test1 = cancelButton.getActionCommand();
		String test2 = submitButton.getActionCommand();


		// Simulate a user entering a username and password
		username ="testuser";
		password = "testpassword";

		// Simulate a click on the "Submit" button submit = panel.getSubmitButton();
		submitClick = true;
		cancelClick = true;
		if (submitClick = true) {
			LoginData ld = new LoginData(username, password);
			assertEquals("testuser", ld.getUsername());
			assertEquals("testpassword", ld.getPassword());
			assertEquals("Submit", test2);
		}
		if (cancelClick = true) {
			assertEquals("Cancel", test1);
		}
		
	
	}

	@Test
	public void testCancelButton() {

	}
}
