package playerGUI;

import java.util.ArrayList;
import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient{

	private GameControl gc;
	boolean isCurrent=true;
	public GameClient() {
		super("localhost", 8300);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 instanceof TopCard) {
			System.out.println("its a top card");
			gc.setTopCard((TopCard)arg0);
		}
		if(arg0 instanceof ArrayList) {
			gc.setDeck((ArrayList<Card>)arg0);
		}
		if(arg0 instanceof Boolean) {
			isCurrentPlayer((Boolean)arg0);
		}
		if(arg0 instanceof Card) {
			gc.addCardToDeck((Card)arg0);
		}
	}

	public void setGameControl(GameControl gc) {
		this.gc=gc;
	}

	public boolean isCurrentPlayer(boolean isCurrent) {
		this.isCurrent=isCurrent;
		return this.isCurrent;
	}
}
