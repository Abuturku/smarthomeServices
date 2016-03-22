package de.mosbach.lan.smarthome.services;

import java.util.List;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import de.mosbach.lan.smarthome.houseComponents.Database;
import de.mosbach.lan.smarthome.houseComponents.Database.EntityManagerTransaction;
import de.mosbach.lan.smarthome.houseComponents.Window_;

@WebService(serviceName = "windowService", name = "windowService", portName = "windowService")
public class WindowService {

	public boolean closeWindow(String roomName) {	
		List<Window_> windows = getWindowsByRoomName(roomName);
		if (windows == null) {
			return false;
		}
		
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				for (Window_ window : windows) {
					window.close();
					em.merge(window);
				}
			}
		});
		return true;
	}

	public boolean openWindow(String roomName) {
		List<Window_> windows = getWindowsByRoomName(roomName);
		if (windows == null) {
			return false;
		}
		
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				for (Window_ window : windows) {
					window.open();
					em.merge(window);
				}
			}
		});
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Window_> getAllWindows() {
		final EntityManager em = Database.getEntityManager();

		try
		{
			return ImmutableList.copyOf(em.createQuery("from Window_ w").getResultList());
		}
		finally
		{
			em.close();
		}
	}
	
	
	public long addWindow(String roomName) {
		
		Window_ newWindow = new Window_();
		newWindow.setRoomName(roomName);
		
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				em.persist(newWindow);
			}
		});
		return newWindow.getId();
	}
	
	public void removeWindow(long id){
		
		
		Database.transaction(new EntityManagerTransaction()
		{
			@Override
			public void run(EntityManager em, EntityTransaction transaction)
			{
				Window_ windowToBeRemoved = em.find(Window_.class, id);
				em.remove(windowToBeRemoved);
				
			}
		});
	}

	public Window_ getWindowById(long id) {		
		final EntityManager em = Database.getEntityManager();
		
		try {
			return (Window_) em.createQuery("from Window_ w where w.id = :id").setParameter("id", id).getSingleResult();
		} finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Window_> getWindowsByRoomName(String roomName){
		final EntityManager em = Database.getEntityManager();

		try
		{
			return ImmutableList.copyOf(em.createQuery("from Window_ w where w.roomName = :roomName").setParameter("roomName", roomName).getResultList());
		}
		finally
		{
			em.close();
		}
	}
}
