package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.List;

/**
 * Defines a strategy to get the number of routes that comply with the number of stops permitted.
 * #7: How many different connections with maximum 3 stops exists between NUE and FRA?
 * #8: How mand different connections with exactly 1 stop exists between LHR and AMS?
 * @author nestis
 *
 */
public class StopsStrategy extends FlexibleRouteTemplate {
	
	/**
	 * Stops number.
	 */
	private Integer stops; 
	
	/**
	 * Boolean logic of the stops number. Maximum, minimum or exactly.
	 */
	private StopsStrategyEnum type;
	
	public StopsStrategy(String origin, String dest, Integer stops, StopsStrategyEnum type) {
		super(origin, dest);
		this.stops = stops;
		this.type = type;
	}

	@Override
	public String getResult(List<Route> routeFound) {
		if (routeFound.isEmpty()) {
			return "No such connection found!";
		} else {
			long trips = routeFound.stream()
				.filter(r -> {
 					boolean valid = false;
 					// We always substract 2, because origin and destination doens't count as stop overs.
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
