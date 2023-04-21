package playerGUI;

import java.awt.*;
import javax.swing.*;

import playerClient.PlayerClient;

//import playerClient.PlayerClient;

import java.awt.event.*;
import java.io.IOException;

public class InitialMenuControl implements ActionListener
{
	// Private data field for storing the container.
	private JPanel container;
	private PlayerClient client;

	// Constructor for the initial controller.
	public InitialMenuControl(JPanel container, PlayerClient client)
	{
		this.container = container;
		this.client = client;
	}


	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Login button takes the user to the login panel.
		if (command.equals("Join Game"))
		{
			GamePanel GamePanel = (GamePanel)container.getComponent(4);
			//loginPanel.setError("");
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "5");
			try {
				client.sendToServer("LOGIN SUCCESS, UPDATE GAMEPANEL NOW**");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// The Create button takes the user to the create account panel.
		else if (command.equals("New Game"))
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			//createAccountPanel.setError("");
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "5");
			
			try {
				client.sendToServer("LOGIN SUCCESS, UPDATE GAMEPANEL NOW**");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
