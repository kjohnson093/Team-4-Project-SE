package playerClient;

import java.util.ArrayList;

import playerGUI.LoginControl;
import playerGUI.CreateAccountControl;
import playerGUI.InitialControl;
import playerGUI.LoginData;
import playerGUI.NewGameData;
import playerGUI.InitialMenuControl;
import playerGUI.GameControl;
import playerGUI.Error;
import playerGUI.TopCard;
import playerGUI.Card;

import ocsf.client.AbstractClient;
//import gameManagement.GameData;
//import playerGUI.*;

public class PlayerClient extends AbstractClient {

    private LoginControl loginControl;
    private CreateAccountControl createAccountControl;
    private InitialControl initialControl;
    private LoginData loginData;
    private NewGameData newGameData;
    private InitialMenuControl initialMenuControl;
    private GameControl gameControl;
    

	public boolean isCurrent=true;
	public PlayerClient() {
		//super("10.251.25.103", 8300);
		super("localhost", 8300);
	}
	//Testing
    @SuppressWarnings("unchecked")
	@Override
    protected void handleMessageFromServer(Object msg) {
    	if (msg instanceof String)
	    {
	      // Get the text of the message.
	      String message = (String)msg;
	      
	      // If we successfully logged in, tell the login controller.
	      if (message.equals("LoginSuccessful"))
	      {
	        loginControl.loginSuccess();
	      }
	      
	      // If we successfully created an account, tell the create account controller.
	      else if (message.equals("CreateAccountSuccessful"))
	      {
	        createAccountControl.createAccountSuccess();
	      }
	    }
	    
	    // If we received an Error, figure out where to display it.
	    else if (msg instanceof Error)
	    {
	      // Get the Error object.
	      Error error = (Error)msg;
	      
	      // Display login errors using the login controller.
	      if (error.getType().equals("Login"))
	      {
	        loginControl.displayError(error.getMessage());
	      }
	      
	      // Display account creation errors using the create account controller.
	      else if (error.getType().equals("CreateAccount"))
	      {
	        createAccountControl.displayError(error.getMessage());
	      }
	    }
    	
    	if(msg instanceof TopCard) {
    		System.out.println("received topCard");
    		gameControl.setTopCard((TopCard)msg);
		}
    	
		if(msg instanceof ArrayList) {
			System.out.println("received Arraylist");
			gameControl.setDeck((ArrayList<Card>)msg);
		}
		
		if(msg instanceof Boolean) {
			isCurrentPlayer((Boolean)msg);
		}
		
		if(msg instanceof Card) {
			gameControl.addCardToDeck((Card)msg);
		}
    }

    public void connectionException(Throwable exception) {
        System.out.println("Connection Exception Occurred");
        System.out.println(exception.getMessage());
        System.out.println(exception.getStackTrace());
    }

    public void connectionEstablished() {
        System.out.println("Client Connected");
    }

    public CreateAccountControl getCreateAccountControl() {
        return createAccountControl;
    }

    public InitialControl getInitialControl() {
        return initialControl;
    }

    public void setInitialControl(InitialControl initialControl) {
        this.initialControl = initialControl;
    }

    public LoginControl getLoginControl() {
        return loginControl;
    }

    public void setLoginControl(LoginControl loginControl) {
        this.loginControl = loginControl;
    }

    public void setCreateAccountControl(CreateAccountControl createAccountControl) {
        this.createAccountControl = createAccountControl;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public NewGameData getNewGameData() {
        return newGameData;
    }

    public void setNewGameData(NewGameData newGameData) {
        this.newGameData = newGameData;
    }

    public InitialMenuControl getInitialMenuControl() {
        return initialMenuControl;
    }

    public void setInitialMenuControl(InitialMenuControl initialMenuControl) {
        this.initialMenuControl = initialMenuControl;
    }

    public GameControl getGameControl() {
        return gameControl;
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

	public boolean isCurrentPlayer(boolean isCurrent) {
		this.isCurrent=isCurrent;
		return this.isCurrent;
	}
}
