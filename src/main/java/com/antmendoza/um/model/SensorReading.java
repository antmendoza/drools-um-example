package com.antmendoza.um.model;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;


@Role(Type.EVENT)
public class SensorReading {
	
	private Room room;
	private double temperature;
	
	
	public SensorReading(Room room, double temperature) {
		this.room = room;
		this.temperature = temperature;
	}


	public double getTemperature() {
		return temperature;
	}

	public Room getRoom() {
		return room;
	}

}
