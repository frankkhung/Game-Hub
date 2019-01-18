package fall2018.csc2017.gamehub.MatchingGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.gamehub.R;
import fall2018.csc2017.gamehub.ScoreboardManager;
import fall2018.csc2017.gamehub.ShowScoreboard;

public class scoreboardChoiceMatch extends AppCompatActivity {

    private String gameType;
    private ScoreboardManager scoreboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_choice);
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        match4x4ScoreboardButtonListener();
        match6x6ScoreboardButtonListener();
    }

    /**
     * Button that goes to 4x4 scoreboard
     */
    private void match4x4ScoreboardButtonListener(){
        Button buttonToScoreboard = findViewById(fall2018.csc2017.gamehub.R.id.match4x4);
        buttonToScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameType = "match4x4";
                Intent score = new Intent(scoreboardChoiceMatch.this, ShowScoreboard.class);
                score.putExtra("scoreboardManager",scoreboardManager);
                score.putExtra("gameType",gameType);
                startActivity(score);
            }
        });
    }

    /**
     * Button that goes to 6X6 scoreboard
     */
    private void match6x6ScoreboardButtonListener(){
        Button buttonToScoreboard = findViewById(fall2018.csc2017.gamehub.R.id.match6x6);
        buttonToScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameType = "match6x6";
                Intent score = new Intent(scoreboardChoiceMatch.this, ShowScoreboard.class);
                score.putExtra("scoreboardManager",scoreboardManager);
                score.putExtra("gameType",gameType);
                startActivity(score);
            }
        });
    }

    /**
     * Get the game type for the scoreboard
     * @return String indicating game type
     */
    public String getGameType(){ return gameType; }


}
