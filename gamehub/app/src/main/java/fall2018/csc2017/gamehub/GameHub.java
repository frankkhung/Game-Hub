package fall2018.csc2017.gamehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import fall2018.csc2017.gamehub.MatchingGame.StartingActivityMatch;

/**
 * Creates a page that allows users to choose which game they want to play. There will be multiple
 * games in the future.
 */
public class GameHub extends AppCompatActivity {
    /**
     * gameSelected is a boolean that shows whether the game is selected as a button
     */
    int gameSelected = 0;
    private ScoreboardManager scoreboardManager;
    private UserManager userManager;
    private UserBoardManager userBoardManager;

    /**
     * The button for choosing the game
     * @param savedInstanceState the state that passes in the state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fall2018.csc2017.gamehub.R.layout.activity_game_hub);
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        addSlidingTileButtonListener();
        addMatchingButtonListener();
        addHanoButtonListener();
        addPlayButtonListener();
        if (scoreboardManager == null){
            openScoreboardManager(ScoreboardManager.SCOREBOARD_FILE);
        }
        if (userBoardManager == null){
            openUserBoard();
        }
    }

    private void openScoreboardManager(String fliePath){
        try{
            scoreboardManager = new ScoreboardManager(this,fliePath);
            scoreboardManager.saveToFile(this,fliePath);
        }
        catch (IOException e){
            System.out.println("can't read or open file!");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            System.out.println("cant find  class!");
            e.printStackTrace();
        }
    }

    private void openUserBoard(){
        try{
            userBoardManager = new UserBoardManager(this);
        }
        catch (IOException e){
            System.out.println("can't read or open file!");
            e.printStackTrace();
        }
    }


    /**
     * The button that links to another page that starts the game
     */
    private void addPlayButtonListener(){
        Button playButton = findViewById(fall2018.csc2017.gamehub.R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (gameSelected) {
                    case 0:
                        //TODO: make it display text saying "please select a game first"
                        break;

                    case 1:
                        Intent sliding = new Intent(GameHub.this, fall2018.csc2017.gamehub.StartingActivity.class);
                        sliding.putExtra("userManager",userManager);
                        sliding.putExtra("scoreboardManager",scoreboardManager);
                        //sliding.putExtra("userBoardManager",userBoardManager);
                        startActivity(sliding);
                        break;

                    case 2:
                        Intent matching = new Intent(GameHub.this, StartingActivityMatch.class);
                        matching.putExtra("userManager",userManager);
                        matching.putExtra("scoreboardManager",scoreboardManager);
                        //matching.putExtra("userBoardManager",userBoardManager);
                        startActivity(matching);
                        break;

                    case 3://Tower of hanoi
                        Intent hano = new Intent(GameHub.this, fall2018.csc2017.gamehub.TowerOfHano.StartingActivity.class);
                        hano.putExtra("userManager",userManager);
                        hano.putExtra("scoreboardManager",scoreboardManager);
                        //hano.putExtra("userBoardManager",userBoardManager);
                        startActivity(hano);
                        break;
                }
            }
        });
    }

    /**
     * Button listener for sliding tile game
     */
    private void addSlidingTileButtonListener() {
        ImageButton gameImageButton = findViewById(fall2018.csc2017.gamehub.R.id.imageButton);
        gameImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSelected = 1;
                ImageView highLightImage=(ImageView) findViewById(R.id.greenlight);
                highLightImage.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Button listener for matching game
     */
    private void addMatchingButtonListener() {
        ImageButton gameImageButton = findViewById(fall2018.csc2017.gamehub.R.id.imageButton2);
        gameImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSelected = 2;
                ImageView highLightImage=(ImageView) findViewById(R.id.greenlight);
                highLightImage.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Button listener for tower Hano
     */
    private void addHanoButtonListener() {
        ImageButton gameImageButton = findViewById(fall2018.csc2017.gamehub.R.id.imageButton3);
        gameImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSelected = 3;
                ImageView highLightImage=(ImageView) findViewById(R.id.greenlight);
                highLightImage.setVisibility(View.VISIBLE);
            }
        });
    }

}
