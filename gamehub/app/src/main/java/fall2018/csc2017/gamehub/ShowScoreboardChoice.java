package fall2018.csc2017.gamehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowScoreboardChoice extends AppCompatActivity {

    public static String gameType;
    private ScoreboardManager scoreboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fall2018.csc2017.gamehub.R.layout.activity_show_scoreboard_choice);
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        tile3x3ScoreboardButtonListener();
        tile4x4ScoreboardButtonListener();
        tile5x5ScoreboardButtonListener();
    }

    /**
     * show game1 Scoreboard button.
     */
    private void tile3x3ScoreboardButtonListener(){
        Button buttonToScoreboard = findViewById(fall2018.csc2017.gamehub.R.id.sildingtiles_game3x3);
        buttonToScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameType = "tile1";
                Intent score = new Intent(ShowScoreboardChoice.this, ShowScoreboard.class);
                score.putExtra("scoreboardManager",scoreboardManager);
                score.putExtra("gameType",gameType);
                startActivity(score);
            }
        });
    }

    /**
     * show game2 Scoreboard button.
     */
    private void tile4x4ScoreboardButtonListener(){
        Button buttonToScoreboard = findViewById(fall2018.csc2017.gamehub.R.id.sildingtiles_game4x4);
        buttonToScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameType = "tile2";
                Intent score = new Intent(ShowScoreboardChoice.this, ShowScoreboard.class);
                score.putExtra("scoreboardManager",scoreboardManager);
                score.putExtra("gameType",gameType);
                startActivity(score);
            }
        });
    }

    /**
     * show game2 Scoreboard button.
     */
    private void tile5x5ScoreboardButtonListener(){
        Button buttonToScoreboard = findViewById(fall2018.csc2017.gamehub.R.id.sildingtiles_game5x5);
        buttonToScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameType = "tile3";
                Intent score = new Intent(ShowScoreboardChoice.this, ShowScoreboard.class);
                score.putExtra("scoreboardManager",scoreboardManager);
                score.putExtra("gameType",gameType);
                startActivity(score);
            }
        });
    }





}
