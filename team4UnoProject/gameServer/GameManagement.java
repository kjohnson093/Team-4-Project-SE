package gameServer;


import java.util.ArrayList;
import java.util.*;

import javax.swing.JPanel;

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
	private long currentPlayer;
	private ArrayList<Long> players;
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
	    	
	}
	
	public void playersConnected(Long id)
	{
		players.add(id);
		playerTurnOrder.add(id);
		if(players.size() >= 2) 
		{
			try 
			{
				Thread.sleep(40000);
				start();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		System.out.println("return 7");
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
	
	public void Draw2()
	{
		for (int i = 0; i < 2; i++)
		{
			panel.addCard();
		}

	}
	
	public void Draw4()
	{
		for (int i = 0; i < 2; i++)
		{
			panel.addCard();
		}
		
	}
	
	public void isPlayerTurn(long id)
	{
		currentPlayer = playerTurnOrder.poll();
		if(id == currentPlayer)
		{
			client.isCurrentPlayer(true);
		}
		else
		{
			client.isCurrentPlayer(false);
		}
	}
	
	public void start() 
	{
		
	}
}
