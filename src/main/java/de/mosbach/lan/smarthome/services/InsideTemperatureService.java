package de.mosbach.lan.smarthome.services;

import de.mosbach.lan.smarthome.houseComponents.Database;
import de.mosbach.lan.smarthome.houseComponents.IStatusData;
import de.mosbach.lan.smarthome.houseComponents.InsideTemperature;
import de.mosbach.lan.smarthome.houseComponents.Database.EntityManagerTransaction;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.bind.annotation.XmlElement;

@WebService(serviceName = "insideTemperatureService", name = "insideTemperatureService", portName = "insideTemperatureService")
public class InsideTemperatureService {

	public String addInsideTemperature(
			@XmlElement(required = true) @WebParam(name = "roomName") String roomName) {
		InsideTemperature is = new InsideTemperature(roomName);

		Database.transaction(new EntityManagerTransaction() {
			@Override
			public void run(EntityManager em, EntityTransaction transaction) {
				em.persist(is);
			}
		});
		return is.getRoomName();
	}

	public void removeInsideTemperature(
			@XmlElement(required = true) @WebParam(name = "roomName") String roomName) {
		Database.transaction(new EntityManagerTransaction() {
			@Override
			public void run(EntityManager em, EntityTransaction transaction) {
				InsideTemperature isToBeRemoved = em.find(
						InsideTemperature.class, roomName);
				em.remove(isToBeRemoved);

			}
		});
	}

	public InsideTemperature getInsideTempByRoomName(
			@XmlElement(required = true) @WebParam(name = "roomName") String roomName) {
		final EntityManager em = Database.getEntityManager();

		try {
			return (InsideTemperature) em
					.createQuery(
							"from InsideTemperature i where i.roomName = :roomName")
					.setParameter("roomName", roomName).getSingleResult();
		} finally {
			em.close();
		}
	}

	public int getTemperature(
			@XmlElement(required = true) @WebParam(name = "roomName") String roomName,
			@XmlElement(required = true) @WebParam(name = "statusData") IStatusData statusData) {
		
		InsideTemperature insideTemp = getInsideTempByRoomName(roomName);

		// check if change of GoalTemp is required
		double oldGoalTemp = insideTemp.getGoalTemp();
		double currentGoalTemp;

		if (statusData.getStateWindow() == IStatusData.TRUE) {
			currentGoalTemp = statusData.getOutsideTemperature();
		} else {
			currentGoalTemp = statusData.getInsideTempRequirement();
		}

		//if changed, update goal temperature
		if (currentGoalTemp != oldGoalTemp) {
			
			insideTemp.setNewGoalTemperature(currentGoalTemp);
			Database.transaction(new EntityManagerTransaction() {
				@Override
				public void run(EntityManager em, EntityTransaction transaction) {
					em.merge(insideTemp);
				}
			});
		}

		//return inside Temperature
		return insideTemp.getInsideTemperature();

	}

	public void setGoalTemperature(
			@XmlElement(required = true) @WebParam(name = "roomName") String roomName,
			@XmlElement(required = true) @WebParam(name = "temperature") double temperature) {

		InsideTemperature insideTemp = getInsideTempByRoomName(roomName);
		insideTemp.setNewGoalTemperature(temperature);

		Database.transaction(new EntityManagerTransaction() {
			@Override
			public void run(EntityManager em, EntityTransaction transaction) {
				em.merge(insideTemp);
			}
		});
	}

	public int getInsideTemperature(
			@XmlElement(required = true) @WebParam(name = "roomName") String roomName) {
		InsideTemperature insideTemp = getInsideTempByRoomName(roomName);
		return insideTemp.getInsideTemperature();
	}

}
