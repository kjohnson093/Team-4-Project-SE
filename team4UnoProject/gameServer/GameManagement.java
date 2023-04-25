package gameServer;

import java.util.ArrayList;
import java.io.IOException;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ocsf.server.ConnectionToClient;
import playerClient.PlayerClient;
import playerGUI.*;

public class GameManagement {
	private GamePanel panel;
	private GameControl gc;
	private GameControl nextPlayerGc;
	private GameControl previousPlayerGc;
	private GameServer server;
	private TopCard topCard;
	private GameCards allCards;
	private Long currentPlayer;
	ArrayList<Long> players;
	private JPanel container;
	private JPanel nextPlayerContainer;
	private JPanel previousPlayerContainer;
	private PlayerClient nextPlayerClient;
	private PlayerClient previousPlayerClient;
	private PlayerClient client;
	private GamePanel nextPlayerPanel;
	private GamePanel previousPlayerPanel;
	private int numCardsToDraw;
	private Queue<Long> playerTurnOrder;
	private Random random;
	private int randIdx;
	private Thread[] arrayOfClients;
	
	private ConnectionToClient currentPlayerConnection;
	private int count=0;
	private int currentPlayerNumber;

	
	public GameManagement(GameServer server) 
	{
		this.server=server;
		allCards = new GameCards();
		allCards.initializeCards();
		container = new JPanel();
		client = new PlayerClient();
		nextPlayerContainer = new JPanel();
		nextPlayerClient = new PlayerClient();
		previousPlayerContainer = new JPanel();
		previousPlayerClient = new PlayerClient();
		gc = new GameControl(container, client);
		nextPlayerGc = new GameControl(nextPlayerContainer, nextPlayerClient);
		previousPlayerGc = new GameControl(previousPlayerContainer, previousPlayerClient);
		panel = new GamePanel(gc);
		Card temp = allCards.firstCard();
		topCard = new TopCard(temp.getColor(),temp.getType(),Integer.parseInt(temp.getValue()));
		players = new ArrayList<>();
		nextPlayerPanel = new GamePanel(nextPlayerGc);
	    previousPlayerPanel = new GamePanel(previousPlayerGc);
	    playerTurnOrder = new LinkedList<>();
	    random = new Random();
	    arrayOfClients = server.getClientConnections();
	    	
	}
	
	public void playersConnected(Long id)
	{
		players.add(id);
		playerTurnOrder.add(id);
		System.out.println(players.size());
		if(players.size() == 3) 
		{
//			try 
//			{
//				//Thread.sleep(40000);
//				start();
//			} catch (InterruptedException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			start();
			server.stopListening();
		}
	}
	
	public void removePlayer(Long id)
	{
		players.remove(id);
		playerTurnOrder.remove(id);
	}
	
	
	
	
	public int getTotalNoCards()
	{
		return allCards.totalCardsLeft();
	}
	
	public void setTopCard(TopCard top)
	{
		this.topCard = top;
	}
	public TopCard getTopCard() 
	{
		return topCard;
	}
	
	public ArrayList<Card> deal7()
	{
		return allCards.deal7();
	}
	
	public Card addCard()
	{
		return allCards.oneCard();
	}
	
	public boolean isWinner()
	{
		if (getTotalNoCards() == 0)
		{
			return true;
		}
		return false;
	}
	
	public boolean hasUno()
	{
		if (getTotalNoCards() == 1)
		{
			return true;
		}
		return false;
	}
	
	public ArrayList<Card> Draw2()
	{
		return allCards.deal2();
	}
	
	public ArrayList<Card> Draw4()
	{
		return allCards.deal4();
	}
	
	public void skip()
	{
		
	}
	
	public void reverse() 
	{
		
		// Reverse the array
		for (int i = 0; i < arrayOfClients.length / 2; i++) 
		{
		    Thread temp = arrayOfClients[i];
		    arrayOfClients[i] = arrayOfClients[arrayOfClients.length - 1 - i];
		    arrayOfClients[arrayOfClients.length - 1 - i] = temp;
		}
		
	}
	
	public ConnectionToClient currentPlayer()
	{
		
		currentPlayerConnection  = (ConnectionToClient) arrayOfClients[currentPlayerNumber];
		
		try {
			currentPlayerConnection.sendToClient(true);
			currentPlayerConnection.sendToClient(new JLabel("Your Turn"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return currentPlayerConnection;
	}
	
	public void nextPlayer()
	{
		count++;
		currentPlayerNumber = count % players.size();
	}
	
	public ConnectionToClient getCurrentPlayer() {
		Thread[] arrayOfClients = server.getClientConnections();
		currentPlayerConnection  = (ConnectionToClient) arrayOfClients[currentPlayerNumber];
		
		return currentPlayerConnection;
	}
	
	public void start() 
	{
		Thread[] arrayOfClients = server.getClientConnections();
		ConnectionToClient client = (ConnectionToClient) arrayOfClients[0];
		
		try 
		{
			client.sendToClient(new JLabel("Your Turn"));
			client.sendToClient(true);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
