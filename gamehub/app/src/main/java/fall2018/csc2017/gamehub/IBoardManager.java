package fall2018.csc2017.gamehub;

public interface IBoardManager {




    /**
     * Check if puzzle is solved
     * @return True if puzzle solved, false if not
     */
    boolean puzzleSolved();

    /**
     * Process a touch at a specific position on the screen. Performing the action if valid.
     * @param position the position on the screen
     */
    void touchMove(int position);

    /**
     * Check if a tap of the screen is valid
     * @param position the position on the screen
     * @return true if it is a valid tap, false if not
     */
    boolean isValidTap(int position);

    /**
     * Gets the score of the game
     * @return the score of the game
     */
    double getScore();
}
