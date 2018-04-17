package com.nestis.adidas.fliteTrakr.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.nestis.adidas.fliteTrakr.model.Airport;
import com.nestis.adidas.fliteTrakr.model.FlightTracker;
import com.nestis.adidas.fliteTrakr.model.Question;
import com.nestis.adidas.fliteTrakr.model.strategy.QuestionStrategy;

/**
 * FileLoadHelper.
 * Reads a file a creates a FlightTracker with the info contained in that file.
 * @author nestis
 *
 */
public class FileLoadHelper {
	
	private static final String CONNECTION_LINE = "Connection: ";
	
	private static final String FLIGHT_SEPARATOR = ",";
	
	private static final String FLIGHT_INFO_SEPARATOR = "-";

	/**
	 * Reads the given file and extracts its info.
	 * @param fileName Filename.
	 * @return
	 */
	public static FlightTracker loadFile(String fileName) {
		FlightTracker flightTracker = new FlightTracker();
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fileReader);
			String sCurrentLine;
	 
			while ((sCurrentLine = br.readLine()) != null) {
				// If the current line is the line with the flight routes
				if (sCurrentLine.startsWith(CONNECTION_LINE)) {
					flightTracker.setRoutes(getFlightInfo(sCurrentLine));
				
				// Otherwise is a question
				} else {
					// Manage the exception here, if a question process throws an exception it will simply not be added to the list of questions.
					try {
						flightTracker.getQuestions().add(getQuestion(sCurrentLine));
					} catch(Exception ex) {
						System.out.println("Cannae parse question: " + sCurrentLine);
					}
				}
			}
			br.close();
			fileReader.close();
			
		} catch (FileNotFoundException ex) {
			System.out.printf("\nThe file %s cannot be found. Maybe it doesn't exist?", fileName);
		} catch (IOException ex) {
			System.out.printf("\nThere was a problem trying to read file %s", fileName);
		}
		
		return flightTracker;
	}
	
	/**
	 * Reads the flight routes line.
	 * @param info
	 * @return
	 */
	private static Map<String, Airport> getFlightInfo(String info) {
		// Strip the useless info in the line and split the rest 
		String[] flights = info.replace(CONNECTION_LINE, "").toLowerCase().split(FLIGHT_SEPARATOR);
		
		Map<String, Airport> airports = new HashMap<String, Airport>();
		
		// For each flight found
		Arrays.asList(flights).forEach(f -> {
			String[] flightInfo = f.split(FLIGHT_INFO_SEPARATOR);
			String origin = flightInfo[0].trim();
			
			// Check if the origin airport already exists
			Airport airport = airports.get(origin);
			
			// If not, add it
			if (airport == null) {
				Airport newAirport = new Airport(origin);
				newAirport.addDestination(getDestinationAirport(airports, flightInfo[1].trim()), Float.valueOf(flightInfo[2]));
				airports.put(origin, newAirport);
				
			// Otherwise, add the destination to the Airport object
			} else {
				airport.addDestination(getDestinationAirport(airports, flightInfo[1].trim()), Float.valueOf(flightInfo[2]));
			}
		});
		
		return airports;
	}
	
	/**
	 * Gets the Airport object instance for a given destination. If not exists, it creates it.
	 * @param airports Collection of airports.
	 * @param destination Destination name.
	 * @return
	 */
	private static Airport getDestinationAirport(Map<String, Airport> airports, String destination) {
		Airport destAirport = airports.get(destination);
		// If the destination doesn't exists yet, create it
		if (destAirport == null) {
			destAirport = new Airport(destination);
			airports.put(destination, destAirport);
		}
		return destAirport;
	}
	
	/**
	 * Gets a Question object for a given question string.
	 * @param line String to get the question from.
	 * @return Question instance.
	 * @throws Exception Exception thrown by ASTQuestionHelper.
	 */
	private static Question getQuestion(String line) throws Exception {
		line = line.substring(line.indexOf(" ") + 1, line.length());
		QuestionStrategy qStrategy = ASTQuestionHelper.getQuestionStrategy(line);
		Question question = new Question(qStrategy);
		return question;
	}
}
