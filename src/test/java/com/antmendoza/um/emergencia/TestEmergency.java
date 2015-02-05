package com.antmendoza.um.emergencia;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.antmendoza.eventlistener.NodeEventListener;
import com.antmendoza.um.model.Fire;
import com.antmendoza.um.model.Room;
import com.antmendoza.um.model.SensorReading;
import com.antmendoza.um.model.Sprinkler;

public class TestEmergency {

	private KieSession ksession;

	private Logger log = LoggerFactory.getLogger(TestEmergency.class);

	private NodeEventListener nodeEventListener = null;

	@Before
	public void createKSession() {
		nodeEventListener = new NodeEventListener();

		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.getKieClasspathContainer();
		ksession = kc.newKieSession("ks_umu_emergencia_test");
		ksession.addEventListener(nodeEventListener);

		Logger logDrools = LoggerFactory.getLogger("RulesEmergency");
		ksession.setGlobal("logDrools", logDrools);

	}

	@Test
	public void testTwoSprinklerOnAndOff() {

		printStartTest("testTwoSprinklerOnAndOff");

		String[] names = new String[] { "kitchen", "bedroom", "office",
				"livingroom" };
		Map<String, Room> name2room = new HashMap<String, Room>();
		for (String name : names) {
			Room room = new Room(name);
			name2room.put(name, room);
			ksession.insert(room);
			Sprinkler sprinkler = new Sprinkler(room);
			ksession.insert(sprinkler);
		}
		ksession.fireAllRules();

		Fire kitchenFire = new Fire(name2room.get("kitchen"));
		Fire officeFire = new Fire(name2room.get("office"));
		FactHandle kitchenFireHandle = ksession.insert(kitchenFire);
		FactHandle officeFireHandle = ksession.insert(officeFire);
		ksession.fireAllRules();

		// The number of Sprinkler on status 'on' is two
		int numSprinklerOnStateOn = getNumSprinklerOnStatusOn();
		Assert.assertSame(2, numSprinklerOnStateOn);

		ksession.delete(kitchenFireHandle);
		ksession.delete(officeFireHandle);
		ksession.fireAllRules();

		// The number of Sprinkler on status 'on' is cero
		numSprinklerOnStateOn = getNumSprinklerOnStatusOn();
		Assert.assertSame(0, numSprinklerOnStateOn);

		printEndTest("testTwoSprinklerOnAndOff");

	}

	@Test
	public void testInsertFireFromAvgTemperatureOfSignal() {

		try {
			printStartTest("testAvgTemperatureOfSignal");

			String[] names = new String[] { "kitchen", "bedroom", "office",
					"livingroom" };
			Map<String, Room> name2room = new HashMap<String, Room>();
			for (String name : names) {
				Room room = new Room(name);
				name2room.put(name, room);
				ksession.insert(room);
				Sprinkler sprinkler = new Sprinkler(room);
				ksession.insert(sprinkler);
			}

			Room kitchenRoom = name2room.get("kitchen");

			SensorReading kitchenSensor1 = new SensorReading(kitchenRoom, 56);

			SensorReading kitchenSensor2 = new SensorReading(kitchenRoom, 55);

			SensorReading kitchenSensor3 = new SensorReading(kitchenRoom, 57);

			SensorReading kitchenSensor4 = new SensorReading(kitchenRoom, 57);

			ksession.insert(kitchenSensor1);
			ksession.insert(kitchenSensor2);
			ksession.insert(kitchenSensor3);
			ksession.insert(kitchenSensor4);

			ksession.fireAllRules();

			// Comprobamos que el fuego existe.
			Fire fireInSystem = getFireFromRoom(kitchenRoom);
			Assert.assertNotNull(fireInSystem);

			// Comprobamos que se ha encendido el extintor
			int numSprinklerOnStateOn = getNumSprinklerOnStatusOn();
			Assert.assertSame(1, numSprinklerOnStateOn);

			printEndTest("testAvgTemperatureOfSignal");

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testInsertFireFromAvgTemperatureOfSignal2() {

		try {
			printStartTest("testAvgTemperatureOfSignal");

			String[] names = new String[] { "kitchen", "bedroom", "office",
					"livingroom" };
			Map<String, Room> name2room = new HashMap<String, Room>();
			for (String name : names) {
				Room room = new Room(name);
				name2room.put(name, room);
				ksession.insert(room);
				Sprinkler sprinkler = new Sprinkler(room);
				ksession.insert(sprinkler);
			}

			Room kitchenRoom = name2room.get("kitchen");

			SensorReading kitchenSensor1 = new SensorReading(kitchenRoom, 56);

			SensorReading kitchenSensor2 = new SensorReading(kitchenRoom, 55);

			SensorReading kitchenSensor3 = new SensorReading(kitchenRoom, 57);

			SensorReading kitchenSensor4 = new SensorReading(kitchenRoom, 57);

			ksession.insert(kitchenSensor1);
			ksession.insert(kitchenSensor2);
			ksession.insert(kitchenSensor3);
			ksession.insert(kitchenSensor4);

			ksession.fireAllRules();

			// Comprobamos que el fuego existe.
			Fire fireInSystem = getFireFromRoom(kitchenRoom);
			Assert.assertNotNull(fireInSystem);

			// Comprobamos que se ha encendido el extintor
			int numSprinklerOnStateOn = getNumSprinklerOnStatusOn();
			Assert.assertSame(1, numSprinklerOnStateOn);

			ksession.signalEvent("emergencyActive", null);

			ksession.fireAllRules();

			printEndTest("testAvgTemperatureOfSignal");

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	private int getNumSprinklerOnStatusOn() {
		QueryResults results = ksession.getQueryResults(
				"Sprinkler on state on", new Object[] { true });
		return results.size();
	}

	private Fire getFireFromRoom(Room room) throws Exception {
		QueryResults results = ksession.getQueryResults("Get Fire from room",
				new Object[] { room });

		int resultSize = results.size();

		int resultSizeExpected = 1;
		if (results.size() == resultSizeExpected) {
			return (Fire) results.iterator().next().get("$fire");

		} else {
			throw new Exception("Unexpected results size, actual size = ["
					+ resultSize + "], must be [" + resultSizeExpected + "]");
		}

	}

	private void printStartTest(String methodName) {
		System.out
				.print("------------------------------------------------------------------------------------------");
		System.out
				.println("------------------------------------------------------------------------------------------");
		log.debug("Start test " + methodName);
	}

	private void printEndTest(String methodName) {

		log.debug("End test " + methodName);
		System.out.println("");

	}

}
