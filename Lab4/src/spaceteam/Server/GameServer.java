package spaceteam.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import spaceteam.Api.CaptainCommand;
import spaceteam.Api.GameSession;
import spaceteam.Api.Player;
import spaceteam.Client.ClientRemote;

public class GameServer extends UnicastRemoteObject implements ServerRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2995118136514155089L;
	public static final String SERVER_LOOKUP = "//localhost/MyServer";
	public static final int SERVER_PORT = 1099;

	private List<ClientRemote> connectedClients;

	private GameSession gameSession;

	public GameServer() throws RemoteException {
		super();

		connectedClients = new ArrayList<>();

		gameSession = new GameSession();
	}

	@Override
	public GameEvent joinGame(ClientRemote client, Player newPlayer) throws RemoteException {

		connectedClients.add(client);

		GameEvent event = gameSession.joinGame(newPlayer);

		publish(event);
		return event;
	}

	@Override
	public GameSession getGameSession() throws RemoteException {
		return gameSession;
	}

	@Override
	public GameEvent startGame() throws RemoteException {
		GameEvent event = gameSession.startGame();

		publish(event);
		return event;
	}

	@Override
	public GameEvent endGame() throws RemoteException {
		return null;

	}

	@Override
	public GameEvent captainSendsCommend(CaptainCommand cmd) throws RemoteException {

		GameEvent event = gameSession.captainSendsCommend(cmd);

		publish(event);
		return event;
	}

	@Override
	public GameEvent setEnginePower(int newPower) throws RemoteException {
		GameEvent event = gameSession.setEnginePower(newPower);

		publish(event);
		return event;
	}

	@Override
	public GameEvent setShieldStrength(int newShieldStrength) throws RemoteException {
		GameEvent event = gameSession.setShieldStrength(newShieldStrength);

		publish(event);
		return event;
	}

	@Override
	public GameEvent setTurbo(boolean newValue) throws RemoteException {
		GameEvent event = gameSession.setTurbo(newValue);

		publish(event);
		return event;
	}

	@Override
	public GameEvent setComouflage(boolean newValue) throws RemoteException {
		GameEvent event = gameSession.setComouflage(newValue);

		publish(event);
		return event;
	}

	public void publish(GameEvent event) {
		connectedClients.stream().forEach(c -> {
			try {
				c.handleGameEvent(event);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

}
