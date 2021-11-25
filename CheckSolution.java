/**
 * CheckSolution is a utility class which can check if
 * a board position in an Aquarium puzzle is a solution.
 *
 * @authors Farshad Ghanbari (21334883) and Glen Nguyen (22575354)
 * @version 2020
 */
import java.util.Arrays; 

public class CheckSolution
{
    /**
     * Non-constructor for objects of class CheckSolution
     */
    private CheckSolution(){}

    /**
     * Returns the number of water squares in each row of Aquarium puzzle p, top down.
     */
    public static int[] rowCounts(Aquarium p)
    {
        // TODO 16
        int[] rowCount = new int[p.getSize()];
        for (int i = 0; i < p.getSize(); i++) {
            for (int j = 0; j < p.getSize(); j++) {
                if (p.getSpaces()[i][j] == Space.WATER) {
                    rowCount[i]++;
                }
            }
        }
        return rowCount;
    }

    /**
     * Returns the number of water squares in each column of Aquarium puzzle p, left to right.
     */
    public static int[] columnCounts(Aquarium p)
    {
        // TODO 17
        int[] columnCount = new int[p.getSize()];
        for (int i = 0; i < p.getSize(); i++) {
            for (int j = 0; j < p.getSize(); j++) {
                if (p.getSpaces()[i][j] == Space.WATER) {
                    columnCount[j]++;
                }
            }
        }
        return columnCount;
    }

    /**
     * Returns a 2-int array denoting the collective status of the spaces 
     * in the aquarium numbered t on Row r of Aquarium puzzle p. 
     * The second element will be the column index c of any space r,c which is in t, or -1 if there is none. 
     * The first element will be: 
     * 0 if there are no spaces in t on Row r; 
     * 1 if they're all water; 
     * 2 if they're all not-water; or 
     * 3 if they're a mixture of water and not-water. 
     */
    public static int[] rowStatus(Aquarium p, int t, int r)
    {
        // TODO 18
        int[] rowStatus = new int[2];
        int aquariumCount = 0;
        int waterCount = 0;
        for (int i = 0; i < p.getSize(); i++) {
            // Counts the number of aquariums t in each row
            if (p.getAquariums()[r][i] == t) {
                aquariumCount++;
            }
            // Counts the number of water spaces in each aquarium t
            if (p.getSpaces()[r][i] == Space.WATER && p.getAquariums()[r][i] == t) {
                waterCount++;
            }
        }
        for (int i = 0; i < p.getSize(); i++) {
            if (p.getAquariums()[r][i] == t) {
                // gets the index of the first box in aquarium t
                rowStatus[1] = i;
                break;
            } else {
                // if no aquarium t found in that row returns -1
                rowStatus[1] = -1;
            }
        }
        if (rowStatus[1] == -1) {
            rowStatus[0] = 0;
        } else if (aquariumCount == waterCount){
            rowStatus[0] = 1;
        } else if (waterCount == 0) {
            rowStatus[0] = 2;
        } else {
            rowStatus[0] = 3;
        }
        return rowStatus;
    }

    /**
     * Returns a statement on whether the aquarium numbered t in Aquarium puzzle p is OK. 
     * Every row must be either all water or all not-water, 
     * and all water must be below all not-water. 
     * Returns "" if the aquarium is ok; otherwise 
     * returns the indices of any square in the aquarium, in the format "r,c". 
     */
    public static String isAquariumOK(Aquarium p, int t)
    {
        // TODO 19
        String isAquariumOK = "";
        // Starting from the bottom of the puzzle
        for (int i = p.getSize() - 1; i > 0; i--) {
            if (rowStatus(p, t, i)[0] != 0) {
                // breaks the loop if anothe aquarium is found
                if ( rowStatus(p, t, i - 1)[0] == 0) {
                        break;
                    }
                if ((rowStatus(p, t, i)[0]     == 1 || rowStatus(p, t, i)[0] == 2) 
                  && rowStatus(p, t, i - 1)[0] >=      rowStatus(p, t, i)[0]) {
                    // aquarium is OK if the row above has a greater rowStatus than the row below  
                    isAquariumOK = "";
                } else {
                    isAquariumOK = i + "," + rowStatus(p, t, i)[1];
                    break;
                }
            }
        }
        return isAquariumOK;
    }   

    /**
     * Returns a statement on whether we have a correct solution to Aquarium puzzle p. 
     * Every row and column must have the correct number of water squares, 
     * and all aquariums must be OK. 
     * Returns three ticks if the solution is correct; 
     * otherwise see the LMS page for the expected results. 
     */
    public static String isSolution(Aquarium p)
    {
        // TODO 20
        int size = p.getSize();
        int aquariumCount = p.getAquariums()[size - 1][size - 1];
        String isSolution = "";
        
        // checks to see if water count in rows and columns match their required numbers
        for (int i = 0; i < size; i++) {
            if (rowCounts(p)[i] == p.getRowTotals()[i]) {
                isSolution = "";
            } else {
                isSolution = "Row " + i + " is wrong";
                break;
            }
            if (columnCounts(p)[i] == p.getColumnTotals()[i]) {
                isSolution = "";
            } else {
                isSolution = "Column " + i + " is wrong";
                break;
            }
        }
        // checks the OK status of the aquariums
        if (isSolution == "") {
            for (int i = 1; i <= aquariumCount; i++) {
                if (isAquariumOK(p, i) == "") {
                    isSolution = "\u2713\u2713\u2713";
                } else {
                    isSolution = "The aquarium at " + isAquariumOK(p, i) + " is wrong";
                    break;
                }
            }
        }
        return isSolution;
    }
}
