package fall2018.csc2017.gamehub;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ScoreboardManagerTest {

    GameActivity gameActivity = new GameActivity();
    private String filePath = "TestScoreBoardFile";
    Context mContext;
    //File file = new File(gameActivity.this.getFilesDir(), filePath);

    @Test
    public void saveToFile() throws IOException,ClassNotFoundException {
        ScoreboardManager playerList = new ScoreboardManager(mContext, filePath);
        User user = new User("John44","winner");
        playerList.addScoreboard("tile1");
        playerList.saveToFile(mContext,filePath);
        ScoreboardManager playList2 = new ScoreboardManager(mContext,filePath);
        playList2.loadFromFile(mContext,filePath);
        assertSame(playerList, playList2);

        }

        // tests getScoreboard() as well
    @Test
    public void updateScoreBoard() throws IOException, ClassNotFoundException{
        ScoreBoard scoreboardNew = new ScoreBoard();
        ScoreboardManager scoreSystem = new ScoreboardManager(mContext, filePath);
        scoreSystem.addScoreboard("tile2");
        scoreSystem.addScoreboard("tile3");
        scoreSystem.updateScoreBoard("tile2","john44",10.0);
        scoreboardNew = scoreSystem.getScoreboard("tile2");

        assertEquals(scoreboardNew.getDataset().get(0).getUsername(),"john44");
        assertEquals(scoreboardNew.getDataset().get(0).getUserScore(), 10.0, 0.01);




    }




    @Test
    public void getScoreboard() {
    }



}