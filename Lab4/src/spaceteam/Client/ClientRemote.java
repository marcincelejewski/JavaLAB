package spaceteam.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import spaceteam.Api.CanHandleGameEvent;
import spaceteam.Api.Player;



public interface ClientRemote extends Remote, CanHandleGameEvent {
	public Player getPlayer() throws RemoteException;
}
