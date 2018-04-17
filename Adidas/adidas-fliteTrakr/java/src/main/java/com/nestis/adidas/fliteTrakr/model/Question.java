package com.nestis.adidas.fliteTrakr.model;

import java.util.Map;

import com.nestis.adidas.fliteTrakr.model.strategy.QuestionStrategy;

/**
 * Question class. Defines a Question to be answered regarding the flights.
 * @author nestis
 *
 */
public class Question {

	/**
	 * Strategy to answer the question.
	 */
	private QuestionStrategy questionStrategy;
	
	public Question(QuestionStrategy questionStrategy) {
		this.questionStrategy = questionStrategy;
	}
	
	/**
	 * Gets the answer for a question and the given collection of flights.
	 * @param flights Collection of fligths.
	 * @return String containing the answer.
	 */
	public String getInfo(Map<String, Airport>  flights) {
		return questionStrategy.getInfo(flights);
	}
	
}
