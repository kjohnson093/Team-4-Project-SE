//This line imports the necessary classes for this file
package playerGUI;
//These lines import the necessary classes for this file
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

//This line defines the GamePanel class which extends the JPanel class
public class GamePanel extends JPanel
{
	// Creates several JPanel objects to be used later
	JPanel panel = new JPanel();
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	JPanel board = new JPanel();
	JPanel panel_4 = new JPanel();
	JPanel deck = new JPanel();
	//Creates several JButton objects to be used later
	JButton useCard = new JButton("Use Card");
	JButton addCard = new JButton("Draw Card");
	JButton uno = new JButton("UNO!");
	//String object to store username
	String username = "";
	//TopCard object
	TopCard topCard = new TopCard();
	//ArrayList object to store cards in the player's hand
	ArrayList<Card> myDeck = new ArrayList<Card>();
	//ButtonGroup object to group the player's cards together
	ButtonGroup deckGroup = new ButtonGroup();
	//GameCards object to represent all the cards in the game
	GameCards allcards = new GameCards();
	// JPanel and JLabel objects to display updates to the game
	JPanel updatesPanel = new JPanel();
	JLabel updatesLabel = new JLabel("");
	// Constructor for the GamePanel class, 
	//which takes a GameControl object as an argument
	public GamePanel (GameControl gc) {

		//Sets the size of the GamePanel
		this.setSize(1500, 900);
		
		//Sets the layout of the GamePanel to border layout
		setLayout(new BorderLayout(0, 0));
		// Add the JPanels to the GamePanel and set their layouts
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(panel_1, BorderLayout.NORTH);
		panel.add(panel_2, BorderLayout.WEST);
		board.setBackground(Color.WHITE);
		panel.add(board, BorderLayout.CENTER);
		board.add(updatesPanel);
		updatesPanel.add(updatesLabel);
		panel.add(panel_4,BorderLayout.EAST);
		JScrollPane scrollDeck = new JScrollPane(deck);
		panel.add(scrollDeck,BorderLayout.SOUTH);
		// These lines add action listeners to the useCard and addCard JButtons
		useCard.addActionListener(gc);	
		addCard.addActionListener(gc);
	}
	// This method updates the deck of cards in the GUI
	public void updateDeck() 
	{
		// These lines remove all the cards from the deck and repaint and revalidate it
		deck.removeAll();
		deck.repaint();
		deck.revalidate();
		//Goes through all the cards in the player's hand and adds them to the deck
		for(int i = 0; i < myDeck.size(); i++) 
		{
			deckGroup.remove(myDeck.get(i));
			//Sets the card's icon based on its value
			if(!myDeck.get(i).getValue().equals("-1") && !myDeck.get(i).getValue().equals("-2")) {
				myDeck.get(i).setIcon(new ImageIcon("images/"+myDeck.get(i).toStringNormal()+".png"));
			}
			else if(myDeck.get(i).getValue().equals("-1"))
			{
				myDeck.get(i).setIcon(new ImageIcon("images/"+myDeck.get(i).toStringSpecial()+".png"));
			}
			//If the card value is -2, set its icon to the corresponding Very special card image
			else if(myDeck.get(i).getValue().equals("-2"))
			{
				myDeck.get(i).setIcon(new ImageIcon("images/"+myDeck.get(i).toStringVerySpecial()+".png"));
			}
			
			deck.add(myDeck.get(i));
			deckGroup.add(myDeck.get(i));
		}
		
		deck.add(useCard);
		deck.add(addCard);
		deck.repaint();
		deck.revalidate();
	}

	/// Update the deck with the new card added
	public void addCard() 
	{
		Card card = allcards.oneCard();
		deckGroup.add(card);

		if(!card.getValue().equals("-1") && !card.getValue().equals("-2")) {
			card.setIcon(new ImageIcon("images/"+card.toStringNormal()+".png"));
			myDeck.add(card);
		}
		else if(card.getValue().equals("-1")) {
			card.setIcon(new ImageIcon("images/"+card.toStringSpecial()+".png"));
			System.out.println(card.toStringSpecial());
			myDeck.add(card);
		}
		else if(card.getValue().equals("-2")) {
			card.setIcon(new ImageIcon("images/"+card.toStringVerySpecial()+".png"));
			System.out.println(card.toStringVerySpecial());
			myDeck.add(card);
		}
		updateDeck();
		return;
	}

	// remove a card from the player's deck
	public void removeCard(Card card) 
	{
		//Iterate through the player's deck and 
		//remove the card that matches the given card
		for (int i = 0; i < myDeck.size(); i++) 
		{
			if(card.toString().equals(myDeck.get(i).toString()))
			{
				myDeck.remove(i);
				updateDeck();
				i=myDeck.size();
				deckGroup.remove(card);
				updateDeck();
				break;
			}
		}
	}
	//Display the top card of the discard pile
	public void displayTopCard() {
		JLabel top = new JLabel();
		this.validate();

		// If the top card's value is not -1 or -2, set its icon to the corresponding number card image
		if(!topCard.getValue().equals("-1") && !topCard.getValue().equals("-2")) {

			top.setIcon(new ImageIcon(new ImageIcon("images/"+topCard.toStringNormal()+".png").getImage().getScaledInstance(250,370, Image.SCALE_DEFAULT)));
			board.removeAll();
			board.add(top);	
		}
		// If the top card's value is -1, 
		//set its icon to the corresponding special image
		else if(topCard.getValue().equals("-1")) 
		{

			top.setIcon(new ImageIcon(new ImageIcon("images/"+topCard.toStringSpecial()+".png").getImage().getScaledInstance(250,370, Image.SCALE_DEFAULT)));
			board.removeAll();
			board.add(top);	
		}
		// If the top card's value is -2, set its icon to the
		//corresponding very special image
		else if(topCard.getValue().equals("-2")) 
		{

			top.setIcon(new ImageIcon(new ImageIcon("images/"+topCard.toStringVerySpecial()+".png").getImage().getScaledInstance(250,370, Image.SCALE_DEFAULT)));			
			board.removeAll();
			board.add(top);	
		}
	}

	////Display top card
	public void setTopCard(TopCard top) {
		this.topCard = top;
		displayTopCard();
	}

	//Get seven cards from the deck at the beginning of the game
	public void getSevenCards(ArrayList<Card> firstSeven)
	{
		myDeck = firstSeven;
	}
	//Get the player's current hand
	public ArrayList<Card> getHand()
	{
		return myDeck;
	}
	
}


