package fall2018.csc2017.gamehub;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer{
    private Chronometer chronometer;
    private boolean isRunning = false;
    private long timeElapsed;
    private double scoreInDouble;

    /**
     * user manager
     */
    private UserManager userManager;

    /**
     * The score Board Manager
     */
    private ScoreboardManager scoreboardManager;

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * save files for each user
     */
    public HashMap<String, BoardManager> userBoards;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    /**
     * Grid View and calculated column height and width based on device size
     * */
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        boardManager = (BoardManager)getIntent().getSerializableExtra("boardManager");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        loadFromFile(UserBoardManager.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(fall2018.csc2017.gamehub.R.layout.activity_main);
        chronometer = findViewById(fall2018.csc2017.gamehub.R.id.chronometer);
        startTime();
        addSaveButtonListener();
        Button undoButton = findViewById(fall2018.csc2017.gamehub.R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int undoCount = boardManager.getUndoCount();
                if (undoCount >= boardManager.getMaxUndo()){
                    toastUndo();
                }
                else {
                    boardManager.setUndoCount(undoCount+1);
                    boardManager.undo();
                }
            }
        });


        // Add View to activity
        gridView = findViewById(fall2018.csc2017.gamehub.R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getNumCols();
                        columnHeight = displayHeight / boardManager.getBoard().getNumRows();

                        display();
                    }
                });
    }
    public void startTime(){
        if (!isRunning){
            chronometer.setBase(SystemClock.elapsedRealtime()-boardManager.getTime());
            chronometer.start();
            isRunning = true;
        }
    }
    public void stopTime(){
        if (isRunning){
            chronometer.stop();
            boardManager.setTime(SystemClock.elapsedRealtime()-chronometer.getBase());
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
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNumRows(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRows();
            int col = nextPos % boardManager.getBoard().getNumCols();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
            saveToFile(UserBoardManager.TEMP_SAVE_FILENAME);
        }
        if (boardManager.puzzleSolved()){
            stopTime();
            scoreInDouble = boardManager.getScore();
            String tileNum = "";
            if (boardManager.getBoard().getNumCols() == 3){
                tileNum = "tile1";
            }else if (boardManager.getBoard().getNumCols() == 4){
                tileNum = "tile2";
            }else{
                tileNum = "tile3";
            }
            System.out.println(scoreboardManager.getSB());
            scoreboardManager.updateScoreBoard(tileNum,userManager.getUser().getUserName(),scoreInDouble);
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
    public void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userBoards = (HashMap<String, BoardManager>) input.readObject();
                inputStream.close();

            }
        } catch (FileNotFoundException e) {
            Log.e("ST activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("ST activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("ST activity", "File contained unexpected data type: " + e.toString());
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
            boardManager.setTime(timeElapsed);
            userBoards.put(userManager.getUser().getUserName()+"ST", boardManager);
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
        Intent tmp = new Intent(this, SlidingTileEnd.class);
        tmp.putExtra("scoreInDouble",scoreInDouble);
        tmp.putExtra("userManager",userManager);
        tmp.putExtra("scoreboardManager",scoreboardManager);
        finish();
        startActivity(tmp);
    }
    private void toastUndo(){
        Toast.makeText(this, "No more Undo", Toast.LENGTH_LONG).show();
    }

}
