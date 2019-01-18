package fall2018.csc2017.gamehub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ScoreListAdapter extends ArrayAdapter<DataTuple> {
    private static final String TAG = "Scorelistapapter";
    private Context mContext;
    int mResouce;

    public ScoreListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DataTuple> objects) {
        super(context, resource, objects);
        mContext = context;
        mResouce = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position).getUsername();
        String score = String.valueOf(getItem(position).getUserScore());

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResouce,parent,false);

        TextView tvName = (TextView) convertView.findViewById(fall2018.csc2017.gamehub.R.id.username);
        TextView tvScore = (TextView) convertView.findViewById(fall2018.csc2017.gamehub.R.id.score);

        tvName.setText(username);
        tvScore.setText(score);

        return convertView;
    }
}
