package spaceteam.Api;

import java.io.Serializable;
import spaceteam.Api.Player.Role;
import spaceteam.Server.GameEvent;




public abstract class CaptainCommand<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3319461087474425451L;
	private String message;
	private Role playerRole;
	private String commandType;
	
	public CaptainCommand(String msg, Role playerRole, String commandType) {
		this.message = msg;
		this.playerRole = playerRole;
		this.commandType = commandType;
	}

	public String getMessage() {
		return message;
	}

	public Role getPlayerRole() {
		return playerRole;
	}
	
	public boolean validate(T newValue, String commandType) {
		
		if(!this.commandType.equalsIgnoreCase(commandType)) {
			return false;
		}
		
		return validateValue(newValue);
	}
	
	protected abstract boolean validateValue(T newValue);
	public abstract GameEvent successEvent();
	public abstract void modifyOnSuccess(SpaceshipMeasurements measurments);

}
