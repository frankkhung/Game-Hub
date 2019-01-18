package fall2018.csc2017.gamehub.MatchingGame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.gamehub.CustomAdapter;
import fall2018.csc2017.gamehub.R;
import fall2018.csc2017.gamehub.ScoreboardManager;
import fall2018.csc2017.gamehub.StartingActivity;
import fall2018.csc2017.gamehub.UserBoardManager;
import fall2018.csc2017.gamehub.UserManager;

/**
 * The game activity.
 */
public class GameActivityMatch extends AppCompatActivity implements Observer {
    private Chronometer chronometer;
    private boolean isRunning = false;
    private long timeElapsed;
    private double scoreInDouble;
    private UserManager userManager;
    private ScoreboardManager scoreboardManager;

    /**
     * The board manager.
     */
    private BoardManagerMatch boardManagerMatch;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> cardButtons;

    public HashMap<String, BoardManagerMatch> userBoards;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridViewMatch gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateCardButtons();
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        boardManagerMatch = (BoardManagerMatch)getIntent().getSerializableExtra("boardManagerMatch");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        loadFromFile(UserBoardManager.TEMP_SAVE_FILENAME);
        createCardButtons(this);
        setContentView(R.layout.activity_main_matching);
        chronometer = findViewById(R.id.chronometer);
        startTime();
        addSaveButtonListener();
        backListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManagerMatch.getBoardMatch().getNumCols());
        gridView.setBoardManagerMatch(boardManagerMatch);
        boardManagerMatch.getBoardMatch().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManagerMatch.getBoardMatch().getNumCols();
                        columnHeight = displayHeight / boardManagerMatch.getBoardMatch().getNumRows();

                        display();
                    }
                });
    }

    /**
     * The start time for the clock to start counting
     */
    public void startTime(){
        if (!isRunning){
            chronometer.setBase(SystemClock.elapsedRealtime()- boardManagerMatch.getTime());
            chronometer.start();
            isRunning = true;
        }
    }

    /**
     * The end time of for the clock to click
     */
    public void stopTime(){
        if (isRunning){
            chronometer.stop();
            boardManagerMatch.setTime(SystemClock.elapsedRealtime()-chronometer.getBase());
            isRunning = false;

        }
    }
    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(fall2018.csc2017.gamehub.R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(UserBoardManager.SAVE_FILENAME);
                saveToFile(UserBoardManager.TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createCardButtons(Context context) {
        BoardMatch boardMatch = boardManagerMatch.getBoardMatch();
        cardButtons = new ArrayList<>();
        for (int row = 0; row != boardManagerMatch.getBoardMatch().getNumRows(); row++) {
            for (int col = 0; col != boardManagerMatch.getBoardMatch().getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(boardMatch.getCard(row, col).getDisplay());
                this.cardButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateCardButtons() {
        BoardMatch boardMatch = boardManagerMatch.getBoardMatch();
        int nextPos = 0;
        for (Button b : cardButtons) {
            int row = nextPos / boardManagerMatch.getBoardMatch().getNumRows();
            int col = nextPos % boardManagerMatch.getBoardMatch().getNumCols();
            b.setBackgroundResource(boardMatch.getCard(row, col).getDisplay());
            nextPos++;
            saveToFile(UserBoardManager.TEMP_SAVE_FILENAME);
        }
        if (boardManagerMatch.puzzleSolved()){
            stopTime();
            scoreInDouble = boardManagerMatch.getScore();
            String cardNum = "";
            if (boardManagerMatch.getBoardMatch().getNumRows() == 4){
                cardNum = "match4x4";
            }else{
                cardNum = "match6x6";
            }
            scoreboardManager.updateScoreBoard(cardNum,userManager.getUser().getUserName(),scoreInDouble);
            scoreboardManager.saveToFile(this,ScoreboardManager.SCOREBOARD_FILE);
            switchToEnd();
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() { super.onPause(); }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userBoards = (HashMap<String, BoardManagerMatch>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }

    }


    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            timeElapsed = SystemClock.elapsedRealtime()-chronometer.getBase();
            boardManagerMatch.setTime(timeElapsed);
            userBoards.put(userManager.getUser().getUserName()+"M", boardManagerMatch);
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userBoards);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Switch to the SlidingTileEnd view to end game.
     */
    public void switchToEnd() {
        stopTime();
        Intent tmp = new Intent(this, MatchingEnd.class);
        tmp.putExtra("scoreInDouble", scoreInDouble);
        tmp.putExtra("scoreboardManager", scoreboardManager);
        tmp.putExtra("userManager", userManager);
        finish();
        startActivity(tmp);
    }

    private void backListener(){
        Button Button1 = findViewById(R.id.ExitButton);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToStart = new Intent(GameActivityMatch.this,StartingActivityMatch.class);
                backToStart.putExtra("userManager",userManager);
                backToStart.putExtra("scoreboardManager",scoreboardManager);
                startActivity(backToStart);
            }
        });
    }

}
