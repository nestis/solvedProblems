package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nestis.adidas.fliteTrakr.model.Airport;

public class BelowPriceStrategy extends PriceFlexibleRouteTemplate {
	
	public BelowPriceStrategy(String origin, String dest, Float maxPrice) {
		super(origin, dest, maxPrice);
	}

	@Override
	public String getInfo(Map<String, Airport> flights) {
		// Create the cheapest route object
		routeFound = new ArrayList<Route>();
		
		getRoutes(flights);
		
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
