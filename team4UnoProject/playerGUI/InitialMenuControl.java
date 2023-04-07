package playerGUI;

import java.awt.*;
import javax.swing.*;

import playerClient.playerClient;

import java.awt.event.*;

public class InitialMenuControl implements ActionListener
{
  // Private data field for storing the container.
  private JPanel container;
  private playerClient client;
  // Constructor for the initial controller.
  public InitialMenuControl(JPanel container, playerClient client)
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
      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
      loginPanel.setError("");
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "2");
     
    }
    
    // The Create button takes the user to the create account panel.
    else if (command.equals("New Game"))
    {
      CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
      createAccountPanel.setError("");
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "3");
    }
  }
}

