package fall2018.csc2017.gamehub.MatchingGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fall2018.csc2017.gamehub.GameHub;
import fall2018.csc2017.gamehub.R;
import fall2018.csc2017.gamehub.ScoreboardManager;
import fall2018.csc2017.gamehub.UserManager;

public class MatchingEnd extends AppCompatActivity {

    private double scoreInDouble;
    private ScoreboardManager scoreboardManager;
    private UserManager userManager;
    TextView ScoreDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scoreInDouble = (double)getIntent().getSerializableExtra("scoreInDouble");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        setContentView(R.layout.activity_matching_end);

        addPlayAgainButtonListener();
        addReturnToGameCentreButtonListener();

        ScoreDisplay = (TextView) findViewById(fall2018.csc2017.gamehub.R.id.Score);
        ScoreDisplay.setText(Double.toString(scoreInDouble));
    }

    /**
     * Activate the play again button.
     */
    private void addPlayAgainButtonListener() {
        Button playAgainButton = findViewById(fall2018.csc2017.gamehub.R.id.PlayAgain);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent slidingStart = new Intent(MatchingEnd.this,
                        StartingActivityMatch.class);
                slidingStart.putExtra("userManager",userManager);
                slidingStart.putExtra("scoreboardManager",scoreboardManager);
                startActivity(slidingStart);
            }
        });
    }

    /**
     * Activate the return to game launch centre button.
     */
    private void addReturnToGameCentreButtonListener() {
        Button returnToGameCentre = findViewById(fall2018.csc2017.gamehub.R.id.ReturnToLaunch);
        returnToGameCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameHub = new Intent(MatchingEnd.this, GameHub.class);
                gameHub.putExtra("userManager",userManager);
                startActivity(gameHub);
            }
        });
    }
}

