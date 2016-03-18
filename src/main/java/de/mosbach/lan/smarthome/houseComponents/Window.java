package de.mosbach.lan.smarthome.houseComponents;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Window {
	
	private long id;
	private State state;
	
	@Id
	@GeneratedValue
	@XmlTransient
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
	
	public Window(){
		super();
	}
	
	public void close(){
		this.state = State.CLOSED;
	}
	
	public void open(){
		this.state = State.OPENED;
	}
}
