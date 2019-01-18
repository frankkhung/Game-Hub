package fall2018.csc2017.gamehub;

import android.content.Context;

public interface ISaveLoadFile {

    String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    String SAVE_FILENAME = "save_file.ser";

    /**
     * Load saved data from file of fileName
     * @param fileName name of file
     */
    void loadFromFile(Context context, String fileName);

    /**
     * Save data from file of fileName
     * @param fileName name of file
     */
    void saveToFile(Context context, String fileName);

}
