const QuestionStrategy = require('./questionStrategy');

/**
 * Price Strategy. It will return the price for a route between two airports.
 * @param {*} flightPath Flight path.
 */
function PriceStrategy(flightPath) {
	QuestionStrategy.call(this);
	this.flightPath = flightPath;
}
PriceStrategy.prototype = Object.create(QuestionStrategy.prototype);
PriceStrategy.prototype.constructor = PriceStrategy;

PriceStrategy.prototype.solve = function(flights) {
	let total = 0;
	
	const route = this.flightPath.split("-");
	for(let i = 0, l = route.length; i < l -1; i++) {
		// Get the origin and destination of the route step
		const origin = route[i];
		const dest = route[i + 1];
		
		// Get the origin airport
		originAirport = flights.find(d => d.name === origin);
		if (originAirport != null) {
			
			// For every destination, get the ones that matches the destination, ordered by price asc
			// get only its price an get the first item
			const price = originAirport.destinations
				.filter(d => d.destination.name === dest)
				.sort((a,b) => a.price - b.price)
				.map(d => d.price);

			// If a price has been found, add it to the total
			// Otherwise, set total to zero and exit the for loop.
			if (Array.isArray(price)) {
				total += price[0];
			} else {
				total = 0;
				break;
			}
		} else {
			total = 0;
			break;
		}
	}
	return total > 0 ? total: "No such route found!";
}

module.exports = PriceStrategy;
