package com.nestis.adidas.fliteTrakr.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that defines an Airport.
 * @author nestis
 *
 */
public class Airport {

	/**
	 * Airport name;
	 */
	private String origin;

	/**
	 * List of possible destinations. Using a list let us add the same destination twice
	 */
	private List<Destination> destinations;

	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void addDestination(Airport destination, Float price) {
		destinations.add(new Destination(destination, price));
	}
	
	public List<Destination> getDestinations() {
		return destinations;
	}

	public Airport() {
		destinations = new ArrayList<Destination>();
	}
	
	public Airport(String origin) {
		destinations = new ArrayList<Destination>();
		this.origin = origin;
	}
	
}
