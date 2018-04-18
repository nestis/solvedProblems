package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.List;

import com.nestis.adidas.fliteTrakr.model.Destination;

/**
 * Defines the base behaviour to find all the possible routes between two airports in the given budget.
 * Inherits all its behaviour from FlexibleRouteTemplate, including the template method.
 * This class just override the routes obtaining method.
 * @author nestis
 *
 */
public abstract class PriceFlexibleRouteTemplate extends FlexibleRouteTemplate {
	
	protected Float price;
	
	public PriceFlexibleRouteTemplate(String origin, String dest, Float price) {
		super(origin, dest);
		this.price = price;
	}
	
	protected abstract String getResult(List<Route> routeFound);
	
	/**
	 * Iterate over all the the destinations of a destination.
	 * We iterate over all of them because there can be more than one way to get to the destination.
	 * @param destination Destination to iterate over
	 * @param route Route taken to get here.
	 * @return Boolean. If the destination is found or not.
	 */
	@Override
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
 				
 				// If the actual route price is lesser than the objective price, add the route (it's a valid route) to the array and iterate again
 				// We iterate again cuz it's possible to come to the destination another time. The aim is to travel within the budget, it's not about flight efficiency
 				// Example => ZGZ-LHR-DUB-LHR-DUB
 				if (route.total <= price) {
 	 				routeFound.add(new Route(route.total, route.route));
 					iterateOverChild(d, route);
 				}
 				// At this point we have already iterate over the destination children looking for another way to the destionation
 				// So we can remove the destination
				route.total -= d.getPrice();
				route.route.remove(route.route.size() - 1);
					
			// If the current route is not the destination and it hasn't been previously visited (we wouldn't want to fly to the same places over and over again...)
 			} else {
 				// Add its price and the location and iterate over its children destinations
 				route.total += d.getPrice();
 				route.route.add(d.getDestination().getOrigin());
 				
 				if (route.total < price) {
 					iterateOverChild(d, route);
 				}
				route.total -= d.getPrice();
				route.route.remove(route.route.size() - 1);
 			}
 		}
 	}
}
