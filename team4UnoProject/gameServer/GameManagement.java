package gameServer;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.Math;

import ocsf.server.ConnectionToClient;
import playerGUI.*;

public class GameManagement {

	private GameServer server;
	private TopCard topCard;
	private GameCards allCards;

	ArrayList<Long> players;

	private ConnectionToClient currentPlayerConnection;
	private int count=0;
	private int currentPlayerNumber;
	Boolean clockwise = true;


	public GameManagement(GameServer server) 
	{
		this.server=server;
		allCards = new GameCards();
		allCards.initializeCards();
		Card temp = allCards.firstCard();
		topCard = new TopCard(temp.getColor(),temp.getType(),Integer.parseInt(temp.getValue()));
		players = new ArrayList<>();
	}

	public void playersConnected(Long id)
	{
		players.add(id);
		if(players.size() == 3) 
		{
			start();
			server.stopListening();
		}
	}

	public void removePlayer(Long id)
	{
		players.remove(id);

		if(players.size() < 3) 
		{
			try {
				server.listen();
			} catch (IOException e) {
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

	public void reverse() 
	{
		clockwise ^= true;
	}

	public ConnectionToClient currentPlayer() {
		Thread[] arrayOfClients = server.getClientConnections();
		currentPlayerConnection  = (ConnectionToClient) arrayOfClients[currentPlayerNumber];

		try {
			currentPlayerConnection.sendToClient(true);
			currentPlayerConnection.sendToClient("Your Turn");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return currentPlayerConnection;
	}

	public void nextPlayer() {
		if(clockwise) 
		{
			count++;
			currentPlayerNumber = count % players.size();
		}
		else {
			count--;
			currentPlayerNumber = Math.abs(count) % players.size();
		}
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

		try {
			client.sendToClient("Your Turn");
			client.sendToClient(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
