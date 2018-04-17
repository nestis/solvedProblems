package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.List;
import java.util.Map;

import com.nestis.adidas.fliteTrakr.model.Airport;
import com.nestis.adidas.fliteTrakr.model.Destination;

/**
 * FlexibleRouteTemplate.
 * Defines the base behaviour to find all the possible routes between two airports.
 * @author nestis
 *
 */
public abstract class FlexibleRouteTemplate extends QuestionStrategy {

	protected String origin;
	
	protected String dest;
	
	/**
	 * List that will contain all the routes found
	 */
	protected List<Route> routeFound;
	
	/**
	 * Class constructor.
	 * @param origin Origin airport.
	 * @param dest Destination airport.
	 */
	public FlexibleRouteTemplate(String origin, String dest) {
		this.origin = origin;
		this.dest = dest;
	}
	
	/**
	 * Get the routes between origin and destination for the given flights.
	 * @param flights Maps containing destionation name and airport object.
	 */
	protected void getRoutes(Map<String, Airport> flights) {
		// Get the origin airport
		Airport originAirport = flights.get(origin);
		
		if (originAirport != null) {
			// Iterate over its destinations to find the cheapest route
			originAirport.getDestinations().forEach(d -> {
				// Set the origin as the first item in Route object
				Route route = new Route(0f, origin);
				route.total += d.getPrice();
				
				// If the this step is the destination, add it to the array
				route.route.add(d.getDestination().getOrigin());
				if (d.getDestination().getOrigin().equals(dest)) {
	 				routeFound.add(new Route(route.total, route.route));
				}
				// Iterate over its children to find another possible route
				iterateOverChild(d, route);
			});
			
		}
	}
	
	/**
	 * Iterate over all the the destinations of a destination.
	 * We iterate over all of them because there can be more than one way to get to the destination.
	 * @param destination Destination to iterate over
	 * @param route Route taken to get here.
	 * @return Boolean. If the destination is found or not.
	 */
 	protected void iterateOverChild(Destination destination, Route route) {
 		// Iterate over all the destinations
 		List<Destination> destinations = destination.getDestination().getDestinations();
 		
 		// Iterate over the destinations
 		for(Destination d : destinations) {
 			// If the current destination is the one we are looking for...
 			if (d.getDestination().getOrigin().equalsIgnoreCase(dest)) {
 				// Add its value and save the route into routeFound array
 				route.total += d.getPrice();
 				route.route.add(d.getDestination().getOrigin());
 				routeFound.add(new Route(route.total, route.route));
 				
 				// Once the route has been added to the array, we can remove this step from the route object.
 				// because the iteration will continue when we get back to the previous caller.
 				// The flow will return to the previous call so we have to delete this iteration.
 				// The parent node will continue the execution flow with the next child (if any)
				route.total -= d.getPrice();
				route.route.remove(route.route.size() - 1);
					
			// If the current route is not the destination and it hasn't been previously visited (we wouldn't want to fly to the same places over and over again...)
 			} else if (!route.route.contains(d.getDestination().getOrigin())){
 				// Add its price and the location and iterate over its children destinations
 				route.total += d.getPrice();
 				route.route.add(d.getDestination().getOrigin());
 				iterateOverChild(d, route);
 				
 				// Once we have iterate over the children, we can delete this route.
 				// Same reasoning as above
				route.total -= d.getPrice();
				route.route.remove(route.route.size() - 1);
 			}
 		}
 	}
}
