package de.mosbach.lan.smarthome.OwmClient;

import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class OwmClient {

	public static final String API_KEY = "cfd2623bf6a6ccbae8e735791d2dce9c"; // get your own key (see http://openweathermap.org/appid)

	private static final String OWM_API_2_5_URL = "http://api.openweathermap.org/data/2.5/weather";

	public static final int OWM_CITY_ID_MOSBACH = 2869120;

	private final WebTarget wt;
	
	public OwmClient(String apiKey)
	{
		final Client client = ClientBuilder.newClient();
		wt = client.target(OWM_API_2_5_URL).queryParam("appid", apiKey);
		
	}
	
	//Does the request to the OpenWeatherMap API
	public float requestCurrentTemperature(int cityId)
	{
		final Response response = wt.queryParam("id", cityId).request(MediaType.APPLICATION_JSON).get();
		final JsonObject jsonObject = Json.createReader(response.readEntity(InputStream.class)).readObject();

		return jsonResponseToTemperature(jsonObject);
	}
	
	//Converts the JSON Response of OWM to a Celsius Temperature Value
	private float jsonResponseToTemperature(final JsonObject jsonObject)
	{
		final JsonObject mainData = jsonObject.getJsonObject("main");

		final float tempCelsiusCurrent = getTemperature(mainData, "temp");

		return tempCelsiusCurrent;
	}
	
	//Returns the result of OpenWeatherMap in Celsius
	private float getTemperature(JsonObject jsonObject, String name)
	{
		final float tempInKelvin = (float) jsonObject.getJsonNumber(name).doubleValue();
		return tempInKelvin - 273.15F;
	}
}
