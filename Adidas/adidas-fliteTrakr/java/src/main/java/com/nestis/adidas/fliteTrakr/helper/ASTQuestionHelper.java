package com.nestis.adidas.fliteTrakr.helper;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nestis.adidas.fliteTrakr.model.strategy.BelowPriceStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.CheapestStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.PriceStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.QuestionStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.StopsStrategy;
import com.nestis.adidas.fliteTrakr.model.strategy.StopsStrategyEnum;

/**
 * Simple Abstract Syntax Tree for Questions.
 * @author nestis
 *
 */
public class ASTQuestionHelper {
	
	private static final String WHITE_SPACE = " ";

	/**
	 * Returns a QuestionStrategy to answer the question by examining it.
	 * @param question String. Question to answer.
	 * @return QuestionStrategy to answer the question. 
	 * @throws Exception If anything goes south...
	 */
	public static QuestionStrategy getQuestionStrategy(String question) throws Exception {
		QuestionStrategy qStrategy = null;
		
		// Normalize question string
		question = question.toLowerCase().replace("?", "");
		
		// Get the string tokens
		List<String> tokens = Arrays.asList(question.split(WHITE_SPACE));
		
		// Check the first word in the question, and act accordingly to it
		switch(tokens.get(0)) {
			case ASTQuestionDictionary.WHAT: 
				qStrategy = findWhat(tokens);
				break;
			case ASTQuestionDictionary.HOW: 
				qStrategy = findDifferentConnections(tokens);
				break;
			case ASTQuestionDictionary.FIND: 
				qStrategy = findConnectionBelow(tokens);
				break;
			default: 
				throw new Exception("Cannae find a proper strategy for question => " + question);
		}
		
		return qStrategy;
	}
	
	/**
	 * Examines the What path.
	 * What is the price of the connection NUE-FRA-LHR?
	 * What is the cheapest connection from NUE to AMS?
	 * @param tokens 
	 * @return QuestionStrategy. Either CheapestStrategy or PriceStrategy.
	 */
	private static QuestionStrategy findWhat(List<String> tokens) {
		
		// What is the cheapest connection from NUE to AMS?
		if (tokens.indexOf(ASTQuestionDictionary.CHEAPEST) > 0) {
			// Get the TO index to obtain the origin and destination.
			Integer toIndex = tokens.lastIndexOf(ASTQuestionDictionary.TO);
			String origin = tokens.get(toIndex - 1);
			String dest = tokens.get(toIndex + 1);
			
;			return new CheapestStrategy(origin, dest);
			
		// What is the price of the connection NUE-FRA-LHR?
		} else {
			// Get the last item of the token as it will be the flight path
			String flightPath = tokens.get(tokens.size() - 1);
			return new PriceStrategy(flightPath);
		}
	}
	
	/**
	 * Examines the Find path.
	 * Find all conenctions from NUE to LHR below 170Euros!
	 * @param tokens 
	 * @return BelowPriceStrategy.
	 */
	private static QuestionStrategy findConnectionBelow(List<String> tokens) {
		// Get the TO index to obtain both origin ans destination.
		Integer toIndex = tokens.lastIndexOf(ASTQuestionDictionary.TO);
		String origin = tokens.get(toIndex - 1);
		String dest = tokens.get(toIndex + 1);

		// Get the last item of the token as it will be the price
		String price = tokens.get(tokens.size() - 1);
		// Get the numeric value from the price token
		// ^\d+[\. || ,]*\d+ => start of String + numbers + (. or ,) + numbers => 170: 170.50: 2133,32 
		Matcher matcher = Pattern.compile("^\\d+[\\. || ,]*\\d+").matcher(price);
		matcher.find();
		Float priceFloat = Float.valueOf(matcher.group());
		
		return new BelowPriceStrategy(origin, dest, priceFloat);
	}
	
	/**
	 * Examines the How path
	 * How many different connections with maximum 3 stops exists between NUE and FRA?
	 * @param tokens
	 * @return StopsStrategy
	 */
	private static QuestionStrategy findDifferentConnections(List<String> tokens) {
		Integer withIndex = tokens.lastIndexOf(ASTQuestionDictionary.WITH);
		// Get the type of search. maximum, minimun or exactly 
		String type = tokens.get(withIndex + 1);
		
		StopsStrategyEnum stopsType = null;
		switch(type) {
			case ASTQuestionDictionary.EXACTLY: stopsType =  StopsStrategyEnum.EXACTLY; break;
			case ASTQuestionDictionary.MINIMUM: stopsType =  StopsStrategyEnum.MINIMUM; break;
			case ASTQuestionDictionary.MAXIMUM: stopsType =  StopsStrategyEnum.MAXIMUM; break;
		}
		
		// Get the number of stops
		Integer stopsNumber = Integer.parseInt(tokens.get(withIndex + 2));
		
		// Get the AND token to obtain origin and destination
		Integer andIndex = tokens.indexOf(ASTQuestionDictionary.AND);
		String origin = tokens.get(andIndex - 1);
		String dest = tokens.get(andIndex + 1);
		
		return new StopsStrategy(origin, dest, stopsNumber, stopsType);
		
	}
}
