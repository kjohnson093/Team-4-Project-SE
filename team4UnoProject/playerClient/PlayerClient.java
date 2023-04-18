package playerClient;

import ocsf.client.AbstractClient;
import gameManagement.GameData;
import playerGUI.*;

public class PlayerClient extends AbstractClient {

    private LoginControl loginControl;
    private CreateAccountControl createAccountControl;
    private InitialControl initialControl;
    private LoginData loginData;
    private NewGameData newGameData;
    private InitialMenuControl initialMenuControl;
    private GameControl gameControl;

    public PlayerClient() {
        super("localhost", 8300);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg instanceof LoginData) {
            LoginData ld = (LoginData) msg;
            if (ld.getUsername().equals("")) {
                System.out.println("Fail to login!");
            } else {
                System.out.println("Login Successfully!");
                // ld.loginSuccess();
            }
        } else if (msg instanceof CreateAccountData) {
            CreateAccountData cad = (CreateAccountData) msg;
            if (cad.getUsername().equals("")) {
                System.out.println("Fail to create new account!");
                // cac.displayError("Username already exist!");
            } else {
                System.out.println("Account created successfully!");
                // cac.createSuccess();
            }
        } else if (msg instanceof String) {
            System.out.println("Server Message sent to Client\n" + msg);
        } else if (msg instanceof GameData) {
            if (gameControl != null) {
                gameControl.handleGameData((GameData) msg);
            }
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
}
