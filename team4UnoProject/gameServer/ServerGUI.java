package gameServer;

import javax.swing.*;
import java.awt.*;
//import java.io.IOException;

public class ServerGUI extends JFrame {

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
		JButton start = new JButton("Start");
		JButton close = new JButton("Close");
		JButton stop = new JButton("Stop");
		JButton quit = new JButton("Quit");
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
		JLabel serverLogLabel = new JLabel("Server Log Below");
		serverLogLabel.setBounds(139, 91, 106, 22);
		center.add(serverLogLabel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 124, 386, 232);
		center.add(scrollPane);
		JTextArea serverLogTextArea = new JTextArea();
		scrollPane.setViewportView(serverLogTextArea);
		serverLogTextArea.setLineWrap(true);
		serverLogTextArea.setEditable(false);

		//Port components
		JLabel portLabel = new JLabel("Port #");
		portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		portLabel.setBounds(86, 11, 75, 17);
		center.add(portLabel);
		JTextField portField = new JTextField();
		portField.setBounds(171, 8, 96, 20);
		center.add(portField);
		portField.setColumns(10);
		
		//Timeout components
		JLabel timeoutLabel = new JLabel("Timeout");
		timeoutLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeoutLabel.setBounds(86, 42, 74, 17);
		center.add(timeoutLabel);
		JTextField timeoutField = new JTextField();
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

		/////Create Server Object/////
		GameServer server = new GameServer();
		//Set server log and status to GUI
		server.setStatus(statusConditionLabel);
		server.setLog(serverLogTextArea);

	}

	public static void main(String[] args) {
		new ServerGUI();
	}
}
