import java.util.ArrayList;

public class SudokuPuzzleGenerator extends ConsoleProgram
{
    public void run()
    {
        long startTime = System.currentTimeMillis();
        int[][] board = new int[9][9];
        goBack(board);
        printBoard(board, startTime);
    }
    
    public static void printBoard(int[][] board, long startTime) {
        System.out.println("Solved Sudoku Puzzle:");
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
        for (int r = 0; r < board.length; r++) {
            if (r % 3 == 0) {
                System.out.println("+-------+-------+-------+");
            }
            System.out.print("| ");
            for (int c = 0; c < board[0].length; c+=3) {
                if (c == 6) {
                    System.out.print(board[r][c]+" "+board[r][c+1]+" "+board[r][c+2]+" |");
                } else {
                    System.out.print(board[r][c]+" "+board[r][c+1]+" "+board[r][c+2]+" | ");
                }
            }
            System.out.println(); 
        }
        System.out.println("+-------+-------+-------+");
    }
    
    public static boolean checkIfSafe(int[][] board, int row, int col, int num) {
        //check if row safe
        for (int c = 0; c < board[0].length; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }
        //check if column safe
        for (int r = 0; r < board.length; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }
        //check if 3x3 grid safe
        int gridRow = (row / 3) * 3; //initializing top left corner of grid: row index
        int gridCol = (col / 3) * 3; //initializing top left corner of grid: column index
        for (int r = gridRow; r < gridRow + 3; r++) {
            for (int c = gridCol; c < gridCol + 3; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean goBack(int[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                //if unmodified
                if (board[r][c] == 0) {
                    //shuffled list of integers 1-9
                    ArrayList<Integer> list = shuffleList();
                    for (int i = 0; i < list.size(); i++) {
                        //get one of the random integers
                        int num = list.get(i);
                        //if it's safe, let it occupy the position
                        if (checkIfSafe(board, r, c, num)) {
                            board[r][c] = num;
                            //if we go back recursively and rest of board is safe, return true
                            if (goBack(board)) {
                                return true;
                            }
                            // else, we set it back to unmodified
                            board[r][c] = 0;
                        }
                    }
                    //no numbers from 1-9 worked (dead end), updates the recursion
                    return false;
                }
            }
            
        }
        //base case
        return true;
    }
    
    //fisher-yates shuffle using arraylist 
    public static ArrayList<Integer> shuffleList() {
        ArrayList<Integer> values = new ArrayList<>(9);
        //first, fill list 1-9 in order
        for (int i = 1; i <= 9; i++) {
            values.add(i);
        }
        //each iteration locks in one random element at position l
        for (int l = 8; l > 0; l--) {
            // pick a random index from 0 to l inclusive
            int s = (int)(Math.random() * (l + 1));
            // swap the locked position with the random position
            // everything at index > l is alr locked, won't be touched again
            int temp = values.get(s);
            values.set(s, values.get(l));
            values.set(l, temp);
        }
        return values;
    }

}

