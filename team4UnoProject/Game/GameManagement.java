package Game;

import java.util.ArrayList;

public class GameManagement {
	GameServer server;
	TopCard topCard = new TopCard();
	GameCards allCards = new GameCards();
	int currentPlayer;
	ArrayList<Long> players;
	int numberOfPlayers=0;
	
	public GameManagement(GameServer server) {
		this.server=server;
		allCards.initializeCards();
		Card temp = allCards.firstCard();
		topCard = new TopCard(temp.getColor(),temp.getType(),Integer.parseInt(temp.getValue()));
	}
	
	public void playersConnected(Long id) {
		numberOfPlayers++;
		players.add(id);
		if(players.size() >= 2) {
			try {
				Thread.sleep(40000);
				start();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int getTotalNoCards() {
		return allCards.totalCardsLeft();
	}
	
	public void setTopCard(TopCard top) {
		topCard = top;//new TopCard(top.getColor(),top.getType(),Integer.parseInt(top.getValue()));
	}
	public TopCard getTopCard() {
		return topCard;
	}
	
	public ArrayList<Card> deal7(){
		return allCards.deal7();
	}
	
	public Card addCard() {
		return allCards.oneCard();
	}
	
	public void start() {
		
	}
	
	
	

}
