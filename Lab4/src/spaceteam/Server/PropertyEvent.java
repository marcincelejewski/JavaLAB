package spaceteam.Server;

import java.io.Serializable;

public class PropertyEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2497461387196846608L;
	private String propertyName;
	private Object propertyValue;
	
	public PropertyEvent(String propertyName, Object propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}


	public Object getPropertyValue() {
		return propertyValue;
	}
}
