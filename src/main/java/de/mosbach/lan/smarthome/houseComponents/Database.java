package de.mosbach.lan.smarthome.houseComponents;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public final class Database
{
	private static final String PERSISTENCE_UNIT_NAME_TESTING = "testing";
	private static final String PERSISTENCE_UNIT_NAME_PRODUCTIVE = "productive";

	private static Database instance;

	private final EntityManagerFactory emf;

	public static interface EntityManagerAction
	{
		void run(EntityManager em);
	}

	public static interface EntityManagerTransaction
	{
		void run(EntityManager em, EntityTransaction transaction);
	}

	public synchronized static Database enableTesting()
	{
		instance = new Database(PERSISTENCE_UNIT_NAME_TESTING);
		return instance;
	}

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

	@Override
	protected void finalize() throws Throwable
	{
		if (emf != null)
		{
			emf.close();
		}

		super.finalize();
	}

	public static EntityManager getEntityManager()
	{
		return getInstance().emf.createEntityManager();
	}

	public static void execute(final EntityManagerAction action)
	{
		final EntityManager em = Database.getEntityManager();

		try
		{
			action.run(em);
		}
		finally
		{
			em.close();
		}
	}

	public static void transaction(final EntityManagerTransaction transactionAction)
	{
		execute(new EntityManagerAction()
		{
			@Override
			public void run(EntityManager em)
			{
				final EntityTransaction transaction = em.getTransaction();

				transaction.begin();

				try
				{
					transactionAction.run(em, transaction);
				}
				catch (final Exception e)
				{
					// TODO do some logging
					transaction.setRollbackOnly();
				}

				// auto-commit
				if (transaction.isActive() && !transaction.getRollbackOnly())
				{
					transaction.commit();
				}
			}
		});
	}
}
