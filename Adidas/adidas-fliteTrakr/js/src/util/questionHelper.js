const Constants = require('./constants');
const PriceStrategy = require('../model/strategy/priceStrategy');
const CheapestStrategy = require('../model/strategy/cheapestStrategy');
const StopsStrategy = require('../model/strategy/stopsStrategy');
const BelowPriceStrategy = require('../model/strategy/belowPriceStrategy');

/**
 * Parses a Whet question.
 * What is the price of the connection NUE-FRA-LHR?
 * What is the cheapest connection from NUE to AMS?
 * @param {*} tokens Array of string containing the line tokens.
 */
const getWhatQuestion = (tokens) => {
    // What is the cheapest connection from NUE to AMS?
    if (tokens.includes(Constants.DICTIONARY.CHEAPEST)) {
        // Get the TO index to obtain the origin and destination.
        const toIndex = tokens.indexOf(Constants.DICTIONARY.TO);
        const origin = tokens[toIndex - 1];
        const dest = tokens[toIndex + 1];
        
        const cheap = new CheapestStrategy(origin, dest);
        return cheap;
        
    // What is the price of the connection NUE-FRA-LHR?
    } else {
        // Get the last item of the token as it will be the flight path
        const flightPath = tokens[tokens.length - 1];
        return new PriceStrategy(flightPath);
    }
}

/**
 * Parses a How question.
 * How many different connections with maximum 3 stops exists between NUE and FRA?
 * @param {*} tokens Array of string containing the line tokens.
 */
const getHowQuestion = (tokens) => {
    const withIndex = tokens.lastIndexOf(Constants.DICTIONARY.WITH);
    // Get the type of search. maximum, minimun or exactly 
    const type = tokens[withIndex + 1];
    
    let stopsType = null;
    switch(type) {
        case Constants.DICTIONARY.EXACTLY: stopsType =  Constants.VALUES.EXACTLY; break;
        case Constants.DICTIONARY.MINIMUM: stopsType =  Constants.VALUES.MINIMUM; break;
        case Constants.DICTIONARY.MAXIMUM: stopsType =  Constants.VALUES.MAXIMUM; break;
    }
    
    // Get the number of stops
    const stopsNumber = parseInt(tokens[withIndex + 2]);
    
    // Get the AND token to obtain origin and destination
    const andIndex = tokens.indexOf(Constants.DICTIONARY.AND);
    const origin = tokens[andIndex - 1];
    const dest = tokens[andIndex + 1];
    
    return new StopsStrategy(origin, dest, stopsNumber, stopsType);
}

/**
 * Find all conenctions from NUE to LHR below 170Euros!
 * @param {*} tokens Array of string containing the line tokens.
 */
const getFindQuestion = (tokens) => {
    // Get the TO index to obtain both origin ans destination.
    const toIndex = tokens.lastIndexOf(Constants.DICTIONARY.TO);
    const origin = tokens[toIndex - 1];
    const dest = tokens[toIndex + 1];

    // Get the last item of the token as it will be the price
    const price = tokens[tokens.length - 1];
    // Get the numeric value from the price token
    // ^\d+[\. || ,]*\d+ => start of String + numbers + (. or ,) + numbers => 170: 170.50: 2133,32 
    const matcher = /^\d+[\. || ,]*\d+/.exec(price);
    let priceFloat = 0.0;
    if (matcher && matcher.length > 0) {
        priceFloat = parseFloat(matcher[0]);
    }
    
    return new BelowPriceStrategy(origin, dest, priceFloat);
}

/**
 * Parses a question and return the corresponding question strategy.
 * @param {*} question String containiing the question.
 */
const readQuestion = (question) => {
    question = question.replace(/#\d+: /, '').replace('?', '')
    qTokens = question.split(' ');
    switch (qTokens[0]) {
        case Constants.DICTIONARY.WHAT:
            return getWhatQuestion(qTokens);
            break;
        case Constants.DICTIONARY.HOW:
            return getHowQuestion(qTokens);
            break;
        case Constants.DICTIONARY.FIND: 
            return getFindQuestion(qTokens);
            break;
    }
}

module.exports = readQuestion;