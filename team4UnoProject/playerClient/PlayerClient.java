package playerClient;

import java.util.ArrayList;

import javax.swing.JLabel;

import playerGUI.LoginControl;
import playerGUI.CreateAccountControl;
import playerGUI.InitialControl;
import playerGUI.LoginData;
import playerGUI.InitialMenuControl;
import playerGUI.GameControl;
import playerGUI.Error;
import playerGUI.TopCard;
import playerGUI.Card;

import ocsf.client.AbstractClient;

public class PlayerClient extends AbstractClient {

	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private InitialControl initialControl;
	private LoginData loginData;
	
	private InitialMenuControl initialMenuControl;
	private GameControl gameControl;

	public boolean isCurrent=false;
	public PlayerClient() {
		//super("10.251.25.103", 8300);
		super("localhost", 8300);
		//super("192.168.0.24", 8300);
	}

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
			// If login failed, tell loginControl
			else if (message.equals("LoginFailed"))
			{
				loginControl.displayError("Invalid username/password.");
			}
			// If login failed, tell loginControl
			else if (message.equals("CreateAccountFailed"))
			{
				createAccountControl.displayError("Username already in use.");
			}
			
			if(message.equals("Your Turn")) {
				gameControl.updateLabel2(new JLabel(message));
			}
			if(message.equals("You Have Been Skip")) {
				gameControl.updateLabel(new JLabel(message));
			}
			if(message.equals("You Recieved 2 Cards")) {
				gameControl.updateLabel(new JLabel(message));
			}
			if(message.equals("You Recieved 4 Cards")) {
				gameControl.updateLabel2(new JLabel(message));
			}
			
			if(message.contains("Has Won The Game")) {
				gameControl.finalMessage(message);
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
			gameControl.setTopCard((TopCard)msg);
		}

		if(msg instanceof ArrayList) {
			gameControl.setDeck((ArrayList<Card>)msg);
		}

		if(msg instanceof Boolean) {
			setIsCurrentPlayer((Boolean)msg);
		}

		if(msg instanceof Card) {
			gameControl.addCardToDeck((Card)msg);
		}
		if(msg instanceof JLabel) {

			gameControl.updateLabel(((JLabel)msg));


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

	public boolean isCurrentPlayer() {
		return this.isCurrent;
	}

	public boolean setIsCurrentPlayer(boolean isCurrent) {
		this.isCurrent=isCurrent;
		return this.isCurrent;
	}
}
