package com.nestis.adidas.fliteTrakr.model.strategy;

/**
 * Enum for the different types of StopStrategy types.
 * @author nestis
 *
 */
public enum StopsStrategyEnum {

	EXACTLY("exactly"),
	MINIMUM("minimum"),
	MAXIMUM("maximum");
	
	private String value;
	
	private StopsStrategyEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
