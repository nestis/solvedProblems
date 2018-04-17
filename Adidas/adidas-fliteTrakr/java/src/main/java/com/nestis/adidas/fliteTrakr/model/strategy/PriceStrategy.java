package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.Map;

import com.nestis.adidas.fliteTrakr.model.Airport;

/**
 * PriceStrategy. Calculates the price for a given flight route, if it exists.
 * Example: #1: What is the price of the connection NUE-FRA-LHR?
 * 
 * @author nestis
 *
 */
public class PriceStrategy extends QuestionStrategy {

	/**
	 * Flight path to take.
	 */
	private String flightPath;

	public PriceStrategy(String flightPath) {
		this.flightPath = flightPath;
	}

	@Override
	public String getInfo(Map<String, Airport> flights) {
		Float total = 0f;
		
		String[] route = flightPath.split("-");
		for(int i = 0, l = route.length; i < l -1; i++) {
			// Get the origin and destination of the route step
			String origin = route[i];
			String dest = route[i + 1];
			
			// Get the origin airport
			Airport originAirport = flights.get(origin);
			if (originAirport != null) {
				
				// For every destination, get the ones that matches the destination, ordered by price asc, get only its price an get the first item
				Float price = originAirport.getDestinations().stream()
					.filter(d -> d.getDestination().getOrigin().equalsIgnoreCase(dest))
					.sorted((a,b) -> a.getPrice().intValue() - b.getPrice().intValue())
					.map(d -> d.getPrice())
					.findFirst()
					.orElse(null);
				
				// If a price has been found, add it to the total
				// Otherwise, set total to zero and exit the for loop.
				if (price != null) {
					total += price;
				} else {
					total = 0f;
					break;
				}
			} else {
				total = 0f;
				break;
			}
		}
		
		return total > 0 ? total.toString() : "No such route found!";
	}
}
