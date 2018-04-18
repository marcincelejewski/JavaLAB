package spaceteam.Api;

import java.rmi.RemoteException;

import spaceteam.Server.GameEvent;


public interface CanHandleGameEvent {
	public void handleGameEvent(GameEvent event) throws RemoteException;
}
