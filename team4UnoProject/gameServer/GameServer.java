/*
 * Keondre Johnson
 * Julius Kwakye
 * RoJon Barnett
 * 
 * This program runs a server for handling clients connecting to each other in an UNO! game.
 * The server will also maintain the database.
 */
package gameServer;

import ocsf.server.*;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;

import playerGUI.LoginData;
import playerGUI.CreateAccountData;

import playerGUI.TopCard;
import database.UserDatabase;

public class GameServer extends AbstractServer {

	GameManagement manageGame=new GameManagement(this);
	//Corresponding text area and label with ServerGUI
	private JTextArea log; 
	private JLabel status; 
	private boolean running = false;
	private String username;
	//private ArrayList<Game> activeGames;
	private UserDatabase database = new UserDatabase();

	public GameServer() {
		super(8300);
		this.setPort(8300);
		try {
			this.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.append("Error starting server: " + e.getMessage());
		}
		// TODO Auto-generated constructor stub
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
		log.append("Client " + client.getId() + " connected\n");
		manageGame.playersConnected(client.getId());
	}
	
	public void clientDisconnected(ConnectionToClient client) {
		log.append("Client " + client.getId() + " Disconnected\n");
		manageGame.removePlayer(client.getId());
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
			this.username = data.getUsername();
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
				log.append("Failed to send to Client " + arg1.getId());
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
				log.append("Failed to create account success message to Client " + arg1.getId());
				return;
			}

		}

		//Handle Game object, validate moves, update game state and send to all clients
		if(arg0 instanceof TopCard) {
			manageGame.setTopCard((TopCard)arg0);
			this.sendToAllClients((TopCard)arg0);
			manageGame.nextPlayer();
			
			if(((TopCard)arg0).getType().equals("draw2")){
				try {
					manageGame.getCurrentPlayer().sendToClient(manageGame.Draw2());
					manageGame.getCurrentPlayer().sendToClient("You Recieved 2 Cards");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.append("Failed to process card from Client " + arg1.getId());
					e.printStackTrace();
				}
				manageGame.nextPlayer();
				manageGame.currentPlayer();
			}
			
			else if(((TopCard)arg0).getType().equals("draw4")){
				
				try {
					manageGame.getCurrentPlayer().sendToClient(manageGame.Draw4());
					manageGame.getCurrentPlayer().sendToClient("You Recieved 4 Cards");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.append("Failed to send card to Client " + arg1.getId());
					e.printStackTrace();
				}
				manageGame.nextPlayer();
				manageGame.currentPlayer();
				
			}
			
			else if(((TopCard)arg0).getType().equals("skip")){
				try {
					manageGame.getCurrentPlayer().sendToClient("You Have Been Skip");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				manageGame.nextPlayer();
				manageGame.currentPlayer();
			}

			else if(((TopCard)arg0).getType().equals("reverse")){
				manageGame.reverse();
				manageGame.nextPlayer();
				manageGame.nextPlayer();
				manageGame.currentPlayer();
			}
			else {
				manageGame.currentPlayer();
			}

			if(((TopCard)arg0).getType().equals("wild")  || ((TopCard)arg0).getType().equals("draw4")){
				this.sendToAllClients(new JLabel("Wild Card Color is "+((TopCard)arg0).getColor()));
			}
			
			
		}

		if(arg0 instanceof String) {
			String message = (String)arg0;
			if(message.equals("REQUEST A CARD")) {
				try {					
					arg1.sendToClient(manageGame.addCard());
					manageGame.nextPlayer();
					manageGame.currentPlayer();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.append("Failed to draw card for Client " + arg1.getId());	
					}
			}
			if(message.equals("WON THE GAME")) {
				String send = this.username+" Has Won The Game";
				try {
					database.addScore(this.username);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.sendToAllClients(send);
			}
			
			if(message.equals("LOGIN SUCCESS, UPDATE GAMEPANEL NOW**")) {

				try {
					arg1.sendToClient(manageGame.getTopCard());
					arg1.sendToClient(manageGame.deal7());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.append("Client " + arg1.getId() + " failed to login");
					e.printStackTrace();
				}
			}
		}
	}
}
