package de.mosbach.lan.smarthome.houseComponents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class InsideTemperature {
	private String roomName;
	
	//start info
    private long startTime;
    public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public double getStartTemp() {
		return startTemp;
	}

	public void setStartTemp(double startTemp) {
		this.startTemp = startTemp;
	}

	public double getGoalTemp() {
		return goalTemp;
	}

	public void setGoalTemp(double goalTemp) {
		this.goalTemp = goalTemp;
	}

	public double getDeltaTemp() {
		return deltaTemp;
	}

	public void setDeltaTemp(double deltaTemp) {
		this.deltaTemp = deltaTemp;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public double getNeededTime() {
		return neededTime;
	}

	public void setNeededTime(double neededTime) {
		this.neededTime = neededTime;
	}

	public double getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(double currentTemp) {
		this.currentTemp = currentTemp;
	}

	public long getTimePassed() {
		return timePassed;
	}

	public void setTimePassed(long timePassed) {
		this.timePassed = timePassed;
	}

	private double startTemp;
    private double goalTemp;
    //parameters for formula
    private double deltaTemp;
    private double a;
    private int sign;
    private double neededTime;
    //updated every call
    private double currentTemp;
    long timePassed;

    public InsideTemperature() {
    	this("A1");
	}
    
    public InsideTemperature(String roomName){
    	this(20.0,20.0, roomName);
    }
    
    public InsideTemperature(double startTemperature, double goalTemperature, String roomName) {
        this.roomName = roomName;
    	startTime = System.currentTimeMillis();
        this.startTemp = startTemperature;
        this.goalTemp = goalTemperature;
        updateParameters();
    }

    public void setNewGoalTemperature(double goalTemperature) {
        calculateCurrentTemperature();
        startTime = System.currentTimeMillis();
        this.startTemp = currentTemp;
        this.goalTemp = goalTemperature;
        updateParameters();
    }

    public int getInsideTemperature() {
        calculateCurrentTemperature();
        //for debugging
        /*/todo: hide
        System.out.println("Temperature after " + timePassed / 1000 + " sec: " + currentTemp);
		//*/
		
        return (int) Math.round(currentTemp);
    }

    private void updateParameters() {
        deltaTemp = (goalTemp - startTemp);
        //unit of time: milliseconds
        neededTime = 2000.0 * deltaTemp;
        if(neededTime == 0){
        	a = 0;
        } else {
			a = ((deltaTemp*deltaTemp) / neededTime);
		}
        if (a < 0) {
            a = Math.abs(a);
            sign = -1;
        } else {
            sign = 1;
        }
    }

    private void calculateCurrentTemperature() {
        timePassed = (System.currentTimeMillis()) - startTime;
        double currentTemperature = sign * Math.sqrt(a * timePassed) + startTemp;
        if ((sign * currentTemperature) > (sign * goalTemp)) {
            currentTemp = goalTemp;
        } else {
            currentTemp = currentTemperature;
        }
    }
    
    @Column
    @Id
    public String getRoomName(){
    	return this.roomName;
    }
    
    public void setRoomName(String roomName){
    	this.roomName = roomName;
    }
}
