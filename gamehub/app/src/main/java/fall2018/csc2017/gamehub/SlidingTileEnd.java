package fall2018.csc2017.gamehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SlidingTileEnd extends AppCompatActivity implements IEndingActivity{
    private double scoreInDouble;
    private ScoreboardManager scoreboardManager;
    private UserManager userManager;
    TextView ScoreDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        scoreInDouble = b.getDouble("scoreInDouble");
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        setContentView(fall2018.csc2017.gamehub.R.layout.activity_sliding_tile_end);
        addPlayAgainButtonListener();
        addReturnToGameCentreButtonListener();

        ScoreDisplay = (TextView)findViewById(fall2018.csc2017.gamehub.R.id.Score);
        ScoreDisplay.setText(Double.toString(scoreInDouble));

    }
    /**
     * Activate the play again button.
     */
    public void addPlayAgainButtonListener() {
        Button playAgainButton = findViewById(fall2018.csc2017.gamehub.R.id.PlayAgain);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent slidingStart = new Intent(SlidingTileEnd.this,
                        StartingActivity.class);
                slidingStart.putExtra("scoreboardManager", scoreboardManager);
                slidingStart.putExtra("userManager", userManager);
                startActivity(slidingStart);
            }
        });
    }

    /**
     * Activate the return to game launch centre button.
     */
    public void addReturnToGameCentreButtonListener() {
        Button returnToGameCentre = findViewById(fall2018.csc2017.gamehub.R.id.ReturnToLaunch);
        returnToGameCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameHub = new Intent(SlidingTileEnd.this, GameHub.class);
                gameHub.putExtra("userManager", userManager);
                startActivity(gameHub);
            }
        });
    }
}
