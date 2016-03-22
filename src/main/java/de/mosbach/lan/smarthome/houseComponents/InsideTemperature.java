package de.mosbach.lan.smarthome.houseComponents;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class InsideTemperature {
	private String roomName;
	
	//start info
    private long startTime;
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
}
