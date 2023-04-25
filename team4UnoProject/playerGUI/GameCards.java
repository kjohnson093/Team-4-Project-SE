package playerGUI;

import java.util.ArrayList;
import java.util.Random;

public class GameCards {
	
	private ArrayList<Card> cards;	
	public GameCards() {
		cards = new ArrayList<Card>();
	}
	public void addCard(Card c) {
		cards.add(c);
	}
	
	public String toString() {
		
		String toReturn = "";
		for (Card c : cards) {
			toReturn += c.toString()+"\n";
		}	
		return toReturn;
	}
	
	public ArrayList<Card> deal7() {
		ArrayList<Card> dealt_cards = new ArrayList<Card>();
		for(int i = 0; i < 7; i++) {
			dealt_cards.add(oneCard());
		}
		return dealt_cards;
	}
	
	public ArrayList<Card> deal2() {
		ArrayList<Card> dealt_cards = new ArrayList<Card>();
		for(int i = 0; i < 2; i++) {
			dealt_cards.add(oneCard());
		}
		return dealt_cards;
	}
	
	public ArrayList<Card> deal4() {
		ArrayList<Card> dealt_cards = new ArrayList<Card>();
		for(int i = 0; i < 4; i++) {
			dealt_cards.add(oneCard());
		}
		return dealt_cards;
	}
	
	public Card oneCard() {
		Random rand = new Random();
		int i = rand.nextInt(cards.size());
		Card returnCard = new Card();
		returnCard = cards.get(i);
		cards.remove(i);
		return returnCard;
	}
	
	public Card firstCard() {
		Random rand = new Random();
		for(int i = 0; i < cards.size();i++) {
			
			int j = rand.nextInt(cards.size());
			Card returnCard = new Card();
			returnCard = cards.get(j);	
			
			if(!cards.get(j).getValue().equals("-1") && !cards.get(j).getValue().equals("-2") && !cards.get(i).getColor().equals("all")) {
				System.out.println(cards.get(j));
				cards.remove(j);
				i=cards.size();
				return returnCard;
				
			}
		}
		
		return cards.get(cards.size()-50);
	}
	public int totalCardsLeft() {
		return cards.size();
	}
	
	public void initializeCards() {
		cards.add(new Card("red","normal",0));
		cards.add(new Card("red","normal",1));
		cards.add(new Card("red","normal",1));
		cards.add(new Card("red","normal",2));
		cards.add(new Card("red","normal",2));
		cards.add(new Card("red","normal",3));
		cards.add(new Card("red","normal",3));
		cards.add(new Card("red","normal",4));
		cards.add(new Card("red","normal",4));
		cards.add(new Card("red","normal",5));
		cards.add(new Card("red","normal",5));
		cards.add(new Card("red","normal",6));
		cards.add(new Card("red","normal",6));
		cards.add(new Card("red","normal",7));
		cards.add(new Card("red","normal",7));
		cards.add(new Card("red","normal",8));
		cards.add(new Card("red","normal",8));
		cards.add(new Card("red","normal",9));
		cards.add(new Card("red","normal",9));
		
		cards.add(new Card("blue","normal",0));
		cards.add(new Card("blue","normal",1));
		cards.add(new Card("blue","normal",1));
		cards.add(new Card("blue","normal",2));
		cards.add(new Card("blue","normal",2));
		cards.add(new Card("blue","normal",3));
		cards.add(new Card("blue","normal",3));
		cards.add(new Card("blue","normal",4));
		cards.add(new Card("blue","normal",4));
		cards.add(new Card("blue","normal",5));
		cards.add(new Card("blue","normal",5));
		cards.add(new Card("blue","normal",6));
		cards.add(new Card("blue","normal",6));
		cards.add(new Card("blue","normal",7));
		cards.add(new Card("blue","normal",7));
		cards.add(new Card("blue","normal",8));
		cards.add(new Card("blue","normal",8));
		cards.add(new Card("blue","normal",9));
		cards.add(new Card("blue","normal",9));
		
		cards.add(new Card("green","normal",0));
		cards.add(new Card("green","normal",1));
		cards.add(new Card("green","normal",1));
		cards.add(new Card("green","normal",2));
		cards.add(new Card("green","normal",2));
		cards.add(new Card("green","normal",3));
		cards.add(new Card("green","normal",3));
		cards.add(new Card("green","normal",4));
		cards.add(new Card("green","normal",4));
		cards.add(new Card("green","normal",5));
		cards.add(new Card("green","normal",5));
		cards.add(new Card("green","normal",6));
		cards.add(new Card("green","normal",6));
		cards.add(new Card("green","normal",7));
		cards.add(new Card("green","normal",7));
		cards.add(new Card("green","normal",8));
		cards.add(new Card("green","normal",8));
		cards.add(new Card("green","normal",9));
		cards.add(new Card("green","normal",9));
		
		cards.add(new Card("yellow","normal",0));
		cards.add(new Card("yellow","normal",1));
		cards.add(new Card("yellow","normal",1));
		cards.add(new Card("yellow","normal",2));
		cards.add(new Card("yellow","normal",2));
		cards.add(new Card("yellow","normal",3));
		cards.add(new Card("yellow","normal",3));
		cards.add(new Card("yellow","normal",4));
		cards.add(new Card("yellow","normal",4));
		cards.add(new Card("yellow","normal",5));
		cards.add(new Card("yellow","normal",5));
		cards.add(new Card("yellow","normal",6));
		cards.add(new Card("yellow","normal",6));
		cards.add(new Card("yellow","normal",7));
		cards.add(new Card("yellow","normal",7));
		cards.add(new Card("yellow","normal",8));
		cards.add(new Card("yellow","normal",8));
		cards.add(new Card("yellow","normal",9));
		cards.add(new Card("yellow","normal",9));
		
		cards.add(new Card("red","draw2",-1));
		cards.add(new Card("red","draw2",-1));
		cards.add(new Card("blue","draw2",-1));
		cards.add(new Card("blue","draw2",-1));
		cards.add(new Card("green","draw2",-1));
		cards.add(new Card("green","draw2",-1));
		cards.add(new Card("yellow","draw2",-1));
		cards.add(new Card("yellow","draw2",-1));
		
		cards.add(new Card("red","reverse",-1));
		cards.add(new Card("red","reverse",-1));
		cards.add(new Card("blue","reverse",-1));
		cards.add(new Card("blue","reverse",-1));		
		cards.add(new Card("green","reverse",-1));
		cards.add(new Card("green","reverse",-1));
		cards.add(new Card("yellow","reverse",-1));
		cards.add(new Card("yellow","reverse",-1));
		
		cards.add(new Card("red","skip",-1));
		cards.add(new Card("red","skip",-1));
		cards.add(new Card("blue","skip",-1));
		cards.add(new Card("blue","skip",-1));
		cards.add(new Card("green","skip",-1));
		cards.add(new Card("green","skip",-1));
		cards.add(new Card("yellow","skip",-1));
		cards.add(new Card("yellow","skip",-1));
		
		cards.add(new Card("all","draw4",-2));
		cards.add(new Card("all","draw4",-2));
		cards.add(new Card("all","draw4",-2));
		cards.add(new Card("all","draw4",-2));
		
		cards.add(new Card("all","wild",-2));
		cards.add(new Card("all","wild",-2));
		cards.add(new Card("all","wild",-2));
		cards.add(new Card("all","wild",-2));
		
		System.out.println(cards.size());
	}
	
}
