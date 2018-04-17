package com.nestis.adidas.fliteTrakr;

import com.nestis.adidas.fliteTrakr.helper.FileLoadHelper;
import com.nestis.adidas.fliteTrakr.model.FlightTracker;

/**
 * FliteTrakr application entry point
 * @author nestis
 *
 */
public class Application { 

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("At least one param is required.");
			System.exit(1);
		} else {
			String paramName = args[0];
			if(paramName.toLowerCase() == "help") {
				System.out.println("Execute the app specifying a filename to be parsed. Example java -jar flite-trakr.jar example.txt");
				System.out.println("If only the fileName is provided, the file must be located within the same path where java is executed.");
				System.out.println("It's also possible to specify an absolute path.");
			} else {
				FlightTracker flightTracker = null;
				// If the paramName is an absolute route...
				if (paramName.startsWith("/") || paramName.startsWith("C")) {
					flightTracker = FileLoadHelper.loadFile(paramName);
				// If not, get the route where java was executed.
				} else {
					final String dir = System.getProperty("user.dir");
					flightTracker = FileLoadHelper.loadFile(dir + "/" + paramName);
				}
				// Solve the mistery!
				flightTracker.solveQuestions();
			}
		}
	}
}
