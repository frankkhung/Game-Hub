package fall2018.csc2017.gamehub;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile>{

    /**
     * The number of rows.
     */
    private int numRows;

    /**
     * The number of rows.
     */
    private int numCols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();
        numRows = (int)Math.sqrt(tiles.size());
        numCols = (int)Math.sqrt(tiles.size());
        this.tiles =  new Tile[numRows][numCols];
        System.out.println(numCols  + "check this       " + numRows);
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    int numTiles() {
        return numRows * numCols;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return the coordinates (row, col) of selected tile
     *
     * @param id
     * @return the coordinates as int[] [row, col]
     */
    int[] getTileCoordinates(int id) {
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                if (this.tiles[row][col].getId() == id) {
                    return new int[]{row, col};
                }

            }
        }
        return new int[]{-1,-1};
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile tempTile = this.tiles[row1][col1];

        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = tempTile;

        setChanged();
        notifyObservers();

    }


    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     *
     * Implements Iterator class for Iterable, which is implemented for Board class
     */
    private class BoardIterator implements Iterator<Tile> {

        int nextRow = 0;
        int nextCol = 0;

        @Override
        public boolean hasNext() {
            return nextRow != numRows;
        }

        @Override
        public Tile next() {
            Tile result = tiles[nextRow][nextCol];
            nextCol++;

            if (nextCol == numCols) {
                nextRow++;
                nextCol = 0;
            }
            return result;

        }
    }
    boolean checkSolve(){
        Tile[] listOfTiles = new Tile[numRows*numCols];
        int indexCounter = 0;
        int inversions = 0;
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                listOfTiles[indexCounter] = tiles[row][col];
                indexCounter+= 1;
            }
        }
        for (int i = 0; i < listOfTiles.length; i++){
            System.out.println("index: " + i + "   value: " + listOfTiles[i].getId());
        }
        inversions = countInversions(listOfTiles);
        System.out.println("NUMBER OF INVERSIONS" + inversions);
        if (numRows%2==1 && inversions%2 == 0) {
            return true;
        }
        else if(numRows%2==0) {
            int blankId = numTiles();
            int blankTileRow = getTileCoordinates(blankId)[0];
            if (blankTileRow %2 == 0 && inversions%2 == 1){
                return true;
            }
            else return blankTileRow % 2 == 1 && inversions % 2 == 0;
        }
        return false;

    }
    private int countInversions(Tile[] tileList){
        int inversions = 0;
        for (int index = 0; index < tileList.length; index++){
            for (int following = index+1; following < tileList.length; following++){
                if(tileList[index].getId() > tileList[following].getId()){
                    if (tileList[index].getId() != tileList.length) {
                        inversions += 1;
                    }
                }
            }
        }
        return inversions;
    }
    int getNumRows(){
        return numRows;
    }
    int getNumCols(){
        return numCols;
    }
}
