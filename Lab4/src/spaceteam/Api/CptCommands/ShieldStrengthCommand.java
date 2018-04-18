package spaceteam.Api.CptCommands;

import spaceteam.Api.CaptainCommand;
import spaceteam.Api.Player.Role;
import spaceteam.Api.SpaceshipMeasurements;
import spaceteam.Server.GameEvent;
import spaceteam.Server.GameEvent.EventType;
import spaceteam.Server.PropertyEvent;

public class ShieldStrengthCommand extends CaptainCommand<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3428230424827293001L;
	int desiredValue;
	
	public ShieldStrengthCommand(int desiredValue) {
		super("Ustaw si³ê tarcz na " + desiredValue, Role.Obroñca, ShieldStrengthCommand.class.getSimpleName());
		// TODO Auto-generated constructor stub
		
		this.desiredValue = desiredValue;
	}

	@Override
	public boolean validateValue(Integer newValue) {
		return this.desiredValue == newValue;
	}

	@Override
	public GameEvent successEvent() {
		return new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Ustawiono si³ê tarcz na " + desiredValue, new PropertyEvent("ShieldStrength", desiredValue));
	}

	@Override
	public void modifyOnSuccess(SpaceshipMeasurements measurments) {
		measurments.setShieldStrength(desiredValue);
	}
}
