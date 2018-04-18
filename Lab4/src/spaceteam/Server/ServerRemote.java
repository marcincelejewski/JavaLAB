package spaceteam.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import spaceteam.Api.CaptainCommand;
import spaceteam.Api.GameSession;
import spaceteam.Api.Player;
import spaceteam.Client.ClientRemote;



public interface ServerRemote extends Remote {

	public GameSession getGameSession() throws RemoteException;

	public GameEvent joinGame(ClientRemote client, Player newPlayer) throws RemoteException;

	public GameEvent startGame() throws RemoteException;
	public GameEvent endGame() throws RemoteException;
	
	public GameEvent captainSendsCommend(CaptainCommand<?> cmd) throws RemoteException;
	
	public GameEvent setEnginePower(int newPower) throws RemoteException;
	public GameEvent setShieldStrength(int newShieldStrength) throws RemoteException;
	public GameEvent setTurbo(boolean newValue) throws RemoteException;
	public GameEvent setComouflage(boolean newValue) throws RemoteException;

}