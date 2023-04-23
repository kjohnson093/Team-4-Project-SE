// Import necessary packages
package gameServer;
import java.util.ArrayList;
import java.util.*;
import javax.swing.JPanel;
import playerClient.PlayerClient;
import playerGUI.*;

public class GameManagement {
    //// Initialize variables
    private GamePanel panel;
    private GameControl gc;
    private GameControl nextPlayerGc;
    private GameControl previousPlayerGc;
    private GameServer server;
    private TopCard topCard;
    private GameCards allCards;
    private long currentPlayer;
    private ArrayList<Long> players;
    private JPanel container;
    private PlayerClient client;
    private Queue<Long> playerTurnOrder;

    // Constructor for initializing GameManagement object
    public GameManagement(GameServer server) 
    {
        this.server=server;
        allCards = new GameCards();
        allCards.initializeCards();
        container = new JPanel();
        client = new PlayerClient();
        gc = new GameControl(container, client);
        panel = new GamePanel(gc);
        Card temp = allCards.firstCard();
        topCard = new TopCard(temp.getColor(),temp.getType(),Integer.parseInt(temp.getValue()));
        players = new ArrayList<>();
        playerTurnOrder = new LinkedList<>();
    }
	
    // Method to add connected player's ID to the list of players and their turn order to the queue
    public void playersConnected(Long id)
    {
        players.add(id);
        playerTurnOrder.add(id);
        if(players.size() == 3) 
        {
            try 
            {
                //Thread.sleep(40000); // wait 40 seconds before starting game
                start();
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace(); // print stack trace if interrupted exception is caught
            }
        }
    }
    
    // Method to get the total number of cards remaining in the deck
    public int getTotalNoCards()
    {
        return allCards.totalCardsLeft();
    }
    
    // Method to set the top card on the pile
    public void setTopCard(TopCard top)
    {
        this.topCard = top;
    }
    
    // Method to get the current top card on the pile
    public TopCard getTopCard() 
    {
        return topCard;
    }
    
    // Method to deal 7 cards to a player
    public ArrayList<Card> deal7()
    {
        System.out.println("return 7"); // print message to console
        return allCards.deal7();
    }
    
    // Method to add a card to a player's hand
    public Card addCard()
    {
        return allCards.oneCard();
    }
    
    // Method to check if there is a winner
    public boolean isWinner()
    {
        if (getTotalNoCards() == 0)
        {
            return true;
        }
        return false;
    }
    
    // Method to check if a player has one card left (has "uno")
    public boolean hasUno()
    {
        if (getTotalNoCards() == 1)
        {
            return true;
        }
        return false;
    }
    
    // Method to draw 2 cards and update the current player
    public ArrayList<Card> Draw2()
    {
        ArrayList<Card> newHand = new ArrayList<>();
        newHand.addAll(panel.getHand()); // get current player's hand
        newHand.addAll(allCards.draw2()); // add 2 drawn cards to the hand
        playerTurnOrder.add(currentPlayer); // put current player at the end of the queue
        currentPlayer = playerTurnOrder.poll(); // set the updated current player as the active player
        client.isCurrentPlayer(true); // set updated current player as the active player
        return newHand; //Return updated hand
    }
    
     // Draw 4 cards from the deck
 	public ArrayList<Card> Draw4()
 	{
 	
 		playerTurnOrder.add(currentPlayer); // put current player at the end of the queue
 		currentPlayer = playerTurnOrder.poll(); // set the updated current player as the active player
        client.isCurrentPlayer(true); // set updated current player as the active player
        //Draw there four cards
 		ArrayList<Card> newHand = new ArrayList<>();
 		newHand.addAll(panel.getHand());
 		newHand.addAll(allCards.draw4());
        
        return newHand; //return updated hand

 	}
 	
 	//Reverse the flow of the game
 	public void reverse()
 	{
 		//Create a new stack object
 		Stack<Long> stack = new Stack<>();
 		//While the queue has items, push items on to stack
 		while (!playerTurnOrder.isEmpty())
 		{
 			stack.push(playerTurnOrder.poll());
 		}
 		//While stack has items, offer the items back on the queue
 		//LIFO FIFO paradigm reverses order
 		while(!stack.isEmpty())
 		{
 			playerTurnOrder.offer(stack.pop());
 		}
 		
 	}
 	//Returns current player so it can behave a sentinel value
 	//That controls whether or not a player can control action listeners
 	//Must be verified in teh server clas
 	public long getCurrentPlayer()
 	{
 		return currentPlayer;
 	}
 	
 	public void skip()
 	{
 		//Remove the next player
 		long nextPlayer = playerTurnOrder.poll();
 		//Place them at the end of the list
 		playerTurnOrder.offer(nextPlayer);
 		//allow the following player to begin
 		currentPlayer = playerTurnOrder.poll();
 		//Change the currentPlayer
 		client.isCurrentPlayer(true);
 	}
 	public void start()
 	{
 		
 	}
}
    
