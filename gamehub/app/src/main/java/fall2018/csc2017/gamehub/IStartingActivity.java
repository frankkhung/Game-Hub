package fall2018.csc2017.gamehub;

import android.os.Bundle;

public interface IStartingActivity {

    /**
     * Button to open new game.
     */
    void addStartButtonListener();

    /**
     * Button to load previous game if there is one. If not toast error message pops.
     */
    void addLoadButtonListener();

    /**
     * Button to go to scoreboard options
     */
    void addScoreButtonListener();

    /**
     * Button to go back to game hub
     */
    void addLaunchButtonListener();

    /**
     * Toaster that pops up when game is loaded
     */
    void makeToastLoadedText();

    /**
     * Toaster that pops when there is no previous game and load button is clicked.
     */
    void chooseNewToast();

    /**
     * Switches activity to scoreboard options.
     */
    void switchToScore();

    /**
     * Switches activity to GameActivity.
     */
    void switchToGame();

    /**
     * Switches activity to GameHub.
     */
    void switchToLaunch();

}
