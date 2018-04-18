package spaceteam.Api;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.UUID;

public class Player implements Remote, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5469754633403914063L;
	private String id;
	private String name;
	private Role role;
	
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
		
		this.id = UUID.randomUUID().toString();
	}
	
	public boolean isCaptain() {
		return getRole() == Role.Kapitan;
	}
	
	public String getName() {
		return name;
	}

	public Role getRole() {
		return role;
	}

	public String getId() {
		return id;
	}

	public enum Role {
		Kapitan,		
		Obroñca,
		Mechanik
	}
}
