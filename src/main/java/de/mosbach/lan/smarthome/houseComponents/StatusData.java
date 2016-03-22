package de.mosbach.lan.smarthome.houseComponents;

public class StatusData implements IStatusData {

	private int insideTempRequirement = 20;
	private int internalTemperature = 20;
	private int outsideTemperature = 10;
	private int stateAirConditioner = 0;
	private int stateHeater = 0;
	private int stateWindow = 0;

	@Override
	public int getInternalTemperature() {
		return this.internalTemperature;
	}

	public int getInsideTempRequirement() {
		return this.insideTempRequirement;
	}

	@Override
	public int getOutsideTemperature() {
		return this.outsideTemperature;
	}

	@Override
	public int getStateAirConditioner() {
		return this.stateAirConditioner;
	}

	@Override
	public int getStateHeater() {
		return this.stateHeater;
	}

	@Override
	public int getStateWindow() {
		return this.stateWindow;
	}

	@Override
	public void setInsideTempRequirement(int value) {
		this.insideTempRequirement = value;
	}

	public void setInternalTemperature(int value) {
		this.internalTemperature = value;
	}

	public void setOutsideTemperature(int value) {
		this.outsideTemperature = value;
	}

	public void setStateAirConditioner(int value) {
		this.stateAirConditioner = value;
	}

	public void setStateHeater(int value) {
		this.stateHeater = value;
	}

	public void setStateWindow(int value) {
		this.stateWindow = value;
	}
}
