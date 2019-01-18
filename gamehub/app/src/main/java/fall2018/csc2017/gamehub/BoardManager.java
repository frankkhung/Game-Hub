package fall2018.csc2017.gamehub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable, IBoardManager {

    private long time = 0;
    private Board board;
    private Stack<ArrayList<Integer>> stepStack = new Stack<>();
    private int undoCount = 0;
    private int maxUndo = 3;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Return the current number of times you can undo.
     */
    int getUndoCount(){return undoCount;}

    /**
     * Set undo count.
     * @param count is the number of undos possible
     */
    void setUndoCount(int count){undoCount = count;}

    /**
     * Return the max number of undos
     */
    int getMaxUndo() {return maxUndo;}

    /**
     * Set max undo
     * @param max is the number of undos max
     */
    void setMaxUndo(int max){maxUndo = max;}

    /**
     * Manage a new shuffled board.
     */
    BoardManager(int getNumTiles) {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != getNumTiles; tileNum++) {
            tiles.add(new Tile(tileNum, (int)Math.sqrt(getNumTiles)));
        }
        boolean checkSolvable = false;
        while(!checkSolvable) {
            Collections.shuffle(tiles);
            Board tempBoard = new Board(tiles);
            if (tempBoard.checkSolve()){
                checkSolvable = true;
                this.board = tempBoard;
            }
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        int index = 1;
        for (Tile tile : board) {
            if (tile.getId() != index) {
                return false;
            }
            index++; }
        return true;
    }
    public void setTime(long timeStopped){
        time = timeStopped;}
    public long getTime(){
        return time;
    }
    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        int blankId = board.numTiles();
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getNumRows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getNumCols() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }


    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        int blankId = board.numTiles();
        if (isValidTap(position)) {
            int blankTileRow = this.board.getTileCoordinates(blankId)[0];
            int blankTileCol = this.board.getTileCoordinates(blankId)[1];
            this.board.swapTiles(row, col, blankTileRow, blankTileCol);
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(row);
            temp.add(col);
            temp.add(blankTileRow);
            temp.add(blankTileCol);
            stepStack.push(temp);
                setUndoCount(0);

        }
    }


    /**
     * when undo is used it will swap back the tiles and return wither the stack is empty or not to
     * show the toast that indicates if undo is available
     *
     * @return boolean
     */
    public boolean undo() {
        if(!stepStack.empty()){
            ArrayList <Integer> step;
            step = stepStack.pop();
            int blankTileRow = step.get(2);
            int blankTileCol = step.get(3);
            int row = step.get(0);
            int col = step.get(1);
            this.board.swapTiles(blankTileRow, blankTileCol, row, col);
            return true;
        }
        return false;
    }

    /**
     * Get score in double format.
     * @return score in double format.
     */
    public double getScore(){
        long score = getTime();
        String longStr = Long.toString(score);
        Double scoreInDouble = Double.parseDouble(longStr);
        return scoreInDouble/1000;
    }
}
