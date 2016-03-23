package de.mosbach.lan.smarthome.services;

import java.util.List;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import de.mosbach.lan.smarthome.houseComponents.Database;
import de.mosbach.lan.smarthome.houseComponents.Database.EntityManagerTransaction;
import de.mosbach.lan.smarthome.houseComponents.Heater;
import jersey.repackaged.com.google.common.collect.ImmutableList;

@WebService(serviceName = "heaterService", name="HeaterService", portName="HeaterService")
public class HeaterService {

	public boolean turnOffHeater(String roomName){
		List<Heater> heaters = getHeatersByRoomName(roomName);
		if (heaters.size() == 0) {
			return false;
		}
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				for (Heater heater : heaters) {
					heater.turnOff();
					em.merge(heater);
				}
			}
		});
		return true;
	}
	
	public boolean turnOnHeater(String roomName){
		List<Heater> heaters = getHeatersByRoomName(roomName);
		if (heaters.size() == 0) {
			return false;
		}
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				for (Heater heater : heaters) {
					heater.turnOn();
					em.merge(heater);
				}
			}
		});
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Heater> getAllHeaters(){
		final EntityManager em = Database.getEntityManager();
		
		try {
			return ImmutableList.copyOf(em.createQuery("from Heater h").getResultList());
		} finally {
			em.close();
		}
	}
	
	public long addHeater(String roomName){
		Heater newHeater = new Heater();
		newHeater.setRoomName(roomName);
		
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				em.persist(newHeater);
			}
		});
		return newHeater.getId();
	}
	
	public void removeHeater(String roomName){
		Database.transaction(new EntityManagerTransaction() {
			
			@Override
			public void run(EntityManager em, EntityTransaction transaction) {
				Heater heaterToBeRemoved = em.find(Heater.class, roomName);
				em.remove(heaterToBeRemoved);
				
			}
		});
	}
	
	public Heater getHeaterByRoomName(String roomName){
		final EntityManager em = Database.getEntityManager();
		
		try {
			return (Heater) em.createQuery("from Heater h where h.roomName = :roomName").setParameter("roomName", roomName).getSingleResult();
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Heater> getHeatersByRoomName(String roomName) {
		final EntityManager em = Database.getEntityManager();

		try
		{
			return ImmutableList.copyOf(em.createQuery("from Heater h where h.roomName = :roomName").setParameter("roomName", roomName).getResultList());
		}
		finally
		{
			em.close();
		}
	}

}
