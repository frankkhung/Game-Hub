package fall2018.csc2017.gamehub.MatchingGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import fall2018.csc2017.gamehub.GameHub;
import fall2018.csc2017.gamehub.R;
import fall2018.csc2017.gamehub.ScoreboardManager;
import fall2018.csc2017.gamehub.TowerOfHano.TowerOfHanoManager;
import fall2018.csc2017.gamehub.UserBoardManager;
import fall2018.csc2017.gamehub.UserManager;

public class StartingActivityMatch extends AppCompatActivity {
    /**
     * The user manager
     */
    private UserManager userManager;

    /**
     * The score board manager
     */
    private ScoreboardManager scoreboardManager;

    /**
     * The match board manager.
     */
    public BoardManagerMatch boardManagerMatch;

    /**
     * The temp save files for each user
     */
    private HashMap<String, Object> userBoards;

    private RadioGroup rg;

    private boolean checked = false;

    /**
     * indicate the size of game
     */
    private int gridSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_starting);
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        rg = (RadioGroup) findViewById(R.id.MatchRG);
        addStartButtonListener();
        addLoadButtonListener();
        addScoreButtonListener();
        addLaunchButtonListener();
        addLoadAutoButtonListener();
    }

    public void onRadioButtonClicked(View view){
        switch (view.getId()) {
            case R.id.RadioMatch4x4:
                gridSize = 4;
                checked = true;
                break;

            case R.id.RadioMatch6x6:
                gridSize = 6;
                checked = true;
                break;
        }
    }

    /**
     * The card complexity which sets the grid size
     * @param gridSize the grid size that passes in to set the number of  columns and rows
     */
    private void setDifficulty(int gridSize){
        boardManagerMatch = new BoardManagerMatch(gridSize, gridSize);
        boardManagerMatch.getBoardMatch().setNumCols(gridSize);
        boardManagerMatch.getBoardMatch().setNumRows(gridSize);
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(fall2018.csc2017.gamehub.R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked) {
                    setDifficulty(gridSize);
                    switchToGame();
                }else {
                    gridSizeToast();
                }
            }
        });
    }

    /**
     * Display that grid size needs to be set.
     */
    private void gridSizeToast() {
        Toast.makeText(this, "Choose grid size", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that file saved. Choose new game.
     */
    private void chooseNewToast() {
        Toast.makeText(this, "No previous save. Choose new game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(fall2018.csc2017.gamehub.R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(UserBoardManager.SAVE_FILENAME);
                if ((userBoards.get(userManager.getUser().getUserName()+'M')) != null){
                    boardManagerMatch = (BoardManagerMatch) userBoards.get(userManager.getUser().getUserName() + "M");
                    makeToastLoadedText();
                    switchToGame();
                }else{
                    chooseNewToast();
                }

            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the button going to scoreboard
     */
    private void addScoreButtonListener() {
        Button scoreButton = findViewById(fall2018.csc2017.gamehub.R.id.ScoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScore();
            }
        });
    }

    /**
     * Switch to Scoreboard view.
     */
    private void switchToScore(){
        Intent score = new Intent(this, scoreboardChoiceMatch.class);
        score.putExtra("scoreboardManager",scoreboardManager);
        startActivity(score);
    }

    /**
     * Activate the return to game launch centre button.
     */
    private void addLaunchButtonListener() {
        Button LaunchButton = findViewById(fall2018.csc2017.gamehub.R.id.ReturnToLaunch);
        LaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLaunch();
            }
        });
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() { super.onResume(); }

    /**
     * Switch to the GameActivityMatch view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivityMatch.class);
        tmp.putExtra("userManager",userManager);
        tmp.putExtra("boardManagerMatch", boardManagerMatch);
        tmp.putExtra("scoreboardManager", scoreboardManager);
        startActivity(tmp);
    }

    /**
     * Switch to GameLaunchHub view to return to game launch centre.
     */
    private void switchToLaunch(){
        Intent launch = new Intent(this, GameHub.class);
        launch.putExtra("userManager",userManager);
        startActivity(launch);
    }

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
                userBoards = (HashMap<String, Object>) input.readObject();
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
        userBoards.put(userManager.getUser().getUserName()+"M", boardManagerMatch); //TODO:Username
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userBoards);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void addLoadAutoButtonListener() {
        Button loadAutoButton = findViewById(R.id.AutoButton);
        loadAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(UserBoardManager.TEMP_SAVE_FILENAME);
                if ((userBoards.get(userManager.getUser().getUserName()+"M")) != null){
                    boardManagerMatch = (BoardManagerMatch)userBoards.get(userManager.getUser().getUserName() + "M");
                    makeToastLoadedText();
                    switchToGame();
                }else{
                    chooseNewToast();
                }
            }
        });
    }
}
