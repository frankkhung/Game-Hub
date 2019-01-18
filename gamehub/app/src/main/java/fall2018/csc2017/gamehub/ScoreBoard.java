package fall2018.csc2017.gamehub;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;
import java.lang.String;


public class ScoreBoard implements Iterable<DataTuple>,Serializable {
    /**
     *  the data set to save the scores
     */
    private ArrayList<DataTuple> dataset;

    /**
     *  generate a scoreboard
     */
    ScoreBoard(){
        dataset = new ArrayList<>();
    }

    /**
     * add data to data set
     */
    public void updateData(String name, Double score){
        DataTuple data = new DataTuple(name,score);
        // binary search
        int p1 = 0;
        int p2 = dataset.size()-1;
        int mid;
        while (p1 <= p2) {
            mid = (p1+p2)/2;
            if (score < dataset.get(mid).getUserScore()){p2 = mid-1;}
            else if (score > dataset.get(mid).getUserScore()){p1 = mid+1;}
            else {
                p1 = mid;
                break;
            }
        }
        dataset.add(p1,data);
    }

    public ArrayList<DataTuple> getDataset(){
        return dataset;
    }

    /**
     * return 10 highest score in the dataset
     * @return an iterator that contain the 10 highest score in this score board
     */
    @Override
    public Iterator<DataTuple> iterator(){
        return new highScoreIterator();
    }

    private class highScoreIterator implements Iterator<DataTuple>{
        int index = 0;

        @Override
        public boolean hasNext() {
            if (index < 10 && index < dataset.size()){return true;}
            else {return false;}
        }

        @Override
        public DataTuple next() {
            DataTuple result = dataset.get(index);
            index++;
            return result;
        }
    }

    /**
     * return personal highest score in this score board
     * @param name
     * @return an iterator that contain the 10 highest score of a user in this score board
     */
    public Iterator<DataTuple> iterator(String name){
        return new personalHighestScoreIterator(name);
    }

    private class personalHighestScoreIterator implements Iterator<DataTuple>{
        int index = 0;
        String username;

        personalHighestScoreIterator(String name){
            username = name;
        }

        @Override
        public boolean hasNext() {
            while (index<dataset.size()){
                if (dataset.get(index).getUsername().equals(username)){ return true;}
                index++;
            }
            return false;
        }

        @Override
        public DataTuple next() {
            DataTuple result = dataset.get(index);
            index++;
            return result;
        }
    }
}


