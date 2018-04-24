const RouteTemplate = require('./routeTemplate');

/**
 * Defines a behaviour to obtain routes from A to B where the price is below the given one.
 * It can go through a same place more than once.
 * @param {*} origin Origin airport
 * @param {*} dest Destination airport.
 * @param {*} price Maximum price.
 */
function PriceRouteTemplate(origin, dest, price) {
    RouteTemplate.call(this, origin, dest);
    this.price = price;
}
PriceRouteTemplate.prototype = Object.create(RouteTemplate.prototype);
PriceRouteTemplate.prototype.constructor = PriceRouteTemplate;

/**
 * Checks a destination and its childs to get a route.
 * @param {*} destination Destination to check.
 * @param {*} route Current route obtained so far.
 */
PriceRouteTemplate.prototype.iterateOverChild = function(destination, route) {
	const destinations = destination.destination.destinations;
 		
	// Iterate over the destinations
	destinations.forEach(d =>{
		// If the current destination is the one we are looking for...
		if (d.destination.name === this.dest) {
			// Add its value and save the route into routeFound array
			route.total += d.price;
			route.path.push(d.destination.name);
			
			// If the actual route price is lower than the objective price, add the route (it's a valid route) to the array and iterate again
			// We iterate again cuz it's possible to come to the destination another time. The aim is to travel within the budget, it's not about flight efficiency
			// Example => ZGZ-LHR-DUB-LHR-DUB
			if (route.total <= this.price) {
				this.routeFound.push({
					total: route.total,
					path: route.path.slice(0)
				});
				this.iterateOverChild(d, route);
			}
			// At this point we have already iterate over the destination children looking for another way to the destionation
			// So we can remove the destination
			route.total -= d.price;
			route.path.splice(route.path.length - 1, 1);
				
		// If the current route is not the destination and it hasn't been previously visited (we wouldn't want to fly to the same places over and over again...)
		} else {
			// Add its price and the location and iterate over its children destinations
			route.total += d.price;
			route.path.push(d.destination.name);
			
			if (route.total < this.price) {
				this.iterateOverChild(d, route);
			}
			route.total -= d.price;
			route.path.splice(route.path.length - 1, 1);
		}
	});
}

module.exports = PriceRouteTemplate;
