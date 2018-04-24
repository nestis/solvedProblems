const fileLoader = require('../util/fileLoader');

it('should return a flightTracker object', async () => {
    const flightTraker = await fileLoader.loadFile('testInput.txt');
    expect(flightTraker.questions.length).toEqual(9);
});
