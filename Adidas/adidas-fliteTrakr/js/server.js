/**
 * Main app entry point.
 */
const fileLoader = require('./util/fileLoader');

const fileName = '/Users/nestis/Proyectos/PetProjects/SolvedProblems/Adidas/adidas-fliteTrakr/js/testInput.txt';
let file;

// Get filename param
if (process.argv.includes('--file')) {
    const indexFile = process.argv.indexOf('--file');
    if (process.argv.length > indexFile + 1) {
        file = process.argv[indexFile + 1];
        if (!file.startsWith('/')) {
            file = `${process.cwd()}/${file}`;
        }
    }
}

/**
 * Async function that process the file and get its results.
 */
const start = async () => {
    const data = await fileLoader.loadFile(fileName)
        .catch(error => {
            // If there has been an error loading the file...
            console.log('Error!');
            process.exit(1);
    });
    // For each question, obtain its answer and print it
    data.questions.forEach(q => {
        console.log(q.solve(data.flights));
    });
    process.exit(0);
};

start();
   