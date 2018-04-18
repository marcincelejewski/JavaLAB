package spaceteam.Api.CptCommands;

import spaceteam.Api.CaptainCommand;
import spaceteam.Api.Player.Role;
import spaceteam.Api.SpaceshipMeasurements;
import spaceteam.Server.GameEvent;
import spaceteam.Server.GameEvent.EventType;
import spaceteam.Server.PropertyEvent;

public class EnginePowerCommand extends CaptainCommand<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6628919924241319555L;
	int desiredValue;
	
	public EnginePowerCommand(int desiredValue) {
		super("Ustaw moc silników na " + desiredValue, Role.Mechanik, EnginePowerCommand.class.getSimpleName());
		
		this.desiredValue = desiredValue;
	}

	@Override
	public boolean validateValue(Integer newValue) {
		return desiredValue == newValue;
	}

	@Override
	public GameEvent successEvent() {
		return new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Ustawiono moc silników na " + desiredValue, new PropertyEvent("EnginePower", desiredValue));
	}

	@Override
	public void modifyOnSuccess(SpaceshipMeasurements measurments) {
		measurments.setEnginePower(desiredValue);
	}
}
