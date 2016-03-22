package de.mosbach.lan.smarthome.services;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.bind.annotation.XmlElement;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import de.mosbach.lan.smarthome.houseComponents.Database;
import de.mosbach.lan.smarthome.houseComponents.Database.EntityManagerTransaction;
import de.mosbach.lan.smarthome.houseComponents.Window_;

@WebService(serviceName = "windowService", name = "windowService", portName = "windowService")
public class WindowService {

	public boolean closeWindow(@XmlElement(required = true) @WebParam(name = "roomName")String roomName) {	
		List<Window_> windows = getWindowsByRoomName(roomName);
		if (windows.size() == 0) {
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
	
	public boolean openWindow(@XmlElement(required = true) @WebParam(name = "roomName")String roomName) {
		List<Window_> windows = getWindowsByRoomName(roomName);
		if (windows.size() == 0) {
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
	
	
	public long addWindow(@XmlElement(required = true) @WebParam(name = "roomName")String roomName) {
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
	
	public void removeWindow(@WebParam(name = "windowId")long id){
		
		
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

	public Window_ getWindowById(@WebParam(name = "windowId")long id) {		
		final EntityManager em = Database.getEntityManager();
		
		try {
			return (Window_) em.createQuery("from Window_ w where w.id = :id").setParameter("id", id).getSingleResult();
		} finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Window_> getWindowsByRoomName(@XmlElement(required = true) @WebParam(name = "roomName")String roomName){
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
