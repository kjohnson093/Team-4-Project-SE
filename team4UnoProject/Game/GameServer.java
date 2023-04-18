package Game;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer{
	
	GameManagement manageGame;
	private JTextArea log;
	private JLabel status;
	private boolean running = false;
	
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
	
	public void setGameManagement(GameManagement manageGame) {
		this.manageGame = manageGame;
	}
	

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		if(arg0 instanceof TopCard) {
			manageGame.setTopCard((TopCard)arg0);
			this.sendToAllClients((TopCard)arg0);
		}
		if(arg0 instanceof String) {
			String message = (String)arg0;
			if(message.equals("REQUEST A CARD")) {
				try {
					System.out.println("card requested");
					arg1.sendToClient(manageGame.addCard());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	
	
	
	
	public boolean isRunning()
	{
		return running;
	}

	// Setters for the data fields corresponding to the GUI elements.
	public void setLog(JTextArea log)
	{
		this.log = log;
	}
	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	// When the server starts, update the GUI.
	public void serverStarted()
	{
		running = true;
		status.setText("Listening");
		status.setForeground(Color.GREEN);
		log.append("Server started\n");
	}

	// When the server stops listening, update the GUI.
	public void serverStopped()
	{
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
	}

	// When the server closes completely, update the GUI.
	public void serverClosed()
	{
		running = false;
		status.setText("Close");
		status.setForeground(Color.RED);
		log.append("Server and all current clients are closed - press Listen to restart\n");
	}

	// When a client connects or disconnects, display a message in the log.
	public void clientConnected(ConnectionToClient client)
	{
		log.append("Client " + client.getId() + " connected\n");
		manageGame.numberOfPlayers++;
		
		try {
			client.sendToClient(manageGame.topCard);
			client.sendToClient(manageGame.deal7());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
