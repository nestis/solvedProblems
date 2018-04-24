/**
 * Airport definition.
 */
class Airport {

    /**
     * Constructor.
     * @param {*} name City name.
     * @param {*} destinations Destinations. Array of Airport.
     */
    constructor(name, destinations) {
        this.name = name;
        this.destinations = destinations || [];
    }

    /**
     * Adds a new destination.
     * @param {*} dest Airport to add.
     */
    addDestination(dest) {
        this.destinations.push(dest);
    }
}

module.exports = Airport;
