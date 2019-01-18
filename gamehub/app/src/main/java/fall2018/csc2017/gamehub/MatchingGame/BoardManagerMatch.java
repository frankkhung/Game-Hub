package fall2018.csc2017.gamehub.MatchingGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import android.os.Handler;


/**
 * Manage a boardMatch, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManagerMatch implements Serializable {

    /**
     * The boardMatch being managed.
     */
    private long time = 0;
    private BoardMatch boardMatch;
    private Stack<Integer> flipped = new Stack();


    /**
     * Manage a boardMatch that has been pre-populated.
     *
     * @param boardMatch the boardMatch
     */
    BoardManagerMatch(BoardMatch boardMatch) {
        this.boardMatch = boardMatch;
    }

    /**
     * Return the current boardMatch.
     */
    BoardMatch getBoardMatch() {
        return boardMatch;
    }

    /**
     * Manage a new shuffled boardMatch.
     */
    BoardManagerMatch(int numRows, int numCols) {
        List<Card> cards = new ArrayList<>();
        final int numCards = numCols * numRows;
        for (int cardNum = 0; cardNum != numCards; cardNum++) {
            cards.add(new Card(cardNum));
        }

        Collections.shuffle(cards);
        int i = 0;
        this.boardMatch = new BoardMatch(cards);
    }

    /**
     * Return whether all the cards are flipped.
     *
     * @return True if all are flipped over, False if still have some unmatched
     */
    boolean puzzleSolved() {
        boolean solved = true;
        int index = 1;
        for (Card card : boardMatch) {
            if (!card.getState()) {
                return false;
            }
            index++;
        }
        return solved;
    }

    /**
     * Setter method for the time
     *
     * @param timeStopped the time that's manage to stop
     */
    void setTime(long timeStopped){
        time = timeStopped;
    }

    /**
     * getter method for the time
     *
     * @return time
     */
    long getTime(){
        return time;
    }

    /**
     * Check if the card can be flipped over (If the card is facing down).
     *
     * @param position the card to check
     * @return whether the card is flipped.
     */
    boolean isValidTap(int position) {
        int row = position / boardMatch.getNumRows();
        int col = position % boardMatch.getNumCols();
        return !boardMatch.getCard(row, col).getState();
    }


    /**
     * Process a touch at position in the boardMatch, flipping the card as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        final int row = position / boardMatch.getNumRows();
        final int col = position % boardMatch.getNumCols();
        if (isValidTap(position)){
            boardMatch.front(row, col);
            flipped.push(position);
        }if (flipped.size() == 2){ //Can't test this portion due to android method used
            int card1 = flipped.pop();
            final int card2 = flipped.pop();
            delayFlip(row, col, card1, card2); }
    }

    /**
     * the flip back method for the delay on the card
     *
     * @param row row of the board
     * @param col col of the board
     * @param card1 card of the board
     * @param card2 card of the board
     */
    private void delayFlip(final int row, final int col, int card1, final int card2) {
        if (!checkCards(card1, card2)){
            //Can't test due to use of Handler which requires an mock since its android related.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boardMatch.back(row, col);
                    boardMatch.back(card2/ boardMatch.getNumRows(), card2% boardMatch.getNumCols());
                }
            }, 450); }
    }

    /**
     * Check if two cards are the same (if they have the same background).
     * @param card1Pos the position of card 1
     * @param card2Pos the position of card 2
     * @return whether the 2 cards are the same.
     */
    public boolean checkCards(int card1Pos, int card2Pos){
        int row1 = card1Pos / boardMatch.getNumRows();
        int col1 = card1Pos % boardMatch.getNumCols();
        int row2 = card2Pos / boardMatch.getNumRows();
        int col2 = card2Pos % boardMatch.getNumCols();
        return boardMatch.getCard(row1,col1).getBackground() == boardMatch.getCard(row2,col2).getBackground();
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

