package playerGUI;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class GamePanel extends JPanel
{
	JPanel panel = new JPanel();
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	JPanel board = new JPanel();
	JPanel panel_4 = new JPanel();
	JPanel deck = new JPanel();
	
	JPanel scrollPanel = new JPanel();
	
	JButton useCard = new JButton("Use Card");
	JButton addCard = new JButton("Draw Card");
	JButton uno = new JButton("UNO!");
	
	JButton logout = new JButton("Log Out");
	JButton quit = new JButton("Quit");
	
	String username = "";
	TopCard topCard = new TopCard();

	ArrayList<Card> myDeck = new ArrayList<Card>();
	ButtonGroup deckGroup = new ButtonGroup();
	GameCards allcards = new GameCards();
	JPanel updatesPanel = new JPanel();
	JPanel updatesPanel2 = new JPanel();
	JLabel updatesLabel = new JLabel("");
	JLabel updatesLabel2 = new JLabel("");
	
	JLabel YouWon = new JLabel("You won this game of UNO, CONGRATULATIONS!!");

	public GamePanel (GameControl gc) {

		this.setSize(1500, 900);

		setLayout(new BorderLayout(0, 0));

		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(panel_1, BorderLayout.NORTH);
		panel.add(panel_2, BorderLayout.WEST);
		board.setBackground(Color.WHITE);
		panel.add(board, BorderLayout.CENTER);
		board.add(updatesPanel);
		updatesPanel.add(updatesLabel);
		panel.add(panel_4,BorderLayout.EAST);	
		panel_1.add(logout);
		panel_1.add(quit);
		
		JScrollPane scrollDeck = new JScrollPane(deck);
		panel.add(scrollDeck,BorderLayout.SOUTH);
		
		useCard.addActionListener(gc);	
		addCard.addActionListener(gc);
		uno.addActionListener(gc);
		logout.addActionListener(gc);
		quit.addActionListener(gc);
	}

	public void updateDeck() 
	{
		deck.removeAll();
		deck.repaint();
		deck.revalidate();
		deckGroup.clearSelection();

		for(int i = 0; i < myDeck.size(); i++) 
		{
			deckGroup.remove(myDeck.get(i));
			if(!myDeck.get(i).getValue().equals("-1") && !myDeck.get(i).getValue().equals("-2")) {
				myDeck.get(i).setIcon(new ImageIcon("images/"+myDeck.get(i).toStringNormal()+".png"));
			}
			else if(myDeck.get(i).getValue().equals("-1")) {
				myDeck.get(i).setIcon(new ImageIcon("images/"+myDeck.get(i).toStringSpecial()+".png"));
			}
			else if(myDeck.get(i).getValue().equals("-2")) {
				myDeck.get(i).setIcon(new ImageIcon("images/"+myDeck.get(i).toStringVerySpecial()+".png"));
			}

			deck.add(myDeck.get(i));
			deckGroup.add(myDeck.get(i));
		}
		
		deck.add(useCard);
		deck.add(addCard);
		
		if(myDeck.size() == 1) {
			deck.add(uno);
		}
		else {
			deck.remove(uno);
		}
		
		if(myDeck.size() == 0){
			deck.removeAll();
			deck.add(YouWon);
		}
		else {
			deck.remove(YouWon);
		}
		
		deck.repaint();
		deck.revalidate();
	}

	public void addCard() 
	{
		Card card = allcards.oneCard();
		deckGroup.clearSelection();
		deckGroup.add(card);

		if(!card.getValue().equals("-1") && !card.getValue().equals("-2")) {
			card.setIcon(new ImageIcon("images/"+card.toStringNormal()+".png"));
			myDeck.add(card);
		}
		else if(card.getValue().equals("-1")) {
			card.setIcon(new ImageIcon("images/"+card.toStringSpecial()+".png"));
			myDeck.add(card);
		}
		else if(card.getValue().equals("-2")) {
			card.setIcon(new ImageIcon("images/"+card.toStringVerySpecial()+".png"));
			myDeck.add(card);
		}
		updateDeck();
		return;
	}

	public void removeCard(Card card) {
		for (int i = 0; i < myDeck.size(); i++) 
		{
			if(card.toString().equals(myDeck.get(i).toString())) {
				myDeck.remove(i);
				updateDeck();
				i=myDeck.size();
				deckGroup.remove(card);
				deckGroup.clearSelection();
				updateDeck();
				break;
			}
		}
	}

	public void displayTopCard() {
		JLabel top = new JLabel();
		this.validate();

		if(!topCard.getValue().equals("-1") && !topCard.getValue().equals("-2")) {

			top.setIcon(new ImageIcon(new ImageIcon("images/"+topCard.toStringNormal()+".png").getImage().getScaledInstance(250,370, Image.SCALE_DEFAULT)));
			board.removeAll();
			board.add(top);	
		}
		else if(topCard.getValue().equals("-1")) {

			top.setIcon(new ImageIcon(new ImageIcon("images/"+topCard.toStringSpecial()+".png").getImage().getScaledInstance(250,370, Image.SCALE_DEFAULT)));
			board.removeAll();
			board.add(top);	
		}
		else if(topCard.getValue().equals("-2")) {

			top.setIcon(new ImageIcon(new ImageIcon("images/"+topCard.toStringVerySpecial()+".png").getImage().getScaledInstance(250,370, Image.SCALE_DEFAULT)));			
			board.removeAll();
			board.add(top);	
		}
	}

	public void setTopCard(TopCard top) {
		this.topCard = top;
		displayTopCard();
	}

	public void getSevenCards(ArrayList<Card> firstSeven) {
		myDeck = firstSeven;
	}
	
	public ArrayList<Card> getHand()
	{
		return myDeck;
	}
	
	public void addToUpdatesPanel(JLabel message) {
		board.add(updatesPanel);
		updatesPanel.removeAll();
		updatesPanel.add(message);
	}
	public void addToUpdatesPanel2(JLabel message) {
		board.add(updatesPanel2);
		updatesPanel2.removeAll();
		updatesPanel2.add(message);
	}
}
