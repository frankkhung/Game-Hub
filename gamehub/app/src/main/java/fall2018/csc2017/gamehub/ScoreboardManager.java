package fall2018.csc2017.gamehub;
import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;


public class ScoreboardManager implements Serializable, ISaveLoadFile {
    private static final Logger logger = Logger.getLogger(ScoreboardManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();
    public static final String SCOREBOARD_FILE = "MY_SCOREBOARD_FILE";

    /**
     * A mapping of scoreboards for each game.
     */
    private HashMap<String, ScoreBoard> scoreboards;

    /**
     * Creates a new empty StudentManager.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    ScoreboardManager(Context mContext, String filePath) throws IOException,ClassNotFoundException{
        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(mContext.getFilesDir(),filePath);
        if (file.exists()) {
            System.out.println("HEY");
            loadFromFile(mContext,filePath);
        } else {
            file.createNewFile();
            scoreboards = new HashMap<String, ScoreBoard>();
            scoreboards.put("tile1",new ScoreBoard());
            scoreboards.put("tile2",new ScoreBoard());
            scoreboards.put("tile3",new ScoreBoard());
            scoreboards.put("match4x4", new ScoreBoard());
            scoreboards.put("match6x6", new ScoreBoard());
            scoreboards.put("hano3", new ScoreBoard());
            scoreboards.put("hano4", new ScoreBoard());
            scoreboards.put("hano5", new ScoreBoard());
            System.out.println("IT WORKS");
        }
    }

    /**
     * Creates a new empty StudentManager.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    ScoreboardManager() throws IOException,ClassNotFoundException{
        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        scoreboards = new HashMap();
    }

    /**
     * read data from a csv file
     * @param filePath: path to the csv file
     * @throws FileNotFoundException: if filePath is not a valid path
     */
    public void readFromCSVFile(String filePath) throws FileNotFoundException {

        // FileInputStream can be used for reading raw bytes, like an image.
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;
        ScoreBoard scoreBoard;

        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            String gameName = record[0];
            scoreBoard = new ScoreBoard();
            for (int i = 1; i<record.length;i++){
                String name = record[i].split(" ")[0];
                Double score = Double.valueOf(record[i].split(" ")[1]);
                scoreBoard.updateData(name, score);
            }
            scoreboards.put(gameName, scoreBoard);
        }
        scanner.close();
    }

    /**
     * read from file
     * @param filePath path to the file
     * @throws ClassNotFoundException
     */
    public void loadFromFile(Context mContext, String filePath){
        try {
            InputStream inputStream = mContext.openFileInput(filePath);
            if (inputStream != null) {
                InputStream buffer = new BufferedInputStream(inputStream);
                ObjectInput input = new ObjectInputStream(buffer);
                scoreboards = (HashMap<String,ScoreBoard>) input.readObject();
                System.out.println(scoreboards);
                input.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("scoreboard activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("scoreboard activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("scoreboard activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Writes the scoreboards to file at filePath.
     * @param filePath where file is saved
     * @throws IOException
     */
    public void saveToFile(Context mContext,String filePath){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    mContext.openFileOutput(filePath, Context.MODE_PRIVATE));
            outputStream.writeObject(scoreboards);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * add a new scoreboard to scoreboards with a game name
     * @param gameName name of the game
     */
    public void addScoreboard(String gameName){
        ScoreBoard aaa = new ScoreBoard();
        scoreboards.put(gameName, aaa);

        // Log the addition of a game's scoreboard.
        logger.log(Level.FINE, "Added the scoreboard for game: " + gameName);
    }

    /**
     * remove a scoreboard from scoreboards when remove a game
     * @param gameName name of the game
     */
    public void deleteScoreboard(String gameName){
        scoreboards.remove(gameName);

        // Log the deletion of a game's scoreboard.
        logger.log(Level.FINE, "Removed the scoreboard for game: " + gameName);
    }

    /**
     * return the scoreboard for given game
     * @param gameName name of the game
     * @return scoreboard of the this game
     */
    public ScoreBoard getScoreboard(String gameName){
        return scoreboards.get(gameName);
    }

    /**
     * update the scoreboard
     * @param gameName name of the game
     * @param username name of user who play the game
     * @param score the score this user achieve
     */
    public void updateScoreBoard(String gameName, String username, double score){
        scoreboards.get(gameName).updateData(username,score);
    }
    public HashMap<String, ScoreBoard> getSB(){
        return scoreboards;
    }
}
