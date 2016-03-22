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
public class Window_ {
	
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
		return roomName;
	}
	

	public void setRoomName(String roomId) {
		this.roomName = roomId;
	}
	
	public Window_(){
		this.state = State.CLOSED;
	}
	
	public boolean close(){
		this.state = State.CLOSED;
		return true;
	}
	
	public boolean open(){
		this.state = State.OPENED;
		return true;
	}
}
