const PriceRouteTemplate = require('./priceRouteTemplate');

/**
 * BelowPriceStrategy function. It will return all the routes found between two cities
 * with a lower price than the given one.
 * @param {*} origin Origin airport.
 * @param {*} dest Destination airport.
 * @param {*} price Maximum price allowed.
 */
function BelowPriceStrategy(origin, dest, price) {
    PriceRouteTemplate.call(this, origin, dest, price);
}

BelowPriceStrategy.prototype = Object.create(PriceRouteTemplate.prototype);
BelowPriceStrategy.prototype.constructor = BelowPriceStrategy;

BelowPriceStrategy.prototype.getInfo = function() {
	if (this.routeFound.length > 0) {
		const routes = [];
		this.routeFound.forEach(r => {
			routes.push(r.path.join('-') + '-' + r.total);
		});
		return routes.join(' ');
	} else {
		return 'No such route found!';
	}
}

module.exports = BelowPriceStrategy;
