package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.ArrayList;
import java.util.Map;

import com.nestis.adidas.fliteTrakr.model.Airport;

public class StopsStrategy extends FlexibleRouteTemplate {
	
	private Integer stops; 
	
	private StopsStrategyEnum type;
	
	public StopsStrategy(String origin, String dest, Integer stops, StopsStrategyEnum type) {
		super(origin, dest);
		this.stops = stops;
		this.type = type;
	}

	@Override
	public String getInfo(Map<String, Airport> flights) {
		// Create the cheapest route object
		routeFound = new ArrayList<Route>();
		
		getRoutes(flights);
		
		if (routeFound.isEmpty()) {
			return "No such connection found!";
		} else {
			long trips = routeFound.stream()
				.filter(r -> {
 					boolean valid = false;
					if (StopsStrategyEnum.EXACTLY == type) {
						valid = r.route.size() - 2 == stops;
					} else if (StopsStrategyEnum.MAXIMUM == type) {
						valid = r.route.size() - 2 <= stops;
					} else {
						valid = r.route.size() - 2 >= stops;
					}
					return valid;
				}).count();
			
			return String.valueOf(trips);
		}
	}

}
