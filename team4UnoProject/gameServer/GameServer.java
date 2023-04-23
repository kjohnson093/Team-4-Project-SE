/*
 * Keondre Johnson
 * 
 * This program runs a server for handling clients connecting to each other in an UNO! game.
 * The server will also maintain the database.
 */
// Package that the GameServer class belongs to.
package gameServer;

// import various libraries and classes
import ocsf.server.*;
import java.awt.Color;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import playerGUI.LoginData;
import playerGUI.CreateAccountData;
import playerGUI.NewGameData;
import playerGUI.JoinGameData;
import playerGUI.TopCard;
import database.UserDatabase;
//Declare the GameServer class and makes it a subclass of the AbstractServer class.
public class GameServer extends AbstractServer {

	//new instance of the GameManagement class, 
	//which will manage the game for the clients that connect to the server.
	GameManagement manageGame=new GameManagement(this);
	//Corresponding text area and label with ServerGUI
	private JTextArea log; 
	private JLabel status; 
	//Tracks whether the server is currently running
	private boolean running = false;
	//private ArrayList<Game> activeGames;
	
	//Represents the database where user account information is stored.
	private UserDatabase database = new UserDatabase();

	public GameServer() {
		super(8300);
		
		this.setPort(8300);
		try {
			this.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	//sets the server's port to 8300 and starts listening for client connections on that port.
	public void setLog(JTextArea log) {
		this.log = log;
	}
	public void setStatus(JLabel status) {
		this.status = status;
	}

	// Set database object to actual mySQL database
	public void setDatabase(UserDatabase db) 
	{
		this.database = db;
	}

	// Getter that returns whether the server is currently running.
	public boolean isRunning()
	{
		return running;
	}

	//alled when an exception occurs while the server is listening for connections. It logs the exception's
	//message to the server's log and prints the stack trace to the console.
	public void listeningException(Throwable exception) {
		log.append("Listening Exception Occurred: " + exception.getMessage() + "\n");
		exception.printStackTrace();;
	}

	//Update GUI in start event
	public void serverStarted() {
		log.append("Server Started" + "\n");
		status.setText("Started");
		status.setForeground(Color.GREEN);
		running = true;
	}

	//Update GUI in stop event
	public void serverStopped() {
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server has stopped accepting new clients - Press Start to start accepting new clients." + "\n");
		running = false;
	}

	//Update GUI in close event
	public void serverClosed() {
		//Update ServerGUI in close event
		status.setText("Closed");
		status.setForeground(Color.RED);
		log.append("Server and all current clients closed - Press Start to restart." + "\n");
		running = false;
	}

	//Update GUI when client connects
	public void clientConnected(ConnectionToClient client) {
		log.append("Client " + client.getId() + " connected\n");
		manageGame.playersConnected(client.getId());
	}
	
	public void clientDisconnected(ConnectionToClient client) {
		log.append("Client " + client.getId() + " Disconnected\n");
		
	}

	@Override
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) 
	{
		// Check what kind of object arg0 is (sent from client), and serve it accordingly

		// Handle login information
		if (arg0 instanceof LoginData)
		{
			// Check the username and password with the database.
			LoginData data = (LoginData)arg0;
			Object result = "LoginFailed";
			try {
				if (database.verifyAccount(data.getUsername(), data.getPassword()))
				{
					result = "LoginSuccessful";
					// Log successful login attempt
					log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
				}
			} catch (Exception e1) {
				// Log failed login attempt and print stack trace
				log.append("Client " + arg1.getId() + " failed to log in\n");
				e1.printStackTrace();
			}

			// Send the result to the client.
			try
			{
				arg1.sendToClient(result);
			}
			catch (IOException e)
			{
				return;
			}
		}

		// Handle create account
		else if (arg0 instanceof CreateAccountData)
		{
			// Try to create the account.
			CreateAccountData data = (CreateAccountData)arg0;
			Object result = "CreateAccountFailed";
			try {
				if (database.createNewAccount(data.getUsername(), data.getPassword()))
				{
					result = "CreateAccountSuccessful";
					// Log successful account creation
					log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
				}
			} catch (Exception e1) {
				// Log failed account creation attempt and print stack trace
				log.append("Client " + arg1.getId() + " failed to create a new account\n");
				e1.printStackTrace();
			}

			// Send the result to the client.
			try
			{
				arg1.sendToClient(result);
			}
			catch (IOException e)
			{
				return;
			}

		}

		//Handle New Game
		else if (arg0 instanceof NewGameData)
		{
			//Assign client player1

			//Wait for other players

			//Send to Game panel when full
		}

		//Handle Join Game
		else if (arg0 instanceof JoinGameData)
		{

			//Search for existing Game slots

			//Assign player number

			// When game slot 2 (or 4) filled, create Game
		}

		//Handle Game object, validate moves, update game state and send to all clients
		if(arg0 instanceof TopCard) 
		{
			// Set the new top card for the game
			manageGame.setTopCard((TopCard)arg0);
			//If card is a draw2 special card, draws card for client
			if (((TopCard)arg0).getType().equals("draw2"))
			{
				// Send the cards to the client.
				try
				{
					arg1.sendToClient(manageGame.Draw2());
				}
				catch (IOException e)
				{
					return;
				}
		    }
			else if(((TopCard)arg0).getType().equals("draw4"))
			{
				// Send the cards to the client.
				try
				{
					arg1.sendToClient(manageGame.Draw4());
				}
				catch (IOException e)
				{
					return;
				}
			}
			else if(((TopCard)arg0).getType().equals("reverse"))
			{
				//Reverse the order for all clients
				manageGame.reverse();
			}
			else if(((TopCard)arg0).getType().equals("reverse"))
			{
				//Skip the next player
				manageGame.skip();
			}
			// Send the new top card to all clients
			//Sends the top card after a special card  is called,
			//so that all clients receive the updated game state
			this.sendToAllClients((TopCard)arg0);
			
			if(((TopCard)arg0).getType().equals("wild")  || ((TopCard)arg0).getType().equals("draw4")){
				this.sendToAllClients(new JLabel("Wild Card Color is "+((TopCard)arg0).getColor()));
			}
			
		}//
		
		// Handle requests to draw a card
		if(arg0 instanceof String) 
		{
			// initialize message to a string casted argument
			String message = (String)arg0;
			if(message.equals("REQUEST A CARD")) 
			{	// Send a card to the client
				try 
				{
					System.out.println("card requested");
					arg1.sendToClient(manageGame.addCard());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Handle successful login and update game panel
			if(message.equals("LOGIN SUCCESS, UPDATE GAMEPANEL NOW**"))
			{
				// Send the top card and deal 7 cards to the client
				try
				{
					arg1.sendToClient(manageGame.getTopCard());
					arg1.sendToClient(manageGame.deal7());
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

