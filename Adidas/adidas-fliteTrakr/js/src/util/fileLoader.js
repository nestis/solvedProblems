const fs = require('fs');
const readline = require('readline');

const Constants = require('./constants');
const Airport = require('../model/airport');
const Flight = require('../model/flight');
const FligthTraker = require('../model/flightTraker');
const QuestionHelper = require('./questionHelper');

/**
 * Parses the flights line of the file.
 * @param {*} conns String containing the flights info.
 */
const readConnections = (conns) => {
    const airports = [];

    conns.split(Constants.FLIGHT_SEPARATOR).forEach(flight => {
        const tokens = flight.replace(' ', '').split(Constants.FLIGHT_INFO_SEPARATOR);

        if (isNaN(tokens[2])) {
            console.log(`Cannae parse destination price ${tokens[2]}`);
        }
        const origin = tokens[0];

        // Check if the origin airport is already inserted in the airports array.
        let originAirport = airports.find(d => d.name === origin);
        // If not, create it
        if (!originAirport) {
            originAirport = new Airport(origin, []);
            airports.push(originAirport);
        }

        // Do the same with the destination airport.
        let destinationAirport = airports.find(d => d.name === tokens[1]);
        if (!destinationAirport) {
            destinationAirport = new Airport(tokens[1], []);
            airports.push(destinationAirport);
        }

        // Create a Flight object with the destination airport object a its price.
        const destination = new Flight(destinationAirport, parseFloat(tokens[2]));
        
        // Add the Flight object to originAirport object.
        originAirport.destinations.push(destination);
    });
    return airports;
}

/**
 * FileLoader function. Provides logic to load a file and get its flights and questions.
 */
const FileLoader = {
    /**
     * Loads the given filename. Returns a Promise.
     * @param {*} fileName Filename. String.
     */
    loadFile(fileName) {
        const flightTraker = new FligthTraker();
        
        // Return created promise
        return new Promise((resolve, reject) => {
            // Check if the file exists and it's readable.
            fs.access(fileName, fs.constants.R_OK, (err) => {
                // If error, log it and reject the promise
                if (err) {
                    console.log(`There is a problem reading the file ${fileName}`)
                    reject(err);
                }
                // Otherwise, read the file line by line
                readline.createInterface({
                    input: fs.createReadStream(fileName)
                }).on('line', (line) => {
                    if (line.startsWith(Constants.CONNECTION_LINE)) {
                        line = line.replace(Constants.CONNECTION_LINE, '').toLowerCase();
                        flightTraker.setFlights(readConnections(line));
                    } else {
                        flightTraker.addQuestion(QuestionHelper(line.toLowerCase()));
                    }
                }).on('close', () => {
                    // Finally, resolve the promise with the info acquired.
                    resolve(flightTraker);
                });
           });
        });
    }
}

module.exports = FileLoader;
