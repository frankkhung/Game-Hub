package fall2018.csc2017.gamehub.TowerOfHano;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fall2018.csc2017.gamehub.GameActivity;
import fall2018.csc2017.gamehub.GameHub;
import fall2018.csc2017.gamehub.ScoreboardManager;
import fall2018.csc2017.gamehub.StartingActivity;
import fall2018.csc2017.gamehub.UserManager;

public class HanoEnd extends AppCompatActivity {

    TextView scoreDisplay;
    private int score;
    private ScoreboardManager scoreboardManager;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fall2018.csc2017.gamehub.R.layout.activity_hano_end);
        score = (int)getIntent().getSerializableExtra("score");
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        scoreDisplay = (TextView)findViewById(fall2018.csc2017.gamehub.R.id.hanoScore);
        scoreDisplay.setText(Integer.toString(score));
        addPlayAgainButtonListener();
        addReturnToGameCentreButtonListener();

    }
    /**
     * Activate the play again button.
     */
    private void addPlayAgainButtonListener() {
        Button playAgainButton = findViewById(fall2018.csc2017.gamehub.R.id.hanoPlayAgain);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hanoStart = new Intent(HanoEnd.this,
                        fall2018.csc2017.gamehub.TowerOfHano.StartingActivity.class);
                hanoStart.putExtra("scoreboardManager",scoreboardManager);
                hanoStart.putExtra("userManager",userManager);
                startActivity(hanoStart);
            }
        });
    }

    /**
     * Activate the return to game launch centre button.
     */
    private void addReturnToGameCentreButtonListener() {
        Button returnToGameCentre = findViewById(fall2018.csc2017.gamehub.R.id.hanoReturnToLaunch);
        returnToGameCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameHub = new Intent(HanoEnd.this, GameHub.class);
                gameHub.putExtra("userManager",userManager);
                startActivity(gameHub);
            }
        });
    }
}
