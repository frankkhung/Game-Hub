package fall2018.csc2017.gamehub.TowerOfHano;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import fall2018.csc2017.gamehub.UserBoardManager;
import fall2018.csc2017.gamehub.UserManager;
/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {
    /**
     * The user manager
     */
    private UserManager userManager;

    /**
     * The score Board Manager
     */
    private ScoreboardManager scoreboardManager;

    /**
     * indicate the game Type
     */
    private String gameType;

    /**
     * temp save files for each user
     */
    private HashMap<String, Object> userBoards;

    /**
     * The tower Manager
     */
    private TowerOfHanoManager towerManager;

    private RadioGroup rg;

    private boolean checked = false;

    /**
     * same as gameType, indicate the game size
     */
    private int numDisks;

    /**
     * The constructor for the activity
     * @param savedInstanceState pass in the position
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        setContentView(fall2018.csc2017.gamehub.R.layout.activity_hano_starting_);
        rg = (RadioGroup) findViewById(fall2018.csc2017.gamehub.R.id.hanorg);

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
            case fall2018.csc2017.gamehub.R.id.hanoradioButton3:
                numDisks = 3;
                gameType = "hano3";
                checked = true;
                break;

            case fall2018.csc2017.gamehub.R.id.hanoradioButton4:
                numDisks = 4;
                gameType = "hano4";
                checked = true;
                break;

            case fall2018.csc2017.gamehub.R.id.hanoradioButton5:
                numDisks = 5;
                gameType = "hano5";
                checked = true;
                break;
        }
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(fall2018.csc2017.gamehub.R.id.hanoStartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked) {
                    towerManager = new TowerOfHanoManager(numDisks);
                    switchToGame();
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
        Button loadButton = findViewById(fall2018.csc2017.gamehub.R.id.hanoLoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(UserBoardManager.SAVE_FILENAME);
                if ((userBoards.get(userManager.getUser().getUserName()+"TH")) != null){
                    towerManager = (TowerOfHanoManager) userBoards.get(userManager.getUser().getUserName() + "TH");
                    makeToastLoadedText();
                    switchToGame();
                }else{
                    chooseNewToast();
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
     * Activate the load auto button.
     */
    private void addLoadAutoButtonListener() {
        Button loadAutoButton = findViewById(fall2018.csc2017.gamehub.R.id.hanoLoadAutoSave);
        loadAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(UserBoardManager.TEMP_SAVE_FILENAME);
                if ((userBoards.get(userManager.getUser().getUserName()+"TH")) != null){
                    towerManager = (TowerOfHanoManager)userBoards.get(userManager.getUser().getUserName() + "TH");
                    makeToastLoadedText();
                    switchToGame();
                }else{
                    chooseNewToast();
                }
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent game = new Intent(this, fall2018.csc2017.gamehub.TowerOfHano.GameActivity.class);
        game.putExtra("userManager",userManager);
        game.putExtra("scoreboardManager",scoreboardManager);
        game.putExtra("towerManager",towerManager);
        game.putExtra("gameType",gameType);
        startActivity(game);
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
        Button scoreButton = findViewById(fall2018.csc2017.gamehub.R.id.hanoScoreButton);
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
        Intent score = new Intent(this, ShowScoreboardChoiceHano.class);
        score.putExtra("userManager",userManager);
        score.putExtra("scoreboardManager",scoreboardManager);
        startActivity(score);
    }

    /**
     * Activate the return to game launch centre button.
     */
    private void addLaunchButtonListener() {
        Button LaunchButton = findViewById(R.id.hanoReturnToLaunch);
        LaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLaunch();
            }
        });
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
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(UserBoardManager.TEMP_SAVE_FILENAME);
    }


    /**
     * Load the towermanager from fileName.
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
        userBoards.put(userManager.getUser().getUserName()+"TH", towerManager);
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
