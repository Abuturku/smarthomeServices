package de.mosbach.lan.smarthome.services;

import java.util.List;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import de.mosbach.lan.smarthome.houseComponents.AirConditioner;
import de.mosbach.lan.smarthome.houseComponents.Database;
import de.mosbach.lan.smarthome.houseComponents.Database.EntityManagerTransaction;
import jersey.repackaged.com.google.common.collect.ImmutableList;

@WebService(serviceName = "airConditionerService", name="airConditionerService", portName="airConditionerService")
public class AirConditionerService {

	public void turnOffAirConditioner(long id){
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				AirConditioner ac = em.find(AirConditioner.class, id);
				ac.turnOff();
				em.merge(ac);	
			}
		});
	}
	
	public void turnOnAirConditioner(long id){
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				AirConditioner ac = em.find(AirConditioner.class, id);
				ac.turnOn();
				em.merge(ac);	
			}
		});
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
	
	public long addAirConditioner(){
		AirConditioner newAc = new AirConditioner();
		
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
	
	public void removeAirConditioner(long id){
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				AirConditioner acToBeRemoved = em.find(AirConditioner.class, id);
				em.remove(acToBeRemoved);
				
			}
		});
	}
	
	public AirConditioner getAirConditionerById(long id){
		final EntityManager em = Database.getEntityManager();
		
		try {
			return (AirConditioner) em.createQuery("from AirConditioner c where c.id = :id").setParameter("id", id).getSingleResult();
		} finally{
			em.close();
		}
	}

}
