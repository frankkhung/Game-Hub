package fall2018.csc2017.gamehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {
    private UserManager userManager;
    private ScoreboardManager scoreboardManager;
    private EditText undoNumber;
    private int undo;

    /**
     * indicate gameType
     */
    private int numTiles;

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The save file for each user
     */
    private HashMap<String,Object> userBoards;

    private RadioGroup rg;

    private boolean checked = false;

    /**
     * The constructor for the activity
     * @param savedInstanceState pass in the position
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        userManager.loadFromFile(this,"save_user");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        setContentView(fall2018.csc2017.gamehub.R.layout.activity_starting_);
        rg = (RadioGroup) findViewById(fall2018.csc2017.gamehub.R.id.rg);
        undoNumber = (EditText)findViewById(R.id.undoCounter);

        addStartButtonListener();
        addLoadButtonListener();
        addScoreButtonListener();
        addLaunchButtonListener();
        addLoadAutoButtonListener();
    }

    /**
     * Add the functions to the radio buttons
     * @param view the position is clicked or touched
     */
    public void onRadioButtonClicked(View view){
        switch (view.getId()) {
            case fall2018.csc2017.gamehub.R.id.radioButton3:
                numTiles = 9;
                checked = true;
                break;

            case fall2018.csc2017.gamehub.R.id.radioButton4:
                numTiles = 16;
                checked = true;
                break;

            case fall2018.csc2017.gamehub.R.id.radioButton5:
                numTiles = 25;
                checked = true;
                break;
        }
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
                    boardManager = new BoardManager(numTiles);
                    String undoStr = undoNumber.getText().toString();
                    if (!undoStr.isEmpty()) {
                        undo = Integer.parseInt(undoStr);
                        boardManager.setMaxUndo(undo);
                        switchToGame();
                    }
                    else{
                        undoToast();
                    }
                }else {
                    gridSizeToast();
                }
            }
        });
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
                if (userBoards.get(userManager.getUser().getUserName()+"ST") != null){
                    boardManager = (BoardManager)userBoards.get(userManager.getUser().getUserName() + "ST");
                    makeToastLoadedText();
                    switchToGame();
                }
                else {
                    chooseNewToast();
                }
            }
        });
    }
    /**
     * Display the choose undo counts toast
     */
    private void undoToast(){
        Toast.makeText(this,"Please input a valid number of undo's",
                Toast.LENGTH_SHORT).show();
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
     * Activate the load auto button.
     */
    private void addLoadAutoButtonListener() {
        Button loadAutoButton = findViewById(fall2018.csc2017.gamehub.R.id.LoadAutoSave);
        loadAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(UserBoardManager.TEMP_SAVE_FILENAME);
                if ((userBoards.get(userManager.getUser().getUserName()+"ST")) != null){
                    boardManager = (BoardManager)userBoards.get(userManager.getUser().getUserName()+"ST");
                    makeToastLoadedText();
                    switchToGame();
                }
                else {
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
        Intent score = new Intent(this, ShowScoreboardChoice.class);
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
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("userManager",userManager);
        tmp.putExtra("scoreboardManager",scoreboardManager);
        tmp.putExtra("boardManager",boardManager);
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
        System.out.println(userManager);
        userBoards.put(userManager.getUser().getUserName()+"ST", boardManager);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userBoards);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
