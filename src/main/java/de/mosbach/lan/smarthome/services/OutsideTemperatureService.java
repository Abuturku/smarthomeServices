package de.mosbach.lan.smarthome.services;

import javax.jws.WebService;

import de.mosbach.lan.smarthome.OwmClient.OwmClient;

@WebService(serviceName = "outsideTemperatureService", name="outsideTemperatureService", portName="outsideTemperatureService")
public class OutsideTemperatureService {
	
	private final OwmClient owmClient = new OwmClient(OwmClient.API_KEY);
	
	public float getMosbachTemperatureToday()
	{
		return owmClient.requestCurrentTemperature(OwmClient.OWM_CITY_ID_MOSBACH);
	}
}
