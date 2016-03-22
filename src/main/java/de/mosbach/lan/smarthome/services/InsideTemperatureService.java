package de.mosbach.lan.smarthome.services;

import javax.jws.WebService;

@WebService(serviceName = "insideTemperatureService", name="insideTemperatureService", portName="insideTemperatureService")
public class InsideTemperatureService {
	//startinfo
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

    public InsideTemperatureService(){
    	this(20.0,20.0);
    }
    
    public InsideTemperatureService(double startTemperature, double goalTemperature) {
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
        //unit of time: millisec
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
