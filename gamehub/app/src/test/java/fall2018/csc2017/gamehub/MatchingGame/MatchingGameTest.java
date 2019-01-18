package fall2018.csc2017.gamehub.MatchingGame;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MatchingGameTest {

    BoardManagerMatch boardManagerMatch;

    /**
     * Make a set of cards not randomized. Matching one is adjacent.
     * @return a set of cards that are not randomized.
     */
    public List<Card> makeCards(int numRows) {
        List<Card> cards = new ArrayList<>();
        final int numCards = numRows*numRows;
        for (int cardNum = 0; cardNum != numCards; cardNum++){
            Card card = new Card(cardNum+1,cardNum);
            assertEquals(cardNum, card.getBackground());
            assertNotNull(card.getDisplay());
            cards.add(card);
        }
        return cards;
    }


    /**
     * Make a set of cards not randomized. Matching one is adjacent. Checks if cards made have
     * background, ID, display.
     * @return a set of cards that are not randomized.
     */
    @Test
    public void makeCards6x6() {
        List<Card> cards = new ArrayList<>();
        final int numCards = 6*6;
        for (int cardNum = 0; cardNum != numCards; cardNum++){
            Card card = new Card(cardNum);
            assertNotNull(card.getBackground());
            assertNotNull(card.getDisplay());
            assertFalse(card.getDisplay()==card.getBackground());
            assertEquals(cardNum+1, card.getId());
            assertEquals(0,card.compareTo(card));
        }
    }

    /**
     * Flip all cards to make puzzle solved.
     */
    public void flipAllFront(int numRows){
        List<Card> cards = makeCards(numRows);
        for (Card card: cards){
            card.flipFront();
            assertTrue(card.getState());
        }
        BoardMatch boardMatch = new BoardMatch(cards);
        boardManagerMatch = new BoardManagerMatch(boardMatch);
    }

    /**
     * Check if flip back function flips all cards back.
     * @param numRows number of rows
     */
    public void flipAllBack(int numRows){
        List<Card> cards = makeCards(numRows);
        for (Card card: cards){
            card.flipBack();
            assertFalse(card.getState());
        }
        BoardMatch boardMatch = new BoardMatch(cards);
        boardManagerMatch = new BoardManagerMatch(boardMatch);
    }

    /**
     * Check if flipping front and back function in BoardMatch change the state of the card.
     */
    @Test
    public void flipOneBackFront(){
        BoardMatch boardMatch = new BoardMatch(makeCards(4));
        boardMatch.front(0,0);
        assertTrue(boardMatch.getCard(0,0).getState());
        assertTrue(boardMatch.getCard(0,0).getDisplay()
                == boardMatch.getCard(0, 0).getBackground());
        boardMatch.back(0,0);
        assertFalse(boardMatch.getCard(0,0).getState());
        assertTrue(boardMatch.getCard(0,0).getDisplay()
                != boardMatch.getCard(0, 0).getBackground());
    }

    /**
     * Check if IsSolved function works by testing
     */
    @Test
    public void testIsSolved() {
        BoardMatch boardMatch = new BoardMatch(makeCards(4));
        boardManagerMatch = new BoardManagerMatch(boardMatch);
        assertFalse(boardManagerMatch.puzzleSolved());
        flipAllFront(4);
        assertTrue(boardManagerMatch.puzzleSolved());
        boardManagerMatch.getBoardMatch().back(0,0);
        assertFalse(boardManagerMatch.puzzleSolved());
        boardManagerMatch.getBoardMatch().back(2,1);
        assertFalse(boardManagerMatch.puzzleSolved());
        boardManagerMatch.getBoardMatch().front(0,0);
        boardManagerMatch.getBoardMatch().front(2,1);
        assertTrue(boardManagerMatch.puzzleSolved());
        flipAllBack(4);
        assertFalse(boardManagerMatch.puzzleSolved());
    }

    /**
     * Check if isValidTap only finds those cards that have card_back.png as a valid tap.
     */
    @Test
    public void isValidTap() {
        BoardMatch boardMatch = new BoardMatch(makeCards(4));
        boardManagerMatch = new BoardManagerMatch(boardMatch);
        assertTrue(boardManagerMatch.isValidTap(0));
        assertTrue(boardManagerMatch.isValidTap(15));
        flipAllFront(4);
        assertFalse(boardManagerMatch.isValidTap(0));
        assertFalse(boardManagerMatch.isValidTap(15));
    }

    /**
     * Check if touchMove works for 4x4 game
     */
    @Test
    public void touchMove4x4() {
        BoardMatch boardMatch = new BoardMatch(makeCards(4));
        boardManagerMatch = new BoardManagerMatch(boardMatch);
        boardManagerMatch.touchMove(15);
        assertTrue(boardManagerMatch.getBoardMatch().getCard(3, 3).getState());
    }

    /**
     * Check if touchMove works for 6x6 game
     */
    @Test
    public void touchMove6x6(){
        BoardMatch boardMatch = new BoardMatch(makeCards(6));
        boardManagerMatch = new BoardManagerMatch(boardMatch);
        boardManagerMatch.getBoardMatch().setGrid(6);
        boardManagerMatch.touchMove(35);
        assertTrue(boardManagerMatch.getBoardMatch().getCard(5,5).getState());
    }

    /**
     * Check if boardManager is created with a board that has the right number of cols and rows.
     */
    @Test
    public void checkBoardManagerCreated(){
        boardManagerMatch = new BoardManagerMatch(4,4);
        assertNotNull(boardManagerMatch.getBoardMatch());
        assertTrue(boardManagerMatch.getBoardMatch().getNumCols() == 4);
        assertTrue(boardManagerMatch.getBoardMatch().getNumRows() == 4);
    }

    /**
     * Check if checkCards work. True if 2 cards that have same background, false if not.
     */
    @Test
    public void checkCardsTest(){
        BoardMatch boardMatch = new BoardMatch(makeCards(4));
        boardManagerMatch = new BoardManagerMatch(boardMatch);
        assertFalse(boardManagerMatch.checkCards(0, 2));
        assertTrue(boardManagerMatch.checkCards(0,0));
    }

    /**
     * Check if score is properly converted to a double.
     */
    @Test
    public void scoreInDouble(){
        BoardMatch boardMatch = new BoardMatch(makeCards(4));
        boardManagerMatch = new BoardManagerMatch(boardMatch);
        boardManagerMatch.setTime(1000);
        assertTrue(boardManagerMatch.getScore()-1 == 0);
    }
}