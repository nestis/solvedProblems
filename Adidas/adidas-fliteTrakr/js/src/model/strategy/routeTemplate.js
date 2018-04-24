const QuestionStrategy = require('./questionStrategy');

/**
 * Defines the base beahaviour to look for a route between two airports.
 * @param {*} origin Origin Airport
 * @param {*} dest Destination airport.
 */
function RouteTemplate(origin, dest) {
	QuestionStrategy.call(this);
	this.origin = origin;
	this.dest = dest;
	this.routeFound = [];
};

// Create a prototype linked to QuestionStrategy prototype.
RouteTemplate.prototype = Object.create(QuestionStrategy.prototype);

// Point constructor to function. In case we want perform a strict type comparison
// a.constructor === RouteTemplate => true
RouteTemplate.prototype.constructor = RouteTemplate;

// Override solve method
RouteTemplate.prototype.solve = function(flights) {
	this.getRoutes(flights);
	return this.getInfo();
}

RouteTemplate.prototype.getRoutes = function(flights) {
	// Get the origin airport
	const originAirport = flights.find(d => d.name === this.origin);
	
	if (originAirport != null) {
		// Iterate over its destinations to find the cheapest route
		originAirport.destinations.forEach(d => {
			// Set the origin as the first item in Route object
			const route = { total: 0, path: [this.origin]};
			route.total += d.price;
			
			// If the this step is the destination, add it to the array
			route.path.push(d.destination.name);
			if (d.destination.name === this.dest) {
				this.routeFound.push({
					total: route.total,
					path: route.path.slice(0)
				});
			}
			// Iterate over its children to find another possible route
			this.iterateOverChild(d, route);
		});
	}
}
RouteTemplate.prototype.iterateOverChild = function(destination, route) {
	// Iterate over all the destinations
	const destinations = destination.destination.destinations;
	
	destinations.forEach(d => {
		// If the current destination is the one we are looking for...
		if (d.destination.name === this.dest) {
			// Add its value and save the route into routeFound array
			route.total += d.price;
			route.path.push(d.destination.name);
			this.routeFound.push({
				total: route.total,
				path: route.path.slice(0)
			});
			
			// Once the route has been added to the array, we can remove this step from the route object.
			// because the iteration will continue when we get back to the previous caller.
			// The flow will return to the previous call so we have to delete this iteration.
			// The parent node will continue the execution flow with the next child (if any)
		   route.total -= d.price;
		   route.path.splice(route.path.length - 1, 1);
			   
	   // If the current route is not the destination and it hasn't been previously visited (we wouldn't want to fly to the same places over and over again...)
		} else if (!route.path.includes(d.destination.name)){
			// Add its price and the location and iterate over its children destinations
			route.total += d.price;
			route.path.push(d.destination.name);
			this.iterateOverChild(d, route);
			
			// Once we have iterate over the children, we can delete this route.
			// Same reasoning as above
		   route.total -= d.price;
		   route.path.splice(route.path.length - 1, 1);
		}
	});
}

module.exports = RouteTemplate;
