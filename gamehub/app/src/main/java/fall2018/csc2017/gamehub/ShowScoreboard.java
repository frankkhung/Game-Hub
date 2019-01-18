package fall2018.csc2017.gamehub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class ShowScoreboard extends AppCompatActivity {

    private ScoreboardManager scoreboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fall2018.csc2017.gamehub.R.layout.activity_show_scoreboard);
        ListView mListView = (ListView) findViewById(fall2018.csc2017.gamehub.R.id.scoreboard);
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
