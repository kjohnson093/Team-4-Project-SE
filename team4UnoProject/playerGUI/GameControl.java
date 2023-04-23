package playerGUI;
//Controller of the game and contains the ActionListener 
//that listens to button clicks

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

	// Constructor
	public GameControl(JPanel container, PlayerClient client)
	{
		this.container=container;
		this.client= client;
	//System.out.println(this.client.getInetAddress());
	}

	// Override actionPerformed method
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		// If "Use Card" button is clicked
		if(command == "Use Card")
		{

			// Get the gamePanel
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			gamePanel.deckGroup.getElements();

			// Get all the card buttons
			Enumeration<AbstractButton> allButtons = gamePanel.deckGroup.getElements();

			// Set a temporary Card
			Card temp=new Card();

			// Loop through all the card buttons
			while(allButtons.hasMoreElements())
			{
				temp=(Card)allButtons.nextElement();
				if(temp.isSelected()) 
				{
					break;
				}
			}

			// Check if the selected card can be played
			if((gamePanel.topCard.getColor().equals(temp.getColor())&&!temp.getColor().equals("all") || gamePanel.topCard.getValue().equals(temp.getValue())&&!temp.getValue().equals("-1") && !temp.getValue().equals("-2")) && client.isCurrent)            {
				// Remove the played card from the player's deck
				gamePanel.myDeck.remove(temp);
				gamePanel.updateDeck();

				// Send the played card to the server
				 TopCard temp2 = new TopCard(temp.getColor(),temp.getType(),Integer.parseInt(temp.getValue()));
				try 
				{
					client.sendToServer(temp2);
					//client.isCurrentPlayer(false);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}

			// If the selected card is a Wild or a Draw 4 Wild card
			else if((temp.getColor().equals("all")||temp.getType().equals("wild"))&& client.isCurrent) {
				// Display a dialog to select a color
				Object[] options = {"Blue", "Yellow", "Green", "Red"};
				int n = JOptionPane.showOptionDialog(null,"Please choose a color", "",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				String playerChoice = (String) options[n];
				
				// Remove the played card from the player's deck and send the played card to the server
				TopCard temp2 = new TopCard(playerChoice.toLowerCase(), temp.getType(), Integer.parseInt(temp.getValue()));
				try 
				{
					gamePanel.myDeck.remove(temp);
					gamePanel.updateDeck();
					client.sendToServer(temp2);
					//client.isCurrentPlayer(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			// If the selected card is a Skip, Reverse, or Draw 2 card of the same type as the top card
			else if((temp.getType().equals("skip")||temp.getType().equals("reverse")||temp.getType().equals("draw2")) && client.isCurrent && gamePanel.topCard.getType().equals(temp.getType()))
			{
				// Remove the played card from the player's deck and send the played card to the server
				TopCard temp2 = new TopCard(temp.getColor(),temp.getType(),Integer.parseInt(temp.getValue()));
				try 
				{
					gamePanel.myDeck.remove(temp);
					gamePanel.updateDeck();
					client.sendToServer(temp2);
					//client.isCurrentPlayer(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			//Executed when the user  who is not current player
			//clicks on "Play Card" button.
			else if(!client.isCurrent)
			{
				System.out.println("Please wait your turn");
				error.setText("Please wait your turn");
				error.setForeground(Color.RED);
				gamePanel.board.add(error);
				gamePanel.revalidate();
				gamePanel.repaint();
			}
			
			//Executed when the user plays invalid card.
			else
			{
				error.setText("PLEASE PLAY A VALID CARD");
				error.setForeground(Color.RED);
				gamePanel.board.add(error);
				gamePanel.revalidate();
				gamePanel.repaint();
			}			
		}
		//Draws card  
		if(command == "Draw Card") {
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			//Stops non-current player from drawing
			if(!client.isCurrent) {
				error.setText("Cannot Draw Card, Please Wait Your Turn");
				error.setForeground(Color.RED);
				gamePanel.board.add(error);
				gamePanel.revalidate();
				gamePanel.repaint();			
			}
			
			// Send a message to the server to request a card
			else {//if(gamePanel.myDeck.size() <= 12){
				try {
					client.sendToServer("REQUEST A CARD");
					//client.isCurrentPlayer(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		if(command == "UNO") {

		}
	}

	//Sets the player's deck of cards
	public void setDeck(ArrayList<Card> myDeck)
	{
		// Get the game panel.
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.myDeck.addAll(myDeck);
		gamePanel.updateDeck();
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	// Add the cards to the user's deck on the game panel and refresh the display.
	public void addCardToDeck(Card oneCard) {
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		gamePanel.myDeck.add(oneCard);
		gamePanel.updateDeck();
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	//// Sets the top card of the discard pile on the game panel.
	public void setTopCard(TopCard topCard) 
	{
		// Get the game panel.
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		// Set the top card on the game panel and repaint the panel.
		gamePanel.setTopCard(topCard);
		gamePanel.revalidate();
		gamePanel.repaint();
	}
}