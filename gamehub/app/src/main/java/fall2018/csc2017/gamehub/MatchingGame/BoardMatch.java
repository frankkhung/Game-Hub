package fall2018.csc2017.gamehub.MatchingGame;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;


/**
 * The sliding cards board.
 */
public class BoardMatch extends Observable implements Serializable, Iterable<Card>{

    /**
     * The number of rows.
     */
    private int numRows = 4;

    /**
     * The number of rows.
     */
    private int numCols = 4;

    /**
     * The cards on the board in row-major order.
     */
    private Card[][] cards;

    /**
     * A new board of cards in row-major order.
     * Precondition: len(cards) == NUM_ROWS * NUM_COLS
     *
     * @param cards the cards for the board
     */
    BoardMatch(List<Card> cards) {
        double length = Math.sqrt((double)cards.size());
        Card[][] tempCards = new Card[(int)length][(int)length];
        Iterator<Card> iter = cards.iterator();

        for (int row = 0; row < length; row++) {
            for (int col = 0; col< length; col++) {
                tempCards[row][col] = iter.next();
            }
        }
        this.cards = tempCards;
    }

    /**
     * Return the card at (row, col)
     *
     * @param row the card row
     * @param col the card column
     * @return the card at (row, col)
     */
    Card getCard(int row, int col) {
        return cards[row][col];
    }

    /**
     * Return number of rows
     * @return the number of rows
     */
    int getNumRows(){return numRows;}
    /**
     * Return number of columns
     * @return the number of columns
     */
    int getNumCols(){return numCols;}

    /**
     * Set rows
     */
    void setNumRows(int num){numRows = num;}
    /**
     * Set columns
     */
    void setNumCols(int num){numCols = num;}


    @NonNull
    @Override
    public Iterator<Card> iterator() {
        return new BoardIterator();
    }

    /**
     *
     * Implements Iterator class for Iterable, which is implemented for BoardMatch class
     */
    private class BoardIterator implements Iterator<Card> {

        int nextRow = 0;
        int nextCol = 0;

        @Override
        public boolean hasNext() {
            return nextRow != numRows;
        }

        @Override
        public Card next() {
            Card result = cards[nextRow][nextCol];
            nextCol++;

            if (nextCol == numCols) {
                nextRow++;
                nextCol = 0;
            }
            return result;
        }
    }

    /**
     * helps implement card puzzle game complexity
     *
     * @param gridDimension the size of the grid
     */
    public void setGrid(int gridDimension){
        setNumRows(gridDimension);
        setNumCols(gridDimension);
    }

    /**
     * implement the flip front method for the use in BoardManagerMatch
     *
     * @param row the row on the board
     * @param col the col on the board
     */
    void front(int row, int col){
        this.getCard(row, col).flipFront();
        setChanged();
        notifyObservers();
    }

    /**
     * implement the flip back method for the use in BoardManagerMatch
     *
     * @param row row in the board
     * @param col row in the col
     */
    void back(int row, int col){
        this.getCard(row, col).flipBack();
        setChanged();
        notifyObservers();
    }

}
