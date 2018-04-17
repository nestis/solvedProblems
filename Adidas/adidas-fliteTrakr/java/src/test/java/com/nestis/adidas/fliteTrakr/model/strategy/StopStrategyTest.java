package com.nestis.adidas.fliteTrakr.model.strategy;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nestis.adidas.fliteTrakr.model.Airport;

public class StopStrategyTest {
	
	private StopsStrategy stopsStrategy;
	
	private Map<String, Airport> flights;

	@BeforeEach
	public void setUp() throws Exception {
		flights = new HashMap<String, Airport>();
		Airport fra = new Airport("FRA");
		flights.put("FRA", fra);
		Airport dub = new Airport("DUB");
		flights.put("DUB", dub);
		Airport lhr = new Airport("LHR");
		flights.put("LHR", lhr);
		Airport zgz = new Airport("ZGZ");
		flights.put("ZGZ", zgz);
		Airport ams = new Airport("AMS");
		flights.put("AMS", ams);
		Airport bos = new Airport("BOS");
		flights.put("BOS", bos);
		
		fra.addDestination(zgz, 100f);
		fra.addDestination(dub, 400f);
		fra.addDestination(ams, 45f);
		
		bos.addDestination(lhr, 100f);
		
		lhr.addDestination(zgz, 80.50f);
		lhr.addDestination(fra, 70f);
		lhr.addDestination(dub, 350.50f);
		lhr.addDestination(dub, 600f);
		
		dub.addDestination(zgz, 450f);
		dub.addDestination(lhr, 350f);
		dub.addDestination(ams, 134.45f);
		
		zgz.addDestination(lhr, 84.50f);
		zgz.addDestination(fra, 24.50f);
		
		ams.addDestination(lhr, 55.60f);
		ams.addDestination(dub, 600f);
	}
	
	@Test
	public void shouldFindAMaxRoute() {
		stopsStrategy = new StopsStrategy("ZGZ", "DUB", 1, StopsStrategyEnum.MAXIMUM);
		String response = stopsStrategy.getInfo(flights);
		assertTrue(response.equals("3"));
	}
	
	@Test
	public void shouldFindAMinRoute() {
		stopsStrategy = new StopsStrategy("ZGZ", "DUB", 2, StopsStrategyEnum.MINIMUM);
		String response = stopsStrategy.getInfo(flights);
		assertTrue(response.equals("5"));
	}
	
	@Test
	public void shouldFindAnExactRoute() {
		stopsStrategy = new StopsStrategy("ZGZ", "DUB", 3, StopsStrategyEnum.EXACTLY);
		String response = stopsStrategy.getInfo(flights);
		assertTrue(response.equals("3"));
	}
	
	@Test
	public void shouldNotFindAnything() {
		stopsStrategy = new StopsStrategy("ZGZ", "AMS", 4, StopsStrategyEnum.EXACTLY);
		String response = stopsStrategy.getInfo(flights);
		assertTrue(response.equals("0"));
	}
	
	@Test
	public void shouldNotFindAConnection() {
		stopsStrategy = new StopsStrategy("ZGZ", "BOS", 2, StopsStrategyEnum.EXACTLY);
		String response = stopsStrategy.getInfo(flights);
		assertTrue(response.equals("No such connection found!"));
	}
}
