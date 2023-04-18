package Game;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class ClientGUI extends JFrame
{
	GameManagement management;
	public ClientGUI()
	{
		GameClient client = new GameClient();
		client.setHost("localhost");
		client.setPort(8300);
		try
		{
			client.openConnection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);
		GameControl gc = new GameControl(container, client);
		client.setGameControl(gc);
		JPanel view4 = new GamePanel(gc);
		container.add(view4,"4");
		cardLayout.show(container, "4");
		
		this.add(container);
		this.setSize(1580, 900);
		this.setVisible(true);
		this.setTitle("UNO");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	}
	public void setGameManagement(GameManagement management) {
		this.management = management;
	}
	
	public static void main(String[] args)
	{
		new ClientGUI();
	}
}