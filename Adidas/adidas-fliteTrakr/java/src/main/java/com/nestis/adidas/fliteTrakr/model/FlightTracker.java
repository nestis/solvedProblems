package com.nestis.adidas.fliteTrakr.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FlightTracker class.
 * Base object thet will contain all the info obtained from the input file.
 * @author nestis
 *
 */
public class FlightTracker {
	
	/**
	 * Collection of route extracted from the input file.
	 * The key is the name of the airport, and the value is the airport itself. 
	 * It's a Map and not a Set, because this way it eases the logic later on the strategies.
	 * Example: 
	 *	 Airport originAirport = flights.get(origin);
	 * The code above is easier than iterate over the Set or converting it to a list. Maybe an overkill :/
	 */
	private Map<String, Airport> routes;
	 
	/**
	 * List of questions extracted from the input file.
	 */
	private List<Question> questions;
	
	public Map<String, Airport> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<String, Airport> routes) {
		this.routes = routes;
	}

	public List<Question> getQuestions() {
		if (questions == null) {
			questions = new ArrayList<Question>();
		}
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public FlightTracker() { 
	}

	/**
	 * Solves the questions. Each question answers will be printed in console.
	 */
	public void solveQuestions() {
		questions.forEach(q -> {
			System.out.println(q.getInfo(routes));
		});
	}
}
