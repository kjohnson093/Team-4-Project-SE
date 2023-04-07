package playerGUI;

import java.awt.*;
import javax.swing.*;

public class InitialMenuPanel extends JPanel {
	// Constructor for the initial panel.
	public InitialMenuPanel(InitialMenuControl ic) {
		// Set the background color to red.
		setBackground(Color.RED);
		
		// Create the image label.
		ImageIcon image = new ImageIcon("Uno.png"); 
		JLabel imageLabel = new JLabel(image);
		
		int desiredWidth = 200; // Specify the desired width
		int desiredHeight = 150; // Specify the desired height

		// Create a new Image object with the desired width and height
		Image resizedImage = image.getImage().getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

		// Create a new ImageIcon object with the resized Image
		ImageIcon resizedIcon = new ImageIcon(resizedImage);		

		// Create the login button.
		JButton loginButton = new JButton("Join Game");
		loginButton.addActionListener(ic);
		// Create the create account button.
		JButton createButton = new JButton("New Game");
		createButton.addActionListener(ic);

		// Create a panel for the buttons and add them to it.
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(loginButton);
		buttonsPanel.add(createButton);
		buttonsPanel.setBackground(Color.RED);

		// Arrange the components in a grid.
		setLayout(new BorderLayout());
		//add(label, BorderLayout.NORTH);
		add(imageLabel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
}
