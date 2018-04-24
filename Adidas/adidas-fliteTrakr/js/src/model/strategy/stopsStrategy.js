const RouteTemplate = require('./routeTemplate');
const Constants = require('../../util/constants');

/**
 * Defines the behaviour that gets the number of routes that comply with the number of stops permitted.
 * How many different connections with maximum 3 stops exists between NUE and FRA?
 * How mand different connections with exactly 1 stop exists between LHR and AMS?
 * @param {*} origin Origin airport.
 * @param {*} dest Destination airport.
 * @param {*} stops Number of stops.
 * @param {*} type Boolean logic for the stop. Must be a value for Constants.VALUES.*
 */
function StopsStrategy(origin, dest, stops, type) {
    RouteTemplate.call(this, origin, dest);
    this.stops = stops;
    this.type = type;
}
StopsStrategy.prototype = Object.create(RouteTemplate.prototype);
StopsStrategy.prototype.constructor = StopsStrategy;

/**
 * Returns the info from the routes obtained.
 */
StopsStrategy.prototype.getInfo = function() {
	if (this.routeFound.length > 0) {
        let routes = [];
        switch (this.type) {
            case Constants.VALUES.EXACTLY:
                routes = this.routeFound.filter(r => r.path.length - 2 === this.stops);
                break;
            case Constants.VALUES.MINIMUM:
                routes = this.routeFound.filter(r => r.path.length - 2 >= this.stops);
                break;
            case Constants.VALUES.MAXIMUM:
                routes = this.routeFound.filter(r => r.path.length - 2 <= this.stops);
                break;
        }
        return routes.length;
	} else {
		return 'No such route found!';
	}
}

module.exports = StopsStrategy;
