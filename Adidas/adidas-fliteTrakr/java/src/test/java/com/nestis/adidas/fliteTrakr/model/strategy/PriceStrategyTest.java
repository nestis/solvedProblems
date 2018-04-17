package com.nestis.adidas.fliteTrakr.model.strategy;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nestis.adidas.fliteTrakr.model.Airport;

public class PriceStrategyTest {

	private PriceStrategy priceStrategy;
	
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
		
		fra.addDestination(zgz, 100f);
		fra.addDestination(dub, 400f);
		fra.addDestination(ams, 45f);
		
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
	public void shouldCalculateThePrice() throws Exception {
		priceStrategy = new PriceStrategy("ZGZ-LHR-FRA");
		String response = priceStrategy.getInfo(flights);
		assertTrue(response.equals("94.5"));
	}
	
	@Test
	public void shouldCalculateTheCheapestPriceWhenMoreThanOneFlight() throws Exception {
		priceStrategy = new PriceStrategy("ZGZ-LHR-DUB");
		String response = priceStrategy.getInfo(flights);
		assertTrue(response.equals("375.0"));
	}
	
	@Test
	public void shouldNotFindARoute() throws Exception {
		priceStrategy = new PriceStrategy("ZGZ-LHR-DUB-FRA");
		String response = priceStrategy.getInfo(flights);
		assertTrue(response.equals("No such route found!"));
	}
}
