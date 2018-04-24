const RouteTemplate = require('./routeTemplate');

/**
 * Cheapest Strategy function. It will get all the connections between two airport
 * and it will print the cheapest one.
 * @param {*} orig Origin airport.
 * @param {*} dest Destination airport.
 */
function CheapestStrategy(orig, dest) {
	RouteTemplate.call(this, orig, dest);
}

CheapestStrategy.prototype = Object.create(RouteTemplate.prototype);
CheapestStrategy.prototype.constructor = CheapestStrategy;

// Define its getInfo method.
CheapestStrategy.prototype.getInfo = function() {
	if (this.routeFound.length > 0) {
		this.routeFound.sort((a, b) => a.total - b.total);
		return this.routeFound[0].path.join('-') + '-' + this.routeFound[0].total;
	} else {
		return 'No such route found!';
	}
};

module.exports = CheapestStrategy;
