package playerClient;

import ocsf.client.AbstractClient;

public class PlayerClient extends AbstractClient 
{

private CreateAccountControl createAccountControl;	
private  InitialControl  initialControl;
private  LoginControl loginControl;
	
private LoginData loginData;
private NewGameData new
		
 //Constructor
public PlayerClient() {
	super("localhost",8300);
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
