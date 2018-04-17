package com.nestis.adidas.fliteTrakr.model.strategy;

import java.util.Map;

import com.nestis.adidas.fliteTrakr.model.Airport;

/**
 * Main QuestionStrategy class.
 * @author nestis
 *
 */
public abstract class QuestionStrategy {
	
	/**
	 * Resolves the specific question for the given flights.
	 * @param flights Collection of flights.
	 * @return String with the answer.
	 */
	public abstract String getInfo(Map<String, Airport> flights);
}
