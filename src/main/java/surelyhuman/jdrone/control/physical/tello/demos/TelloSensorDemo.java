package main.java.surelyhuman.jdrone.control.physical.tello.demos;

import java.io.IOException;

import main.java.surelyhuman.jdrone.control.physical.tello.TelloDrone;

public class TelloSensorDemo {

	private static void sensors() throws IOException, InterruptedException {
		TelloDrone tello = new TelloDrone();
		tello.activateSDK();
		System.out.println(tello.getBattery());
		System.out.println(tello.getHeight());
		System.out.println(tello.getSpeed());
		System.out.println(tello.getTemp());
		System.out.println(tello.getBarometer());
		System.out.println(tello.getAttitudePitch());
		System.out.println(tello.getAttitudeRoll());
		System.out.println(tello.getAttitudeYaw());
		System.out.println(tello.getAccelerationX());
		System.out.println(tello.getAccelerationY());
		System.out.println(tello.getAccelerationZ());
		System.out.println(tello.getTOF());
		System.out.println(tello.getFlightTime());
		System.out.println(tello.getWIFI());
		System.out.println(tello.getVersionSDK());
		System.out.println(tello.getSerialNumber());
		tello.end();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		sensors();
		System.exit(0);
	}
}
