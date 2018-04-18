package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.List;

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
	public String getResult(List<Route> routeFound) {
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