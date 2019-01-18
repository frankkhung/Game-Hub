package fall2018.csc2017.gamehub;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ScoreBoardTest {

    ScoreBoard playerScores = new ScoreBoard();
    DataTuple score1 = new DataTuple("john44",10);
    DataTuple score2 = new DataTuple("john44", 12);
    DataTuple score3 = new DataTuple("john44", 12);
    DataTuple score4 = new DataTuple("chris1", 13);
    private ArrayList<DataTuple> dataset;
    private ArrayList<DataTuple> dataset1;

    @Test
    public void updateData() {
        dataset = new ArrayList<DataTuple>();
        dataset.add(0,score1);
        dataset.add(1,score2);
        playerScores.updateData("john44",10.0);
        playerScores.updateData("john44",12.0);
        assertEquals(dataset.get(0).getUsername(),playerScores.getDataset().get(0).getUsername());
        assertEquals(dataset.get(0).getUserScore(),playerScores.getDataset().get(0).getUserScore(),0.01);
        assertEquals(dataset.get(1).getUsername(),playerScores.getDataset().get(1).getUsername());
        assertEquals(dataset.get(1).getUserScore(),playerScores.getDataset().get(1).getUserScore(),0.01);
        }

    @Test
    public void iterator() {

        dataset = new ArrayList<>();
        dataset.add(0, score1);
        dataset.add(1, score2);
        dataset.add(2, score3);
        dataset.add(3, score4);
        playerScores.updateData("john44", (double)10);
        playerScores.updateData("john44", (double)12);
        playerScores.updateData("john44", (double)12);
        playerScores.updateData("chris1", (double)13);
        assertTrue(playerScores.iterator().hasNext());
        assertEquals(playerScores.iterator().next().getUsername(),dataset.get(0).getUsername());
        assertEquals(playerScores.iterator().next().getUsername(),dataset.get(1).getUsername());

    }


}