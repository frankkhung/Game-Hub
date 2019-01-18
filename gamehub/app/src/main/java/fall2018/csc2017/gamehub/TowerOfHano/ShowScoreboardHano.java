package fall2018.csc2017.gamehub.TowerOfHano;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

import fall2018.csc2017.gamehub.DataTuple;
import fall2018.csc2017.gamehub.R;
import fall2018.csc2017.gamehub.ScoreListAdapter;
import fall2018.csc2017.gamehub.ScoreboardManager;

public class ShowScoreboardHano  extends AppCompatActivity {
    private ScoreboardManager scoreboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scoreboard_hano);
        ListView mListView = (ListView) findViewById(fall2018.csc2017.gamehub.R.id.hanoscoreboard);
        scoreboardManager = (ScoreboardManager)getIntent().getSerializableExtra("scoreboardManager");
        String gameType = (String)getIntent().getSerializableExtra("gameType");
        ArrayList<DataTuple> l1 = new ArrayList();
        Iterator<DataTuple> tmp = scoreboardManager.getScoreboard(gameType).iterator();
        while (tmp.hasNext()){
            l1.add(tmp.next());
        }
        ScoreListAdapter adapter = new ScoreListAdapter(this, fall2018.csc2017.gamehub.R.layout.adapter_listview_layout, l1);
        mListView.setAdapter(adapter);
    }
}
