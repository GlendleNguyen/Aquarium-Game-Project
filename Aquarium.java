/**
 * Aquarium represents a single problem in the game Aquarium.
 *
 * @authors Farshad Ghanbari (21334883) and Glen Nguyen (22575354)
 * @version 2020
 */

public class Aquarium
{
    private int   size;         // the board is size x size
    private int[] columnTotals; // the totals at the top of the columns, left to right
    private int[] rowTotals;    // the totals at the left of the rows, top to bottom 
    private FileIO file;        // the fileIO class to read puzzle's text files
    private Space[][] spaces;   // the board divided into spaces, each empty, water, or air                         
    private int[][] aquariums;  // the board divided into aquariums, numbered from 1,2,3,...
                                // spaces with the same number are part of the same aquarium
    /**
     * Constructor for objects of class Aquarium. 
     * Creates, initialises, and populates all of the fields.
     */
    public Aquarium(String filename)
    {
        // TODO 3
        
        this.file = new FileIO(filename);
        
        // Sets the size based on the length of the text file's first line
        this.size = parseLine(file.getLines().get(0)).length;
        this.columnTotals = parseLine(file.getLines().get(0));
        this.rowTotals = parseLine(file.getLines().get(1));
        this.spaces = new Space[size][size];
        this.aquariums = new int[size][size];
        
        // Reads the text file starting from the 3rd line
        // initalizes the aquarium array based on the numbers from the text file
        for (int i = 3; i < this.size + 3; i++) {
         aquariums[i-3] = parseLine(file.getLines().get(i));
        }
        
        // clear() initializes all the spaces to empty
        clear();
    }

    /**
     * Uses the provided example file on the LMS page.
     */
    public Aquarium()
    {
        this("Examples/a6_1.txt");
    }

    /**
     * Returns an array containing the ints in s, 
     * each of which is separated by one space. 
     * e.g. if s = "1 299 34 5", it will return {1,299,34,5} 
     */
    public static int[] parseLine(String s)
    {
        // TODO 2
        
        // String array to hold the string of numbers
        String[] numbers = s.split(" ");
        
        // String numbers are convereted to int and stored in num
        int[] num = new int[numbers.length];
        for(int i = 0; i < numbers.length; i++) {
         num[i] = Integer.parseInt(numbers[i]);   
        }
        return num;
    }

    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 1a
        return size;
    }

    /**
     * Returns the column totals.
     */
    public int[] getColumnTotals()
    {
        // TODO 1b
        return columnTotals;
    }

    /**
     * Returns the row totals.
     */
    public int[] getRowTotals()
    {
        // TODO 1c
        return rowTotals;
    }

    /**
     * Returns the board in aquariums.
     */
    public int[][] getAquariums()
    {
        // TODO 1d
        return aquariums;
    }

    /**
     * Returns the board in spaces.
     */
    public Space[][] getSpaces()
    {
        // TODO 1e
        return spaces;
    }

    /**
     * Performs a left click on Square r,c if the indices are legal, o/w does nothing. 
     * A water space becomes empty; other spaces become water. 
     */
    public void leftClick(int r, int c)
    {
        // TODO 4
        
        // Indeces for rows (r) and columns (c) are legal if...
        // the are >= to 0 and < size of the puzzel.
        // if space is air or empty, when left clicked it becomes water
        // else if it is water, it becomes empty
        if (( r >= 0 && r < this.size) && (c >= 0 && c < this.size)) {
            if ((spaces[r][c] == Space.AIR) || spaces[r][c] == Space.EMPTY) {
                spaces[r][c] = Space.WATER;
            } else {
                spaces[r][c] = Space.EMPTY;
            }
        }
    }

    /**
     * Performs a right click on Square r,c if the indices are legal, o/w does nothing. 
     * An air space becomes empty; other spaces become air. 
     */
    public void rightClick(int r, int c)
    {
        // TODO 5
        
        // Indeces for rows (r) and columns (c) are legal if...
        // the are >= to 0 and < size of the puzzel.
        // if space is water or empty, when right clicked it becomes air
        // else if it is air, it becomes empty
        if (( r >= 0 && r < this.size) && (c >= 0 && c < this.size)) {
            if ((spaces[r][c] == Space.WATER) || spaces[r][c] == Space.EMPTY) {
                spaces[r][c] = Space.AIR;
            } else {
                spaces[r][c] = Space.EMPTY;
            }
        }
    }

    /**
     * Empties all of the spaces.
     */
    public void clear()
    {
        // TODO 6
        
        // Initializes all the spaces to empty.
        // It is used in the constructor of Aquarium
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.spaces[i][j] = Space.EMPTY;
            }
        }
    }
}
