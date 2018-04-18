package spaceteam.Api.CptCommands;

import spaceteam.Api.CaptainCommand;
import spaceteam.Api.SpaceshipMeasurements;
import spaceteam.Api.Player.Role;
import spaceteam.Server.GameEvent;
import spaceteam.Server.PropertyEvent;
import spaceteam.Server.GameEvent.EventType;

public class ComouflageCommand extends CaptainCommand<Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7844646626880063728L;
	boolean desiredValue;
	
	public ComouflageCommand(boolean desiredValue) {
		super("Ustaw maskowanie jako " + desiredValue, Role.Obroñca, ComouflageCommand.class.getSimpleName());

		this.desiredValue = desiredValue;
	}

	@Override
	public boolean validateValue(Boolean newValue) {
		return desiredValue == newValue;
	}

	@Override
	public GameEvent successEvent() {
		return new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Maskowanie jest " + desiredValue, new PropertyEvent("Maskowanie", desiredValue));
	}

	@Override
	public void modifyOnSuccess(SpaceshipMeasurements measurments) {
		measurments.setTurbo(desiredValue);
	}

}
