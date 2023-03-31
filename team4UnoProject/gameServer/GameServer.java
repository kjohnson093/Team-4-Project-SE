package gameServer;

import ocsf.server.*;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JTextArea;



public class GameServer extends AbstractServer {

	//Corresponding text area and label with ServerGUI
	private JTextArea log; 
	private JLabel status; 
	
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


	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}
