package fall2018.csc2017.gamehub.TowerOfHano;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import fall2018.csc2017.gamehub.R;
import fall2018.csc2017.gamehub.ScoreboardManager;
import fall2018.csc2017.gamehub.UserBoardManager;
import fall2018.csc2017.gamehub.UserManager;

public class GameActivity extends AppCompatActivity {
    /**
     * temp save files for each user
     */
    private HashMap<String, TowerOfHanoManager> userBoards;

    /**
     * The user manager
     */
    private UserManager userManager;

    /**
     * The score board manager
     */
    private ScoreboardManager scoreboardManager;

    /**
     * the tower manager
     */
    private TowerOfHanoManager towerManager;

    /**
     * indicate the game type
     */
    private String gameType;


    private DrawRingsAndBackground ringSet1;
    private DrawRingsAndBackground ringSet2;
    private DrawRingsAndBackground ringSet3;
    private DrawBrick ring1;
    private DrawBrick ring2;
    private DrawBrick ring3;
    private LinearLayout tower1;
    private LinearLayout tower2;
    private LinearLayout tower3;
    private LinearLayout brick1;
    private LinearLayout brick2;
    private LinearLayout brick3;
    private TextView step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        towerManager = (TowerOfHanoManager)getIntent().getSerializableExtra("towerManager");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        loadFromFile(UserBoardManager.TEMP_SAVE_FILENAME);
        gameType = (String)getIntent().getSerializableExtra("gameType");
        System.out.println(gameType);
        setContentView(R.layout.activity_towerofhano_game);
        tower1 = findViewById(R.id.tower1);
        tower2 = findViewById(R.id.tower2);
        tower3 = findViewById(R.id.tower3);

        brick1 = findViewById(R.id.brick1);
        brick2 = findViewById(R.id.brick2);
        brick3 = findViewById(R.id.brick3);

        step = findViewById(fall2018.csc2017.gamehub.R.id.hanoScore);

        ringSet1 = new DrawRingsAndBackground(this, towerManager.getTower1(), towerManager.getSizeOfGame());
        ringSet2 = new DrawRingsAndBackground(this, towerManager.getTower2(), towerManager.getSizeOfGame());
        ringSet3 = new DrawRingsAndBackground(this, towerManager.getTower3(), towerManager.getSizeOfGame());

        ring1 = new DrawBrick(this, 1, towerManager.getBricks(), towerManager.getSizeOfGame());
        ring2 = new DrawBrick(this, 2, towerManager.getBricks(), towerManager.getSizeOfGame());
        ring3 = new DrawBrick(this, 3, towerManager.getBricks(), towerManager.getSizeOfGame());

        step.setText(Integer.toString(towerManager.getStep()));

