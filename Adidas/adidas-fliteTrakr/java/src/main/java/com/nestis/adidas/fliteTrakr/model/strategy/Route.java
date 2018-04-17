package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that defines a Route between two airports.
 * @author nestis
 *
 */
public class Route {
	
	/**
	 * Route total price
	 */
	Float total;
	
	/**
	 * Route steps
	 */
	List<String> route;
	
	Route(Float total, String route) {
		this.total = total;
		this.route = new ArrayList<String>();
		this.route.add(route);
	}
	
	Route(Float total, List<String> route) {
		this.total = total;
		this.route = new ArrayList<String>();
		this.route.addAll(route);
	}
}
