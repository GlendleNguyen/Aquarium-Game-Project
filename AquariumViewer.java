/**
 * AquariumViewer represents an interface for playing a game of Aquarium.
 *
 * @authors Farshad Ghanbari (21334883) and Glen Nguyen (22575354)
 * @version 2020
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.SwingUtilities;
import javax.swing.Timer;
public class AquariumViewer implements MouseListener
{
    private final int BOXSIZE = 40;          // the size of each square
    private final int OFFSET  = BOXSIZE * 2; // the gap around the board
    private       int WINDOWSIZE;            // set this in the constructor 
    private Aquarium puzzle;                 // the internal representation of the puzzle
    private int        size;                 // the puzzle is size x size
    private SimpleCanvas sc;                 // the display window
    private Timer timer;                     // timer for the puzzle
    private int seconds = 0;                 // holds seconds value for timer
    private int minutes = 0;                 // holds minutes value for timer
    private boolean solved = false;          // whether the puzzle has been solved
                                             // Used in the timer() method
    private int puzzleTheme = 0;             // the color scheme for the puzzle
    private Color gridColor = Color.BLACK;
    private Color aquariumColor = Color.BLACK;
    private Color waterColor = Color.ORANGE;
    private Color airColor = Color.PINK;
    private Color emptyColor = Color.WHITE;

    public static final Color VERY_LIGHT_RED = new Color (255,102,102);
    public static final Color VERY_LIGHT_BLUE = new Color (51,204,255);
    public static final Color turquoise = new Color (64,224,208);
    public static final Color VERY_LIGHT_GREEN = new Color (102,255,102);
    public static final Color mediumBlue = new Color (0, 0, 205);
    public static final Color orchid = new Color (186,85,211);
    public static final Color sienna = new Color (160, 82, 45);
    public static final Color peru = new Color (205, 133, 63);
    public static final Color navajo = new Color (255, 222, 173);
    /**
     * Main constructor for objects of class AquariumViewer.
     * Sets all fields, and displays the initial puzzle.
     */
    public AquariumViewer(Aquarium puzzle)
    {
        // TODO 8
        this.puzzle = puzzle;
        this.size = this.puzzle.getSize();
        WINDOWSIZE = (BOXSIZE * size) + (2 * OFFSET);
        sc = new SimpleCanvas("Aquarium Puzzle", WINDOWSIZE, WINDOWSIZE,Color.WHITE);
        sc.addMouseListener(this);
        
        // Draws the grids, numbers, aquariums and buttons
        displayPuzzle();

        // Creates and starts the timer
        timer();
    }

    /**
     * Selects from among the provided files in folder Examples. 
     * xyz selects axy_z.txt. 
     */
    public AquariumViewer(int n)
    {
        this(new Aquarium("Examples/a" + n / 10 + "_" + n % 10 + ".txt"));
    }

    /**
     * Uses the provided example file on the LMS page.
     */
    public AquariumViewer()
    {
        this(61);
    }

    /**
     * Returns the current state of the puzzle.
     */
    public Aquarium getPuzzle()
    {
        // TODO 7a
        return this.puzzle;
    }

    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 7b
        return this.size;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        // TODO 7c
        return this.sc;
    }

    /**
     * Displays the initial puzzle; see the LMS page for the format.
     */
    private void displayPuzzle()
    {
        // TODO 13
        displayGrid();
        displayNumbers();
        displayAquariums();
        displayButtons();
    }
            
    /**
     * Displays the grid in the middle of the window.
     */
    public void displayGrid()
    {
        // TODO 9
        for (int i = 0; i <= size; i++) {
            sc.drawLine((i * BOXSIZE) + OFFSET, OFFSET,                             
                        (i * BOXSIZE) + OFFSET, WINDOWSIZE - OFFSET, gridColor);
        }
        for (int i = 0; i <= size; i++) {
            sc.drawLine(OFFSET             , (i * BOXSIZE) + OFFSET, 
                        WINDOWSIZE - OFFSET, (i * BOXSIZE) + OFFSET, gridColor);
        }
    }

    /**
     * Displays the numbers around the grid.
     */
    public void displayNumbers()
    {
        // TODO 10
        sc.setFont(new Font("SansSerif", Font.BOLD, 20));
        
        // In order to centre the numbers correctly...
        // We had to use different coordinates for 1 digit and 2 digit numbers
        for (int i = 0; i < size; i++) {
            // gets and draws the column numbers above the grid
            if (puzzle.getColumnTotals()[i] < 10) {
                sc.drawString(puzzle.getColumnTotals()[i], 
                   (((i * BOXSIZE) + OFFSET) - 5  + (BOXSIZE / 2)), OFFSET - (BOXSIZE / 2), 
                   Color.BLACK);
            } else {
                sc.drawString(puzzle.getColumnTotals()[i], 
                   (((i * BOXSIZE) + OFFSET) - 13 + (BOXSIZE / 2)), OFFSET - (BOXSIZE / 2), 
                   Color.BLACK);
            }
            // gets and draws the row numbers to the left of the grid
            if (puzzle.getRowTotals()[i] < 10) {
                sc.drawString(puzzle.getRowTotals()[i], 
                OFFSET - (BOXSIZE / 2) - 10, (((i * BOXSIZE) + OFFSET) + 7 + (BOXSIZE / 2)), 
                   Color.BLACK);
            } else {
                sc.drawString(puzzle.getRowTotals()[i], 
                OFFSET - (BOXSIZE / 2) - 17, (((i * BOXSIZE) + OFFSET) + 7 + (BOXSIZE / 2)), 
                   Color.BLACK);
            }
        }
    }

    /**
     * Displays the aquariums.
     */
    public void displayAquariums()
    {
        // TODO 11
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int im = (size + i - 1) % size;
                int jm = (size + j - 1) % size;
                // Since there is always a border between two different numbers...
                // if two neighbouring numbers (up and down) are different...
                // draws a rectangle in the top of the box
                if (puzzle.getAquariums()[i][jm] != puzzle.getAquariums()[i][j]) {
                    sc.drawRectangle((j * BOXSIZE) + OFFSET - 2, 
                                     (i * BOXSIZE) + OFFSET - 2,
                                     (j * BOXSIZE) + OFFSET + 2, 
                                     (i * BOXSIZE) + OFFSET + BOXSIZE + 2, 
                                      aquariumColor);
                }
                // if two neighbouring numbers (left and right) are different...
                // draws a rectangle to the left of the box
                if (puzzle.getAquariums()[im][j] != puzzle.getAquariums()[i][j]) {
                    sc.drawRectangle((j * BOXSIZE) + OFFSET    ,
                                     (i * BOXSIZE) + OFFSET - 2, 
                                     (j * BOXSIZE) + OFFSET + BOXSIZE, 
                                     (i * BOXSIZE) + OFFSET + 2, 
                                      aquariumColor);
                }
                // The above two if statemenents dont include the...
                // right and bottom border of the puzzle
                
                // draws rectangle in the right side of the puzzle
                sc.drawRectangle((WINDOWSIZE - OFFSET)    ,               OFFSET  - 2,
                                 (WINDOWSIZE - OFFSET) + 4, (WINDOWSIZE - OFFSET) + 4,
                                  aquariumColor);
                                  
                // draws rectangle in the bottom of the puzzle
                sc.drawRectangle(              OFFSET  - 2, (WINDOWSIZE - OFFSET)    ,
                                ( WINDOWSIZE - OFFSET)    , (WINDOWSIZE - OFFSET) + 4, 
                                  aquariumColor);
            }
        }
    }

    /**
     * Displays the buttons below the grid.
     */
    public void displayButtons()
    {
        // TODO 12
        sc.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        // draws the solved botton
        sc.drawRectangle(        OFFSET - 2, (WINDOWSIZE - OFFSET) + 15, 
                             2 * OFFSET,      WINDOWSIZE - BOXSIZE,      Color.LIGHT_GRAY);
        sc.drawString("SOLVED?", OFFSET + 8, (WINDOWSIZE - OFFSET) + 32, Color.BLACK);
        
        // draws the clear button
        sc.drawRectangle(      WINDOWSIZE - (2 * OFFSET),      (WINDOWSIZE - OFFSET) + 15, 
                        (      WINDOWSIZE -      OFFSET) + 5,   WINDOWSIZE - BOXSIZE,      
                                                                         Color.LIGHT_GRAY);
        sc.drawString("CLEAR", WINDOWSIZE - (2 * OFFSET) + 20, (WINDOWSIZE - OFFSET) + 32,
                                                                         Color.BLACK);
                                                                         
        // draws the theme button
        sc.drawRectangle(WINDOWSIZE / 2 - 30,10, WINDOWSIZE / 2 + 30, 30, Color.LIGHT_GRAY);
        sc.drawString("Theme",WINDOWSIZE / 2 - 23, 25,    Color.BLACK);                                                                 
    }

    /**
     * Updates the display of Square r,c. 
     * Sets the display of this square to whatever is in the squares array. 
     */
    public void updateSquare(int r, int c)
    {
        // TODO 14
        if (puzzle.getSpaces()[r][c] == Space.WATER) {
            sc.drawRectangle((c * BOXSIZE) + OFFSET          , (r * BOXSIZE) + OFFSET,
                             (c * BOXSIZE) + OFFSET + BOXSIZE, (r * BOXSIZE) + OFFSET + BOXSIZE,
                              waterColor);
        }
        if (puzzle.getSpaces()[r][c] == Space.AIR) {
            sc.drawRectangle((c * BOXSIZE) + OFFSET          , (r * BOXSIZE) + OFFSET,
                             (c * BOXSIZE) + OFFSET + BOXSIZE, (r * BOXSIZE) + OFFSET + BOXSIZE, 
                              Color.WHITE);
            sc.drawCircle((  (2 * c) + 1)  * (BOXSIZE / 2) + OFFSET, 
                          (  (2 * r) + 1)  * (BOXSIZE / 2) + OFFSET, BOXSIZE / 4, 
                              airColor);
        }
        if (puzzle.getSpaces()[r][c] == Space.EMPTY) {
            sc.drawRectangle((c * BOXSIZE) + OFFSET,           (r * BOXSIZE) + OFFSET,
                             (c * BOXSIZE) + OFFSET + BOXSIZE, (r * BOXSIZE) + OFFSET + BOXSIZE, 
                              emptyColor);
        }
        displayGrid();
        displayAquariums();
    }

    /**
     * Responds to a mouse click. 
     * If it's on the board, make the appropriate move and update the screen display. 
     * If it's on SOLVED?,   check the solution and display the result. 
     * If it's on CLEAR,     clear the puzzle and update the screen display. 
     */
    public void mousePressed(MouseEvent e) 
    {
        // TODO 15
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.getY() > OFFSET && e.getY() < (WINDOWSIZE - OFFSET) 
             && e.getX() > OFFSET && e.getX() < (WINDOWSIZE - OFFSET)) {
                // if clicked on box, update that square
                puzzle.leftClick((e.getY() - OFFSET) / BOXSIZE, (e.getX() - OFFSET) / BOXSIZE);
                updateSquare    ((e.getY() - OFFSET) / BOXSIZE, (e.getX() - OFFSET) / BOXSIZE);
                // update the color of numbers for rows and columns (Red if it is wrong)
                displayNumbers();
                waterCountCheck ();
                
            }
            
            if (e.getX() > WINDOWSIZE / 2 - 30 && e.getX() < WINDOWSIZE / 2 + 30 && e.getY() > 10 && e.getY() < 30){
                changeTheme();
                displayPuzzle();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        updateSquare(i,j);
                    }
                }
            }
            
            if (e.getX() >  WINDOWSIZE - (2 * OFFSET)      && e.getX() <  (WINDOWSIZE - OFFSET) + 5
             && e.getY() > (WINDOWSIZE -      OFFSET) + 15 && e.getY() <   WINDOWSIZE - BOXSIZE){
                // if clicked on clear button, sets all boxes to blank 
                puzzle.clear  ();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        updateSquare(i,j);
                    }
                }
            }
            
            if (e.getX() >                    OFFSET  - 2  && e.getX() <            2 * OFFSET
             && e.getY() > (WINDOWSIZE -      OFFSET) + 15 && e.getY() <   WINDOWSIZE - BOXSIZE) {
                // if left clicked on solved button, it checkes the solution 
                String solution = CheckSolution.isSolution(puzzle);
                sc.setFont(new Font("SansSerif", Font.BOLD, 20));
                if (solution  == "\u2713\u2713\u2713") {
                    sc.drawRectangle(0         , WINDOWSIZE - BOXSIZE + 5, 
                                     WINDOWSIZE, WINDOWSIZE, 
                                     Color.WHITE);   
                    sc.drawString("\u2713\u2713\u2713", OFFSET, (WINDOWSIZE - BOXSIZE / 2), 
                                     Color.BLACK);
                             
                    // Stop the timer if game is solved                 
                    this.solved = true;
                } else {
                    sc.drawRectangle(0,          WINDOWSIZE - BOXSIZE + 5, 
                                     WINDOWSIZE, WINDOWSIZE, 
                                     Color.WHITE);   
                    sc.drawString(solution            , OFFSET, (WINDOWSIZE - BOXSIZE / 2), 
                                     Color.BLACK);   
                }
            }
            
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (e.getY() > OFFSET && e.getY() < (WINDOWSIZE - OFFSET) 
             && e.getX() > OFFSET && e.getX() < (WINDOWSIZE - OFFSET)) {
                puzzle.rightClick((e.getY() - OFFSET) / BOXSIZE, (e.getX() - OFFSET) / BOXSIZE);
                updateSquare     ((e.getY() - OFFSET) / BOXSIZE, (e.getX() - OFFSET) / BOXSIZE);
            }
        }
    }

    /**
     * Creates a timer for the puzzle.
     */
    public void timer()
    {
        // create and start a timer
        new Timer(1000, new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if (solved == true) {
                        // do nothing, to stop the timer
                    } else {
                        updateTimer();
                    }
                }
            }).start();
    }

    /**
     * Updates the puzzle timer.
     */
    public void updateTimer()
    {
        sc.setFont(new Font("SansSerif", Font.BOLD, 16));
        sc.drawRectangle((WINDOWSIZE / 2) - BOXSIZE + 5, (WINDOWSIZE - OFFSET) + 15, 
                         (WINDOWSIZE / 2) + BOXSIZE - 5, (WINDOWSIZE - BOXSIZE), Color.WHITE);
        if(seconds < 59) {
            seconds++;
        }
        else  {
            seconds = 0;
            minutes++;
        }
        if(seconds < 10) {
            sc.drawString(minutes + ":0" + seconds, (WINDOWSIZE / 2) - 20, 
                                                    (WINDOWSIZE - 47)          , Color.BLACK);
        }
        else {
            sc.drawString(minutes + ":" + seconds, (WINDOWSIZE / 2) - 20, 
                                                   (WINDOWSIZE - 47)           , Color.BLACK);
        }
    }
    
    /**
     * if water count for each row or column is greater than
     * what it is suppose to be, the color of the number changes to red
     */
    public void waterCountCheck()
    {
        // put your code here
        sc.setFont(new Font("SansSerif", Font.BOLD, 20));
        for (int i = 0; i < size; i++) {
            if (CheckSolution.rowCounts(puzzle)[i] > puzzle.getRowTotals()[i]) {
                    if (puzzle.getRowTotals()[i] < 10) {
                    sc.drawString(puzzle.getRowTotals()[i], OFFSET     - (BOXSIZE / 2) - 10, 
                                           ((i * BOXSIZE) + OFFSET + 7 + (BOXSIZE / 2)),   Color.RED);
                } else {
                    sc.drawString(puzzle.getRowTotals()[i], OFFSET - (BOXSIZE / 2) - 17, 
                                           ((i * BOXSIZE) + OFFSET + 7 + (BOXSIZE / 2)),   Color.RED);
                }
            }
            
            if (CheckSolution.columnCounts(puzzle)[i] > puzzle.getColumnTotals()[i]) {
                    if (puzzle.getColumnTotals()[i] < 10) {
                    sc.drawString(puzzle.getColumnTotals()[i], 
                    ((i * BOXSIZE) + OFFSET - 5  + (BOXSIZE / 2)), OFFSET - (BOXSIZE / 2), Color.RED);
                } else {
                    sc.drawString(puzzle.getColumnTotals()[i], 
                    ((i * BOXSIZE) + OFFSET - 13 + (BOXSIZE / 2)), OFFSET - (BOXSIZE / 2), Color.RED);
                }
            }
        }
        
    }
    
    /**
     * Updates the color scheme of the puzzle
     */
    public void changeTheme()
    {
        if (puzzleTheme < 4) {
            puzzleTheme++;   
        } else {
            puzzleTheme = 0;   
        }
        if (puzzleTheme == 0) {
            gridColor = Color.BLACK;
            aquariumColor = Color.BLACK;
            waterColor = Color.ORANGE;
            airColor = Color.PINK;
            emptyColor = Color.WHITE;
        }
        if (puzzleTheme == 1) {
            gridColor = Color.BLACK;
            aquariumColor = mediumBlue;
            waterColor = orchid;
            airColor = turquoise;
            emptyColor = Color.WHITE;
        }
        if (puzzleTheme == 2) {
            gridColor = Color.BLACK;
            aquariumColor = VERY_LIGHT_RED;
            waterColor = VERY_LIGHT_GREEN;
            airColor = VERY_LIGHT_BLUE;
            emptyColor = Color.WHITE;
        }
        if (puzzleTheme == 3) {
            gridColor = Color.BLACK;
            aquariumColor = sienna;
            waterColor = peru;
            airColor = navajo;
            emptyColor = Color.WHITE;
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
