package de.mosbach.lan.smarthome.services;

import de.mosbach.lan.smarthome.houseComponents.Database;
import de.mosbach.lan.smarthome.houseComponents.InsideTemperature;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlElement;


@WebService(serviceName = "insideTemperatureService", name="insideTemperatureService", portName="insideTemperatureService")
public class InsideTemperatureService {


	public InsideTemperature getInsideTempsByRoomName(@XmlElement(required = true) @WebParam(name = "roomName")String roomName){
		final EntityManager em = Database.getEntityManager();

		try
		{
			return (InsideTemperature) em.createQuery("from InsideTemperature i where i.roomName = :roomName").setParameter("roomName", roomName).getSingleResult();
		}
		finally
		{
			em.close();
		}
	}
	
	public int getInsideTemperatureAfterSettingGoalTemperature(@XmlElement(required = true) @WebParam(name = "roomName") String roomName,
			@XmlElement(required = true) @WebParam(name = "temperature") double temperature){
		InsideTemperature insideTemp = getInsideTempsByRoomName(roomName);
		insideTemp.setNewGoalTemperature(temperature);
		
		return getInsideTemperature(roomName);
	}
	
	public int getInsideTemperature(@XmlElement(required = true) @WebParam(name = "roomName") String roomName){
		InsideTemperature insideTemp = getInsideTempsByRoomName(roomName);
		return insideTemp.getInsideTemperature();
	}
	
}
