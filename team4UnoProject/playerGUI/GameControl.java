package playerGUI;

import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;

import playerClient.PlayerClient;

public class GameControl implements ActionListener{

	private JPanel container;
	JLabel error = new JLabel("PLEASE PLAY A VALID CARD");
	PlayerClient client;

	public GameControl(JPanel container, PlayerClient client) {
		this.container=container;
		this.client= client;
		//System.out.println(this.client.getInetAddress());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if(command == "Use Card"){

			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			gamePanel.deckGroup.getElements();

			Enumeration<AbstractButton> allButtons = gamePanel.deckGroup.getElements();
			Card temp=new Card();
			while(allButtons.hasMoreElements()) {
				temp=(Card)allButtons.nextElement();
				if(temp.isSelected()) {
					break;
				}
			}

			if((gamePanel.topCard.getColor().equals(temp.getColor())&&!temp.getColor().equals("all") || gamePanel.topCard.getValue().equals(temp.getValue())&&!temp.getValue().equals("-1") && !temp.getValue().equals("-2")) && client.isCurrent)            {
				gamePanel.myDeck.remove(temp);
				gamePanel.updateDeck();
				TopCard temp2 = new TopCard(temp.getColor(),temp.getType(),Integer.parseInt(temp.getValue()));
				try {
					client.sendToServer(temp2);
					//client.isCurrentPlayer(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if((temp.getColor().equals("all")||temp.getType().equals("wild"))&& client.isCurrent) {
				String playerChoice=new String();
				Object[] options = {"Blue", "Yellow", "Green", "Red"};
				int n = 0;
				
				do {
				n = JOptionPane.showOptionDialog(null,"Please choose a color", "",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);				
				if(n >= 0) {
					break;
				}
				System.out.println("Please choose a card color");
				}while(true);
				
				playerChoice = (String) options[n];
				TopCard temp2 = new TopCard(playerChoice.toLowerCase(), temp.getType(), Integer.parseInt(temp.getValue()));
				
				try {
					gamePanel.myDeck.remove(temp);
					gamePanel.updateDeck();
					client.sendToServer(temp2);
					//client.isCurrentPlayer(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			else if((temp.getType().equals("skip")||temp.getType().equals("reverse")||temp.getType().equals("draw2")) && client.isCurrent && gamePanel.topCard.getType().equals(temp.getType())) {
				
				TopCard temp2 = new TopCard(temp.getColor(),temp.getType(),Integer.parseInt(temp.getValue()));
				try {
					gamePanel.myDeck.remove(temp);
					gamePanel.updateDeck();
					client.sendToServer(temp2);
					//client.isCurrentPlayer(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			else if(!client.isCurrent) {
				System.out.println("Please wait your turn");
				error.setText("Please wait your turn");
				error.setForeground(Color.RED);
				gamePanel.board.add(error);
				gamePanel.revalidate();
				gamePanel.repaint();
			}

			else {
				error.setText("PLEASE PLAY A VALID CARD");
				error.setForeground(Color.RED);
				gamePanel.board.add(error);
				gamePanel.revalidate();
				gamePanel.repaint();
			}			
		}
		if(command == "Draw Card") {
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			
			if(!client.isCurrent) {
				error.setText("Cannot Draw Card, Please Wait Your Turn");
				error.setForeground(Color.RED);
				gamePanel.board.add(error);
				gamePanel.revalidate();
				gamePanel.repaint();			
			}
			
			else {
				try {
					client.sendToServer("REQUEST A CARD");
					//client.isCurrentPlayer(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		if(command == "UNO") {

		}
	}

	public void setDeck(ArrayList<Card> myDeck) {
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.myDeck.addAll(myDeck);
		gamePanel.updateDeck();
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	public void addCardToDeck(Card oneCard) {
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.myDeck.add(oneCard);
		gamePanel.updateDeck();
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	public void setTopCard(TopCard topCard) {
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.setTopCard(topCard);
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	public void updateLabel(JLabel message) {
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.addToUpdatesPanel(message);
		gamePanel.revalidate();
		gamePanel.repaint();
	}
}
