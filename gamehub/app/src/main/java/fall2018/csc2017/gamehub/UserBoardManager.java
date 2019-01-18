package fall2018.csc2017.gamehub;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserBoardManager implements Serializable {
    private static final Logger logger = Logger.getLogger(ScoreboardManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    private HashMap<String, Object> userBoard;
    public final static String SAVE_FILENAME = "save_file.ser";
    public final static String TEMP_SAVE_FILENAME = "save_file_tmp.ser";


    UserBoardManager(Context mContext)throws IOException{
        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        File file = new File(mContext.getFilesDir(), SAVE_FILENAME);
        if (file.exists()) {
            readFromFile(mContext,SAVE_FILENAME);
        } else {
            file.createNewFile();
            userBoard = new HashMap<>();
            saveToFile(mContext,SAVE_FILENAME);
        }
        File fileTemp = new File(mContext.getFilesDir(), TEMP_SAVE_FILENAME);
        if (fileTemp.exists()) {
            readFromFile(mContext,TEMP_SAVE_FILENAME);
        } else {
            file.createNewFile();
            userBoard = new HashMap<>();
            saveToFile(mContext,TEMP_SAVE_FILENAME);
        }
    }

    /**
     * read from file
     * @param filePath path to the file
     * @throws ClassNotFoundException
     */
    public void readFromFile(Context mContext, String filePath){
        try {
            InputStream inputStream = mContext.openFileInput(filePath);
            if (inputStream != null) {
                InputStream buffer = new BufferedInputStream(inputStream);
                ObjectInput input = new ObjectInputStream(buffer);
                userBoard = (HashMap<String,Object>) input.readObject();
                input.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("userboard activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("userboard activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("userboard activity", "File contained unexpected data type: " + e.toString());
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
            outputStream.writeObject(userBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