        init();
        tower1Listener();
        tower2Listener();
        tower3Listener();
        undoListener();
        saveListener();
        backListener();
    }

    /**
     * init the towers, draw they on screen
     */
    private void init() {
        ringSet1.invalidate();
        ringSet2.invalidate();
        ringSet3.invalidate();
        ring1.invalidate();
        ring2.invalidate();
        ring3.invalidate();
        tower1.addView(ringSet1);
        tower2.addView(ringSet2);
        tower3.addView(ringSet3);
        brick1.addView(ring1);
        brick2.addView(ring2);
        brick3.addView(ring3);

    }

    /**
     * The button for tower1
     */
    private void tower1Listener(){
        Button Button1 = findViewById(R.id.hanobutton1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (towerManager.getBricks().get(0) != 0){
                    if (towerManager.tower1Push(1)){
                        towerManager.addStep(1);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring1.invalidate();
                }
                else if(towerManager.getBricks().get(1) != 0){
                    if (towerManager.tower1Push(2)){
                        towerManager.addStep(1);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring2.invalidate();
                }
                else if(towerManager.getBricks().get(2) != 0){
                    if (towerManager.tower1Push(3)){
                        towerManager.addStep(1);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring3.invalidate();
                }
                else{
                    if (towerManager.tower1Pop()){
                        towerManager.addStep(1);   // add move the undo stack
                    }
                    ring1.invalidate();
                }
                ringSet1.invalidate();
                step.setText(Integer.toString(towerManager.getStep()));
                saveToFile(UserBoardManager.TEMP_SAVE_FILENAME);
            }
        });
    }

    /**
     * The button for tower2
     */
    private void tower2Listener(){
        Button playButton = findViewById(R.id.hanobutton2);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (towerManager.getBricks().get(0) != 0){
                    if (towerManager.tower2Push(1)){
                        towerManager.addStep(2);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring1.invalidate();
                }
                else if(towerManager.getBricks().get(1) != 0){
                    if (towerManager.tower2Push(2)){
                        towerManager.addStep(2);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring2.invalidate();
                }
                else if(towerManager.getBricks().get(2) != 0){
                    if (towerManager.tower2Push(3)){
                        towerManager.addStep(2);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring3.invalidate();
                }
                else{
                    if (towerManager.tower2Pop()){
                        towerManager.addStep(2);   // add move the undo stack
                    }
                    ring2.invalidate();
                }
                ringSet2.invalidate();
                step.setText(Integer.toString(towerManager.getStep()));
                saveToFile(UserBoardManager.TEMP_SAVE_FILENAME);
            }
        });
    }

    /**
     * The button for tower3
     */
    private void tower3Listener(){
        Button playButton = findViewById(R.id.hanobutton3);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (towerManager.getBricks().get(0) != 0){
                    if (towerManager.tower3Push(1)){
                        towerManager.addStep(3);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring1.invalidate();
                }
                else if(towerManager.getBricks().get(1) != 0){
                    if (towerManager.tower3Push(2)){
                        towerManager.addStep(3);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring2.invalidate();
                }
                else if(towerManager.getBricks().get(2) != 0){
                    if (towerManager.tower3Push(3)){
                        towerManager.addStep(3);   // add move the undo stack
                        towerManager.addoneStep();    // add step to counter
                    }
                    ring3.invalidate();
                }
                else{
                    if(towerManager.tower3Pop()){
                        towerManager.addStep(3);   // add move the undo stack
                    }
                    ring3.invalidate();
                }
                ringSet3.invalidate();
                step.setText(Integer.toString(towerManager.getStep()));

                // check if game is over
                boolean gameOver = towerManager.checkGameOver();
                if (gameOver) {
                    String tmp;
                    if (towerManager.getSizeOfGame() == 3){
                        tmp = "hano3";
                    }else if (towerManager.getSizeOfGame() == 4){
                        tmp = "hano4";
                    }else{
                        tmp = "hano5";
                    }
                    scoreboardManager.updateScoreBoard(tmp,userManager.getUser().getUserName(),towerManager.getStep());
                    scoreboardManager.saveToFile(GameActivity.this, ScoreboardManager.SCOREBOARD_FILE);
                    Intent toEnd = new Intent(GameActivity.this, HanoEnd.class);
                    toEnd.putExtra("score",towerManager.getStep());
                    toEnd.putExtra("userManager",userManager);
                    toEnd.putExtra("scoreboardManager",scoreboardManager);
                    startActivity(toEnd);
                }else{
                    saveToFile(UserBoardManager.TEMP_SAVE_FILENAME);
                }
            }
        });
    }

    /**
     * The button for undo
     */
    private void undoListener(){
        Button Button1 = findViewById(R.id.hanoundobutton);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = towerManager.undo();
                if (tmp.equals("stack is empty")){
                    Toast.makeText(GameActivity.this, "can't undo anymore!", Toast.LENGTH_SHORT).show();
                }else if (tmp.equals("odd size of stack")){
                    Toast.makeText(GameActivity.this, "you need to land the ring first!", Toast.LENGTH_SHORT).show();
                }else {
                    ringSet1.invalidate();
                    ringSet2.invalidate();
                    ringSet3.invalidate();
                    step.setText(Integer.toString(towerManager.getStep()));
                    saveToFile(UserBoardManager.TEMP_SAVE_FILENAME);
                }
            }
        });
    }

    /**
     * The button for saving game
     */
    private void saveListener(){
        Button Button1 = findViewById(R.id.hanosavebutton);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(UserBoardManager.SAVE_FILENAME);
                saveToFile(UserBoardManager.TEMP_SAVE_FILENAME);
            }
        });
    }

    /**
     * The button for saving game
     */
    private void backListener(){
        Button Button1 = findViewById(R.id.hanobackbutton);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToStart = new Intent(GameActivity.this,StartingActivity.class);
                backToStart.putExtra("userManager",userManager);
                backToStart.putExtra("scoreboardManager",scoreboardManager);
                startActivity(backToStart);
            }
        });
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
                userBoards = (HashMap<String, TowerOfHanoManager>) input.readObject();
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
