package gameServer;

import javax.swing.*;
import java.awt.*;
//import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerGUI extends JFrame {


	private JButton start ;
	private JButton close; 
	private JButton stop; 
	private JButton quit; 
	private JLabel serverLogLabel;
	private JLabel portLabel; 
	private JTextField portField; 
	private JLabel timeoutLabel;
	private JTextField timeoutField;
	private JTextArea serverLogTextArea; 
	private GameServer server; 
	//Constructor - Creates view and interactive components for controlling the server
	public ServerGUI() {
		setTitle("ServerGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,450);

		//Create panels, labels, and buttons
		//Server Status Condition Label
		JLabel statusConditionLabel = new JLabel("Not Connected",JLabel.CENTER);
		statusConditionLabel.setForeground(Color.RED);

		/////North Panel//////
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel status = new JLabel("Status: ", JLabel.CENTER);
		north.add(status);
		north.add(statusConditionLabel);

		/////South Panel/////
		JPanel south = new JPanel();

		//Server control buttons
		start = new JButton("Start");
		close = new JButton("Close");
		stop = new JButton("Stop");
		quit = new JButton("Quit");
		south.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		south.add(start);
		south.add(close);
		south.add(stop);
		south.add(quit);

		/////Center Panel/////
		JPanel center = new JPanel();
		getContentPane().add(center, BorderLayout.CENTER);
		center.setLayout(null);

		//Server Log
		serverLogLabel = new JLabel("Server Log Below");
		serverLogLabel.setBounds(139, 91, 106, 22);
		center.add(serverLogLabel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 124, 386, 232);
		center.add(scrollPane);
		serverLogTextArea = new JTextArea();
		scrollPane.setViewportView(serverLogTextArea);
		serverLogTextArea.setLineWrap(true);
		serverLogTextArea.setEditable(false);

		//Port components
		portLabel = new JLabel("Port #");
		portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		portLabel.setBounds(86, 11, 75, 17);
		center.add(portLabel);
		portField = new JTextField("8300");
		portField.setBounds(171, 8, 96, 20);
		center.add(portField);
		portField.setColumns(10);

		//Timeout components
		timeoutLabel = new JLabel("Timeout");
		timeoutLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeoutLabel.setBounds(86, 42, 74, 17);
		center.add(timeoutLabel);
		timeoutField = new JTextField("500");
		timeoutField.setBounds(171, 39, 96, 20);
		center.add(timeoutField);
		timeoutField.setColumns(10);

		//Add Panels to Frame
		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(south, BorderLayout.SOUTH);
		north.setVisible(true);
		south.setVisible(true);
		//Set frame visible
		setVisible(true);

		//Event handling
		EventHandler event = new EventHandler();
		start.addActionListener(event);
		close.addActionListener(event);
		stop.addActionListener(event);
		quit.addActionListener(event);

		/////Create Server Object/////
		server = new GameServer();
		//Set server log and status to GUI
		server.setStatus(statusConditionLabel);
		server.setLog(serverLogTextArea);

	}

	public static void main(String[] args) {
		new ServerGUI();
	}

	// Class for handling events.
	class EventHandler implements ActionListener
	{
		// Event handler for ActionEvent.
		public void actionPerformed(ActionEvent e)
		{
			// Determine which button was clicked.
			Object buttonClicked = e.getSource();

			// Handle the Listen button.
			if (buttonClicked == start)
			{
				// Display an error if the port number or timeout was not entered.
				if (portField.getText().equals("") || timeoutField.getText().equals(""))
				{
					serverLogTextArea.append("Port number or timeout not entered before pressing Listen\n");
				}

				// Otherwise, tell the server to start listening with the user's settings.
				else
				{
					int port = Integer.parseInt(portField.getText());
					int timeout = Integer.parseInt(timeoutField.getText());
					server.setPort(port);
					server.setTimeout(timeout);
					try
					{
						server.listen();
						
					}
					catch (IOException e1)
					{
						serverLogTextArea.append("An exception occurred: " + e1.getMessage() + "\n");
					}
				}
			}

			// Handle the Close button.
			else if (buttonClicked == close)
			{
				// Display an error if the server has not been started.
				if (!server.isRunning())
				{
					serverLogTextArea.append("Server not currently started\n");
				}

				// Otherwise, close the server.
				else
				{
					try
					{
						server.close();
					}
					catch (IOException e1)
					{
						serverLogTextArea.append("An exception occurred: " + e1.getMessage() + "\n");
					}
				}
			}

			// Handle the Stop button.
			else if (buttonClicked == stop)
			{
				// Display an error if the server is not listening.
				if (!server.isListening())
				{
					serverLogTextArea.append("Server not currently listening\n");
				}

				// Otherwise, stop listening.
				else
				{
					server.stopListening();
				}
			}

			// For the Quit button, just stop this program.
			else if (buttonClicked == quit)
			{
				System.exit(0);
			}
		}
	}
}
