package de.mosbach.lan.smarthome.houseComponents;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Database {
	
	private static final String PERSISTENCE_UNIT_NAME_PRODUCTIVE = "productive";
	
	private final EntityManagerFactory emf;
	
	private static Database instance;
	
	public static Database getInstance()
	{
		if (instance == null)
		{
			synchronized (Database.class)
			{
				if (instance == null)
				{
					instance = new Database(PERSISTENCE_UNIT_NAME_PRODUCTIVE);
				}
			}
		}
		return instance;
	}
	
	private Database(String persistenceUnitName)
	{
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	
	public static EntityManager getEntityManager()
	{
		return getInstance().emf.createEntityManager();
	}

}
