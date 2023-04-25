package playerGUI;

import static org.junit.Assert.*;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.BeforeClass;
import org.junit.Test;

import playerClient.PlayerClient;

public class LoginControlTest {

	private static JPanel container;
	private static PlayerClient client;
	private static JPanel view1;
	private static JPanel view2;
	private static JPanel view3;
	private static JPanel view4;
	private static JPanel view5;
	private static InitialControl ic;
	private static LoginControl lc;
	private static CreateAccountControl cac;
	private static InitialMenuControl imc;
	private static GameControl gc;

	private JButton submitButton;
	private JButton cancelButton;

	
	
	@BeforeClass
	public static void setUp() {
		CardLayout cardLayout = new CardLayout();
		container = new JPanel(cardLayout);
		ic = new InitialControl(container, client);
		lc = new LoginControl(container, client);
		cac = new CreateAccountControl(container, client);
		imc = new InitialMenuControl(container, client);
		gc = new GameControl(container, client);

		// Set the client info
		client = new PlayerClient();
		client.setLoginControl(lc);
		client.setCreateAccountControl(cac);
		client.setInitialMenuControl(imc);
		client.setGameControl(gc);
		// Create the four views. (need the controller to register with the Panels
		 view1 = new InitialPanel(ic);
		 view2 = new LoginPanel(lc);
		 view3 = new CreateAccountPanel(cac);
		view4 = new InitialMenuPanel(imc);
		 view5 = new GamePanel(gc);

		// Add the views to the card layout container.
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");
		container.add(view4, "4");
		container.add(view5, "5");
	}

	@Test
	public void testActionListeners() {
		

		cancelButton = ((LoginPanel) view2).getCancelButton();
		cancelButton.addActionListener(lc);
		submitButton = ((LoginPanel) view2).getSubmitButton();
		submitButton.addActionListener(lc);

		String test1 = cancelButton.getActionCommand();
		String test2 = submitButton.getActionCommand();

		assertEquals("Cancel", test1);
		assertEquals("Submit", test2);

		if (test1.equals("Cancel")) {
			// check that the correct panel is displayed
			container.add(view1, "2");
			Component[] components = container.getComponents();
			Component expectedPanel = components[0];
			Component actualPanel = view1.getParent().getComponent(0);
			assertEquals(expectedPanel, actualPanel);
		} else if (test2.equals("Submit")) {
			container.add(view4, "5");
			Component[] components = container.getComponents();
			Component expectedPanel = components[2];
			Component actualPanel = view5.getParent().getComponent(0);
			assertEquals(expectedPanel, actualPanel);
		}
	}

}
