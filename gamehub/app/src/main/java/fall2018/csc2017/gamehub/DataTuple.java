package fall2018.csc2017.gamehub;

import java.io.Serializable;

public class DataTuple implements Serializable {
    private String username;

    private double userScore;

    DataTuple(String name, double score){
        username = name;
        userScore = score;
    }

    /**
     * get username of this data
     * @return name of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * get score of this data
     * @return score of this data
     */
    public double getUserScore() {
        return userScore;
    }

    @Override
    public java.lang.String toString() {
        return "(" + username + ", " + userScore + ")";
    }
}