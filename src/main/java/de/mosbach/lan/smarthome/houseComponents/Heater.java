package de.mosbach.lan.smarthome.houseComponents;

import javax.persistence.GeneratedValue;

public class Heater {
	private long id;
	private State state;
	
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	public Heater() {
		super();
	}
	
	public void turnOn(){
		this.state = State.TURNED_ON;
	}
	
	
	public void turnOff(){
		this.state = State.TURNED_OFF;
	}
}
