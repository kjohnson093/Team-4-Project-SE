package Game;

import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.JPanel;

public class GameControl implements ActionListener{

	private JPanel container;
	JLabel error = new JLabel("PLEASE PLAY A VALID CARD");
	GameClient client;

	public GameControl(JPanel container, GameClient client) {
		this.container=container;
		this.client= client;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if(command == "Use Card"){

			GamePanel gamePanel = (GamePanel)container.getComponent(0);
			gamePanel.deckGroup.getElements();

			Enumeration<AbstractButton> allButtons = gamePanel.deckGroup.getElements();
			Card temp=new Card();
			while(allButtons.hasMoreElements()) {
				temp=(Card)allButtons.nextElement();
				if(temp.isSelected()) {
					break;
				}
			}

			if((gamePanel.topCard.getColor().equals(temp.getColor()) || gamePanel.topCard.getValue().equals(temp.getValue()))&&client.isCurrent){
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
			else if(!client.isCurrent) {
				System.out.println("Please wait your turn");
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
			GamePanel gamePanel = (GamePanel)container.getComponent(0);
			
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
		GamePanel gamePanel = (GamePanel)container.getComponent(0);
		gamePanel.myDeck=myDeck;
		gamePanel.updateDeck();
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	public void addCardToDeck(Card oneCard) {
		GamePanel gamePanel = (GamePanel)container.getComponent(0);
		gamePanel.myDeck.add(oneCard);
		gamePanel.updateDeck();
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	public void setTopCard(TopCard topCard) {
		GamePanel gamePanel = (GamePanel)container.getComponent(0);
		gamePanel.setTopCard(topCard);
		gamePanel.revalidate();
		gamePanel.repaint();
	}
}
