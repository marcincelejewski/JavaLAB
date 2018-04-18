package spaceteam.Api;

import java.io.Serializable;

public class SpaceshipMeasurements implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1667634156596192754L;
	//Guardian
	private int shieldStrength = 10;
	private boolean comouflage = false;
	
	//Mechanic
	private int enginePower = 0;
	private boolean turbo = false;
	
	public SpaceshipMeasurements() {}

	public int getShieldStrength() {
		return shieldStrength;
	}

	public void setShieldStrength(int shieldStrength) {
		this.shieldStrength = shieldStrength;
	}

	public int getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(int enginePower) {
		this.enginePower = enginePower;
	}

	public boolean isTurbo() {
		return turbo;
	}

	public void setTurbo(boolean turbo) {
		this.turbo = turbo;
	}

	public boolean isComouflage() {
		return comouflage;
	}

	public void setComouflage(boolean comouflage) {
		this.comouflage = comouflage;
	}
}
