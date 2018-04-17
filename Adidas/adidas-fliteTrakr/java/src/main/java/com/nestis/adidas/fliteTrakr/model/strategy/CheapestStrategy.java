package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.ArrayList;
import java.util.Map;

import com.nestis.adidas.fliteTrakr.model.Airport;

/**
 * Cheapest Strategy. Gets the cheapest route between to cities.
 * #4: What is the cheapest connection from NUE to AMS?
 * @author nestis
 *
 */
public class CheapestStrategy extends FlexibleRouteTemplate {

	public CheapestStrategy(String origin, String dest) {
		super(origin, dest);
	}

	@Override
	public String getInfo(Map<String, Airport> flights) {
		// Create the cheapest route object
		routeFound = new ArrayList<Route>();
		
		getRoutes(flights);
		
		if (routeFound.isEmpty()) {
			return "No such connection found!";
		} else {
			return routeFound.stream()
				.sorted((a,b) -> a.total.intValue() - b.total.intValue())
				.map(r -> String.join("-", r.route) + "-" + r.total)
				.findFirst()
				.get();
		}
	}
}