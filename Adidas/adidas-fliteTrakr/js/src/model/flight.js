/**
 * Flight definition.
 */
class Flight {

    /**
     * Constructor.
     * @param {*} destination Destination Airport. An Airport object.
     * @param {*} price Price. Float.
     */
    constructor(destination, price) {
        this.destination = destination;
        this.price = price;
    }
}

module.exports = Flight;
