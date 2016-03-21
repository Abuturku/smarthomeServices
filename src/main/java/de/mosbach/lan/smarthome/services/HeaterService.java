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

	public void turnOffHeater(long id){
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				Heater heater= em.find(Heater.class, id);
				heater.turnOff();
				em.merge(heater);	
			}
		});
	}
	
	public void turnOnHeater(long id){
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				Heater heater= em.find(Heater.class, id);
				heater.turnOn();
				em.merge(heater);	
			}
		});
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
	
	public long addHeater(){
		Heater newHeater = new Heater();
		
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
	
	public void removeHeater(long id){
		Database.transaction(new EntityManagerTransaction() {
			
			@Override
			public void run(EntityManager em, EntityTransaction transaction) {
				Heater heaterToBeRemoved = em.find(Heater.class, id);
				em.remove(heaterToBeRemoved);
				
			}
		});
	}
	
	public Heater getHeaterById(long id){
		final EntityManager em = Database.getEntityManager();
		
		try {
			return (Heater) em.createQuery("from Heater h where h.id = :id").setParameter("id", id).getSingleResult();
		} finally {
			em.close();
		}
	}

}
