package de.mosbach.lan.smarthome.services;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.bind.annotation.XmlElement;

import de.mosbach.lan.smarthome.houseComponents.AirConditioner;
import de.mosbach.lan.smarthome.houseComponents.Database;
import de.mosbach.lan.smarthome.houseComponents.Database.EntityManagerTransaction;
import jersey.repackaged.com.google.common.collect.ImmutableList;

@WebService(serviceName = "airConditionerService", name="airConditionerService", portName="airConditionerService")
public class AirConditionerService {

	public boolean turnOffAirConditioner(String roomName){
		List<AirConditioner> acs = getAcsByRoomName(roomName);
		if (acs.size() == 0) {
			return false;
		}
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				for (AirConditioner airConditioner : acs) {
					airConditioner.turnOff();
					em.merge(airConditioner);
				}
			}
		});
		return true;
	}
	
	public boolean turnOnAirConditioner(String roomName){
		List<AirConditioner> acs = getAcsByRoomName(roomName);
		if (acs.size() == 0) {
			return false;
		}
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				for (AirConditioner airConditioner : acs) {
					airConditioner.turnOn();
					em.merge(airConditioner);
				}
			}
		});
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<AirConditioner> getAllAirConditioners(){
		final EntityManager em = Database.getEntityManager();
		
		try
		{
			return ImmutableList.copyOf(em.createQuery("from AirConditioner c").getResultList());
		}
		finally
		{
			em.close();
		}
	}
	
	public long addAirConditioner(String roomName){
		AirConditioner newAc = new AirConditioner();
		newAc.setRoomName(roomName);
		
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				em.persist(newAc);
			}
		});
		return newAc.getId();
	}
	
	public void removeAirConditioner(String roomName){
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				AirConditioner acToBeRemoved = em.find(AirConditioner.class, roomName);
				em.remove(acToBeRemoved);
				
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private List<AirConditioner> getAcsByRoomName(@XmlElement(required = true) @WebParam(name = "roomName")String roomName) {
		final EntityManager em = Database.getEntityManager();

		try
		{
			return ImmutableList.copyOf(em.createQuery("from AirConditioner c where c.roomName = :roomName").setParameter("roomName", roomName).getResultList());
		}
		finally
		{
			em.close();
		}
	}

}
