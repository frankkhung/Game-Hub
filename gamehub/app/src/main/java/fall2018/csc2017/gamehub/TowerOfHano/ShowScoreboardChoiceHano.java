package fall2018.csc2017.gamehub.TowerOfHano;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.gamehub.R;
import fall2018.csc2017.gamehub.ScoreboardManager;
import fall2018.csc2017.gamehub.ShowScoreboard;
import fall2018.csc2017.gamehub.UserManager;

public class ShowScoreboardChoiceHano extends AppCompatActivity {

    private ScoreboardManager scoreboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scoreboard_choice_hano);
        scoreboardManager = (ScoreboardManager) getIntent().getSerializableExtra("scoreboardManager");
        hano3ScoreboardButtonListener();
        hano4ScoreboardButtonListener();
        hano5ScoreboardButtonListener();
    }

    /**
     * show game1 Scoreboard button.
     */
    private void hano3ScoreboardButtonListener(){
        Button buttonToScoreboard = findViewById(fall2018.csc2017.gamehub.R.id.hanogame3);
        buttonToScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent score = new Intent(ShowScoreboardChoiceHano.this, ShowScoreboardHano.class);
                score.putExtra("gameType","hano3");
                score.putExtra("scoreboardManager",scoreboardManager);
                startActivity(score);
            }
        });
    }

    /**
     * show game2 Scoreboard button.
     */
    private void hano4ScoreboardButtonListener(){
        Button buttonToScoreboard = findViewById(fall2018.csc2017.gamehub.R.id.hanogame4);
        buttonToScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent score = new Intent(ShowScoreboardChoiceHano.this, ShowScoreboardHano.class);
                score.putExtra("gameType","hano4");
                score.putExtra("scoreboardManager",scoreboardManager);
                startActivity(score);
            }
        });
    }

    /**
     * show game2 Scoreboard button.
     */
    private void hano5ScoreboardButtonListener(){
        Button buttonToScoreboard = findViewById(fall2018.csc2017.gamehub.R.id.hanogame5);
        buttonToScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent score = new Intent(ShowScoreboardChoiceHano.this, ShowScoreboardHano.class);
                score.putExtra("gameType","hano5");
                score.putExtra("scoreboardManager",scoreboardManager);
                startActivity(score);
            }
        });
    }





}
