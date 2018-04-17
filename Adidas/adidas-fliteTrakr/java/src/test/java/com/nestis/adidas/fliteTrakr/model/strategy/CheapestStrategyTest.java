package com.nestis.adidas.fliteTrakr.model.strategy;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nestis.adidas.fliteTrakr.model.Airport;

public class CheapestStrategyTest {
	
	private CheapestStrategy cheapestStrategy;
	
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
		
		zgz.addDestination(lhr, 24.50f);
		
		ams.addDestination(lhr, 55.60f);
		ams.addDestination(dub, 600f);
	}
	
	@Test
	public void shouldFindTheCheapestRoute() {
		cheapestStrategy = new CheapestStrategy("LHR", "AMS");
		String response = cheapestStrategy.getInfo(flights);
		assertTrue(response.equals("LHR-FRA-AMS-115.0"));
	}
	
	@Test
	public void shouldFindTheCheapestRouteWhenOnlyOneFlightFromOrigin() {
		cheapestStrategy = new CheapestStrategy("ZGZ", "DUB");
		String response = cheapestStrategy.getInfo(flights);
		assertTrue(response.equals("ZGZ-LHR-DUB-375.0"));
	}
	
	@Test
	public void shouldFindTheCheapestRouteToSameCity() {
		cheapestStrategy = new CheapestStrategy("FRA", "FRA");
		String response = cheapestStrategy.getInfo(flights);
		assertTrue(response.equals("FRA-AMS-LHR-FRA-170.6"));
	}
	
	@Test
	public void shouldNotFindAConnection() {
		cheapestStrategy = new CheapestStrategy("ZGZ", "BOS");
		String response = cheapestStrategy.getInfo(flights);
		assertTrue(response.equals("No such connection found!"));
	}
}
