/*
 * Keondre Johnson
 * 
 * This program runs a server for handling clients connecting to each other in an UNO! game.
 * The server will also maintain the database.
 */
package gameServer;

import ocsf.server.*;
import java.awt.Color;
import java.io.IOException;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import database.*;
import gameManagement.*;
import playerGUI.*;
import playerClient.*;

public class GameServer extends AbstractServer {

	//Corresponding text area and label with ServerGUI
	private JTextArea log; 
	private JLabel status; 
	private boolean running = false;
	//private ArrayList<Game> activeGames;
	private UserDatabase database = new UserDatabase();

	public GameServer() {
		super(12345); //dummy port to be overridden by input
		setTimeout(500);
	}

	//Set ServerGUI log and status
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
		try {
			client.sendToClient("username: client-" + client.getId());
			log.append("Client " + client.getId() + " Connected" + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
							log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
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
							log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
						}
					} catch (Exception e1) {
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
				else if (arg0 instanceof GameData)
				{
					//Take in Game command
					
					//Based on command, manage the game by invoking its methods
					
					//Fetch existing  game
						//Create initial hand and first cards
					
					//Get player turn 
					
					//Validate move
					
					//Update game
					
					
					
				}
	}


}
