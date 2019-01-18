package fall2018.csc2017.gamehub.MatchingGame;

import android.content.Context;
import android.widget.Toast;


public class MovementControllerMatch {

    private BoardManagerMatch boardManagerMatch = null;

    public MovementControllerMatch() {
    }

    public void setBoardManagerMatch(BoardManagerMatch boardManagerMatch) {
        this.boardManagerMatch = boardManagerMatch;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManagerMatch.isValidTap(position)) {
            boardManagerMatch.touchMove(position);
            if (boardManagerMatch.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
