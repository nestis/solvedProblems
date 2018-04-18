package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Defines a Strategy that complies with the maximum price for a route.
 * #9: Find all conenctions from NUE to LHR below 170Euros!
 * @author nestis
 *
 */
public class BelowPriceStrategy extends PriceFlexibleRouteTemplate {
	
	/**
	 * Class constructor.
	 * @param origin Origin
	 * @param dest Destination
	 * @param maxPrice Maximum price for the route.
	 */
	public BelowPriceStrategy(String origin, String dest, Float maxPrice) {
		super(origin, dest, maxPrice);
	}

	@Override
	public String getResult(List<Route> routeFound) {
		
		if (routeFound.isEmpty()) {
			return "No such connection found!";
		} else {
			List<String> routes = routeFound.stream()
				.sorted((a,b) -> a.total.intValue() - b.total.intValue())
				.map(r -> String.join("-", r.route) + "-" + r.total)
				.collect(Collectors.toList());
			return String.join(" ", routes);
		}
	}

}
