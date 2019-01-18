package fall2018.csc2017.gamehub;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTileTest {

    /** The board manager for testing. */
    private BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 25;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, tileNum));
        }
        return tiles;
    }

    /**
     * Check if Tile is created properly with specific ID, background,
     */
    @Test
    public void createTile(){
        Tile tile = new Tile (1, 1, 0);
        assertTrue(tile.getBackground() == 1);
        assertTrue(tile.getId() == 1);
        assertEquals(0,tile.compareTo(tile));
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles);
        boardManager = new BoardManager(board);
    }

    /**
     * Check if boardManager is created and has a board that is the right dimensions.
     */
    @Test
    public void setUpNewBoardManager(){
        boardManager = new BoardManager(25);
        assertNotNull(boardManager.getBoard());
        assertEquals(5, boardManager.getBoard().getNumCols());
        assertEquals(5, boardManager.getBoard().getNumRows());
        boardManager = new BoardManager(9);
        assertNotNull(boardManager.getBoard());
        assertEquals(3, boardManager.getBoard().getNumCols());
        assertEquals(3, boardManager.getBoard().getNumRows());
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(24, boardManager.getBoard().getTile(4, 3).getId());
        assertEquals(25, boardManager.getBoard().getTile(4, 4).getId());
        boardManager.getBoard().swapTiles(4, 4, 4, 3);
        assertEquals(25, boardManager.getBoard().getTile(4, 3).getId());
        assertEquals(24, boardManager.getBoard().getTile(4, 4).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertFalse(boardManager.isValidTap(11));
        assertTrue(boardManager.isValidTap(23));
        assertFalse(boardManager.isValidTap(10));
    }


    /**
     * The test for the getter method for the Time
     */
    @Test
    public void testGetAndSetTime() {
        setUpCorrect();
        boardManager.setTime(1);
        assertEquals(1, boardManager.getTime());
    }


    /**
     * The test for touch move to check at the position for some tile
     */
    @Test
    public void testTouchMove() {
        setUpCorrect();
        assertEquals(24, boardManager.getBoard().getTile(4, 3).getId());
        assertEquals(25, boardManager.getBoard().getTile(4, 4).getId());
        boardManager.touchMove(23);
        assertEquals(25, boardManager.getBoard().getTile(4, 3).getId());
        assertEquals(24, boardManager.getBoard().getTile(4, 4).getId());
    }

    /**
     * Test for the Undo by seeing it actually changes and when there's nothing to undo it will not
     * do anything
     */
    @Test
    public void testUndo() {
        setUpCorrect();
        boardManager.touchMove(1);
        assertFalse(boardManager.undo());
        boardManager.touchMove(23);
        boardManager.undo();
        assertFalse(boardManager.undo());
        assertArrayEquals(new int[]{4,3}, boardManager.getBoard().getTileCoordinates(24));
        assertArrayEquals(new int[]{4,4}, boardManager.getBoard().getTileCoordinates(25));
    }

    /**
     * Test for the getter method for the score
     */
    @Test
    public void testGetScore(){
        setUpCorrect();
        boardManager.setTime(1);
        Double score = 0.001;
        assertEquals((Object)score, (Object) boardManager.getScore());

    }

    /**
     * Test for the setter and getter for Undo in general
     */
    @Test
    public void testSetGetUndoCount(){
        setUpCorrect();
        boardManager.setUndoCount(10);
        assertEquals(10, boardManager.getUndoCount());
    }

    /**
     * Test for the setter for max undo
     */
    @Test
    public void testSetGetMaxUndo(){
        setUpCorrect();
        boardManager.setMaxUndo(10);
        assertEquals(10, boardManager.getMaxUndo());
    }
}

