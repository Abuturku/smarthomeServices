package de.mosbach.lan.smarthome.houseComponents;

public interface IStatusData {

	public static final int TRUE = 0;
	public static final int FALSE = 1;
	public static final int DEFEKT = -1;

	public void setInsideTempRequirement(int value);

	public int getInternalTemperature();

	public int getOutsideTemperature();

	public int getStateAirConditioner();

	public int getStateHeater();

	public int getStateWindow();
}
