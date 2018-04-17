package com.nestis.adidas.fliteTrakr.model.strategy;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nestis.adidas.fliteTrakr.model.Airport;

public class BelowPriceStrategyTest {

	private BelowPriceStrategy belowStrategy;
	
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
		
		dub.addDestination(zgz, 450f);
		dub.addDestination(lhr, 350f);
		dub.addDestination(ams, 134.50f);
		
		zgz.addDestination(lhr, 84.50f);
		zgz.addDestination(fra, 24.50f);
		
		ams.addDestination(lhr, 55.60f);
		ams.addDestination(dub, 600f);
	}

	@Test
	public void shouldFindARoute() {
		belowStrategy = new BelowPriceStrategy("ZGZ", "DUB", 450f);
		String response = belowStrategy.getInfo(flights);
		assertTrue(response.equals("ZGZ-FRA-DUB-424.5 ZGZ-LHR-DUB-435.0"));
	}

	@Test
	public void shouldNotFindARouteAsked() {
		belowStrategy = new BelowPriceStrategy("ZGZ", "DUB", 45f);
		String response = belowStrategy.getInfo(flights);
		assertTrue(response.equals("No such connection found!"));
	}
	
	@Test
	public void shouldNotFindAConnection() {
		belowStrategy = new BelowPriceStrategy("ZGZ", "BOS", 45f);
		String response = belowStrategy.getInfo(flights);
		assertTrue(response.equals("No such connection found!"));
	}
}
