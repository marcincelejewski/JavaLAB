package spaceteam.Api.CptCommands;

import spaceteam.Api.CaptainCommand;
import spaceteam.Api.Player.Role;
import spaceteam.Api.SpaceshipMeasurements;
import spaceteam.Server.GameEvent;
import spaceteam.Server.GameEvent.EventType;
import spaceteam.Server.PropertyEvent;

public class TurboCommand extends CaptainCommand<Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4185085065654136402L;
	boolean desiredValue;
	
	public TurboCommand(boolean desiredValue) {
		super("Ustaw turbo jako " + desiredValue, Role.Mechanik, TurboCommand.class.getSimpleName());
		this.desiredValue = desiredValue;
	}

	@Override
	public boolean validateValue(Boolean newValue) {
		return desiredValue == newValue;
	}

	@Override
	public GameEvent successEvent() {
		return new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Turbo jest " + desiredValue, new PropertyEvent("Turbo", desiredValue));
	}

	@Override
	public void modifyOnSuccess(SpaceshipMeasurements measurments) {
		measurments.setTurbo(desiredValue);
	}

}
