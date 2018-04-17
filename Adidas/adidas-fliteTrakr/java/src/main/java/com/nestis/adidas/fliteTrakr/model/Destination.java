package com.nestis.adidas.fliteTrakr.model;

/**
 * Class that defines a destination.
 * @author nestis
 *
 */
public class Destination {

	/**
	 * Destionatio airprice.
	 */
	private Airport destination;
	
	/**
	 * Ticket price.
	 */
	private Float price;

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Destination(Airport destination, Float price) {
		this.destination = destination;
		this.price = price;
	}
	
}
