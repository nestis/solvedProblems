package com.nestis.adidas.fliteTrakr.helper;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.nestis.adidas.fliteTrakr.model.FlightTracker;

public class FileLoadHelperTest {

	@Test
	public void shouldLoadAFile() throws Exception {
		String fileName = "testInput.txt";

		final String dir = System.getProperty("user.dir") + "/src/test/resources";
		FlightTracker flightTracker = FileLoadHelper.loadFile(dir + "/" + fileName);
		
		assertTrue(flightTracker != null);
		assertTrue(flightTracker.getQuestions().size() == 9);
		assertTrue(flightTracker.getRoutes().keySet().size() == 4);
	}
	
	@Test
	public void shouldLoadAFileWithAnInvalidQuestion() throws Exception {
		String fileName = "testInput2.txt";

		final String dir = System.getProperty("user.dir") + "/src/test/resources";
		FlightTracker flightTracker = FileLoadHelper.loadFile(dir + "/" + fileName);
		
		assertTrue(flightTracker != null);
		assertTrue(flightTracker.getQuestions().size() == 8);
		assertTrue(flightTracker.getRoutes().keySet().size() == 4);
	}
	

	
	@Test
	public void shouldNotLoadAFile() throws Exception {
		String fileName = "testInput3.txt";

		final String dir = System.getProperty("user.dir") + "/src/test/resources";
		FlightTracker flightTracker = FileLoadHelper.loadFile(dir + "/" + fileName);
		
		assertTrue(flightTracker != null);
		assertTrue(flightTracker.getQuestions().isEmpty());
		assertTrue(flightTracker.getRoutes() == null);
	}
}
