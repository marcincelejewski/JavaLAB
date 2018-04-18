package spaceteam.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import spaceteam.Api.CanHandleGameEvent;
import spaceteam.Api.Player;
import spaceteam.Server.GameEvent;



public class Client extends UnicastRemoteObject implements ClientRemote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 738690008512587785L;

	Player player;
	
	CanHandleGameEvent eventHandler;
	
	public Client(Player player)  throws RemoteException {
		this.player = player;
	}
	
	public void setEventHandler(CanHandleGameEvent handler) {
		this.eventHandler = handler;
	}
	
	@Override
	public void handleGameEvent(GameEvent event) throws RemoteException {
		
		if(eventHandler != null)
			eventHandler.handleGameEvent(event);
	}

	@Override
	public Player getPlayer() throws RemoteException {
		return player;
	}

}
