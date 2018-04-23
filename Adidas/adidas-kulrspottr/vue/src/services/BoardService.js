
const generateColorPalette = (level) => {
    const hue = Math.floor(Math.random() * 360);
    const saturation = 100 - 5 * level;
    const lightness = 50 + 5 * level;

    return {
        color: `hsl(${hue}, 100%, 50%)`,
        specialColor: `hsl(${hue},${lightness}%,${saturation}%)`,
    };
}

class BoardService {
    initBoard(level) {
        // Init the number of boxes that will be rendered per axis.
        // Level 1 must render 4 boxes, Level 2 must render 9 boxes..
        // So the number of boxes for each level is => (Level + 1)^2
        const boxesPerAxis = level + 1;
        const totalBoxes = boxesPerAxis ** 2;
    
        // Get the special box with the special color and save its value
        const specialBox = Math.ceil((Math.random() * totalBoxes));
        const hsl = generateColorPalette(level);

        return { hsl, level, boxesPerAxis, totalBoxes, specialBox };
    }
}

const boardService = new BoardService();
export default boardService;
