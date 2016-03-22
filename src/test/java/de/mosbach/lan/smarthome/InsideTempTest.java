package de.mosbach.lan.smarthome;

import de.mosbach.lan.smarthome.houseComponents.InsideTemperature;

public class InsideTempTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InsideTemperature test = new InsideTemperature("A1");
		
		test.setNewGoalTemperature(30);
		System.out.println("Innentemp: "+test.getInsideTemperature());


		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Innentemp: "+test.getInsideTemperature());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Innentemp: "+test.getInsideTemperature());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Innentemp: "+test.getInsideTemperature());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Innentemp: "+test.getInsideTemperature());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Innentemp: "+test.getInsideTemperature());
		

	}

}
