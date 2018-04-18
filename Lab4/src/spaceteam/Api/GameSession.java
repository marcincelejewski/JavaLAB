package spaceteam.Api;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import spaceteam.Api.Player.Role;
import spaceteam.Api.CptCommands.*;
import spaceteam.Server.GameEvent;
import spaceteam.Server.GameEvent.EventType;



public class GameSession implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5438197848342599427L;

	private Status status;

	private SpaceshipMeasurements measurements;

	private Map<String, Player> players;

	private List<GameEvent> serverEvents;

	private CaptainCommand<Object> currentCaptainCommand;

	public GameSession() {

		players = new HashMap<>();
		serverEvents = new ArrayList<>();

		status = Status.NotStarted;
	}

	public GameEvent startGame() {

		if (isGameStarted())
			return null;

		this.status = Status.Running;
		this.setMeasurements(new SpaceshipMeasurements());

		GameEvent e = new GameEvent(EventType.EVENT_GAME_STARTED,
				String.format("Game started: players %d", players.values().size()),
				new HashSet<Role>(players.values().stream().map(p -> p.getRole()).collect(Collectors.toList())));
		serverEvents.add(e);

		return e;
	}


	public GameEvent captainSendsCommend(CaptainCommand<Object> cmd) throws RemoteException {
		setCurrentCaptainCommand(cmd);

		GameEvent e = new GameEvent(EventType.EVENT_CAPTAIN_SENDS_COMMAND, "Captain sends commend: " + cmd.getMessage(),
				cmd);
		serverEvents.add(e);

		return e;
	}

	public GameEvent joinGame(Player newPlayer) throws RemoteException {

		String playerKey = newPlayer.getId();
		if ((newPlayer.isCaptain() && isCaptainInSquad()) || players.containsKey(playerKey)) {
			return null;
		}

		players.put(playerKey, newPlayer);

		GameEvent e = new GameEvent(EventType.EVENT_NEW_PLAYER_JOINED, "New player joined: " + newPlayer.getName(),
				newPlayer);
		serverEvents.add(e);
		return e;
	}

	public GameEvent setEnginePower(int newPower) throws RemoteException {
		return testCurrentCommand(newPower, EnginePowerCommand.class.getSimpleName());
	}

	public GameEvent setShieldStrength(int newShieldStrength) throws RemoteException {
		return testCurrentCommand(newShieldStrength, ShieldStrengthCommand.class.getSimpleName());
	}

	public GameEvent setTurbo(boolean newTurbo) {
		return testCurrentCommand(newTurbo, TurboCommand.class.getSimpleName());
	}
	
	public GameEvent setComouflage(boolean newComouflage) {
		return testCurrentCommand(newComouflage, ComouflageCommand.class.getSimpleName());
	}

	private GameEvent testCurrentCommand(Object newValue, String commandType) {

		GameEvent e = null;
		CaptainCommand<Object> currentCmd = getCurrentCaptainCommand();
		if (currentCmd == null)
			return e;

		try {
			if (currentCmd.validate(newValue, commandType)) {

				currentCmd.modifyOnSuccess(getMeasurements());
				e = currentCmd.successEvent();

				setCurrentCaptainCommand(null);
			}
		} catch (Exception ex) {
		}

		return e;
	}

	public boolean isCaptainInSquad() {
		return getPlayers().values().stream().anyMatch(p -> p.isCaptain());
	}

	public boolean isGameStarted() {
		return getStatus() != Status.NotStarted;
	}
	
	public boolean isGameEnded() {
		return getStatus() != Status.Running;
	}

	public Status getStatus() {
		return status;
	}

	public Map<String, Player> getPlayers() {
		return players;
	}

	public SpaceshipMeasurements getMeasurements() {
		return measurements;
	}

	public void setMeasurements(SpaceshipMeasurements measurements) {
		this.measurements = measurements;
	}

	public CaptainCommand<Object> getCurrentCaptainCommand() {
		return currentCaptainCommand;
	}

	public void setCurrentCaptainCommand(CaptainCommand<Object> cmd) {
		this.currentCaptainCommand = cmd;
	}

	public List<GameEvent> getServerEvents() {
		return serverEvents;
	}

	enum Status {
		NotStarted, Running, Finished
	}
}
