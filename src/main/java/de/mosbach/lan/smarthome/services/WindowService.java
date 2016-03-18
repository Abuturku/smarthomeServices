package de.mosbach.lan.smarthome.services;

import java.util.List;

import javax.jws.WebService;
import javax.persistence.EntityManager;

import de.mosbach.lan.smarthome.houseComponents.Database;
import de.mosbach.lan.smarthome.houseComponents.Window;

@WebService(serviceName = "windowService", name="windowService", portName="windowService")
public class WindowService {
	
	private final static EntityManager em = Database.getEntityManager();
	
	private static List<Window> windows;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			windows = (List<Window>) em.createQuery("FROM Window w").getResultList();
		} catch (Exception e) {

		} finally{
			em.close();
		}	
	}
	
	public void closeWindow(long id){
		for (Window window : windows) {
			if (window.getId() == id) {
				window.close();
			}
		}
	}
	
	public void openWindow(long id){
		for (Window window : windows) {
			if (window.getId() == id) {
				window.open();
			}
		}
	}
	
	public List<Window> getAllWindows(){
		return windows;
	}
	
	public long addWindow(){
		Window newWindow = new Window();
		windows.add(newWindow);
		return newWindow.getId();
	}
}
