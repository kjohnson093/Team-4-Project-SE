package playerClient;

import ocsf.client.AbstractClient;

public class PlayerClient extends AbstractClient 
{

	private  LoginControl loginControl;
	private CreateAccountControl createAccountControl;	
	
	private  InitialControl  initialControl;
	private LoginData loginData;
	private NewGameData New;
	
	public ChatClient()
	{
		super("local host", 8300);
	}
	
		
 @Override
	protected void handleMessageFromServer(Object msg)
	{
		if(msg instanceof LoginData) {
			LoginData ld = (LoginData)msg;
			if(ld.getUsername().equals("")) {
				System.out.println("Fail to login!");
			}
			else {
				System.out.println("Login Successfully!");
				lc.loginSuccess();
			}
		}
		else if(msg instanceof CreateAccountData)
		{
			CreateAccountData cad = (CreateAccountData)msg;
		if(cad.getUsername().equals("")) {
			System.out.println("Fail to create new account!");
			cac.displayError("Username already exist!");
		}
		else {
			System.out.println("Account created successfully!");
			cac.createSuccess();
		}
		}
		else if(msg instanceof String) {
			System.out.println("Server Message sent to Client\n" + msg);
		}
	}

	public void connectionException (Throwable exception) {
		System.out.println("Connection Exception Occurred");
		System.out.println(exception.getMessage());
		System.out.println(exception.getStackTrace());
	}

	public void connectionEstablished() {
		System.out.println("Client Connected");
	}
}
	

public CreateAccountControl getCreateAccountControl() {
	  return createAccountControl;
	  }

	
public InitialControl getInitialControl() {
		    return InitialControl;
		  }
public void setInitialControl(InitialControl initialControl) {
		    InitialControl = initialControl;
}
	
public LoginControl getLoginControl() {
		    return loginControl;
		  }
		  public void setLoginControl(LoginControl loginControl) {
		    this.loginControl = loginControl;
		  }


}
