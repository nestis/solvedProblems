/**
 * FlightTracker definition.
 */
class FlightTraker {

    constructor() {
        this.questions = [];
    }

    /**
     * Sets the array of flights.
     * @param {*} flights Must be an array of Airport objects.
     */
    setFlights(flights) {
        this.flights = flights;
    }

    /**
     * Array of questions.
     * @param {*} question Must be an array of QuestionStrategy objects.
     */
    addQuestion(question) {
        this.questions.push(question);
    }
}

module.exports = FlightTraker;
