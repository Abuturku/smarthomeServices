package de.mosbach.lan.smarthome.houseComponents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Heater {
	private long id;
	private State state;
	private String roomName;
	
	@Id
	@GeneratedValue
	@XmlTransient
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column
	@XmlAttribute
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	@Column
	@XmlAttribute
	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
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
