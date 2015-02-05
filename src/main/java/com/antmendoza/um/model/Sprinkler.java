package com.antmendoza.um.model;

public class Sprinkler {
	private Room room;
	private boolean on;

	public Sprinkler(Room room) {
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}
}