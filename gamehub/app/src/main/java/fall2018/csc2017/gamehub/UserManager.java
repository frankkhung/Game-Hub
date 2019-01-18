package fall2018.csc2017.gamehub;
import android.content.Context;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

/**user manager class
 *
 */
public class UserManager implements Serializable,ISaveLoadFile {
    private User user;
    /**
     * boolean checking if there are more objects in the file
     */
    private boolean cont = true;
    /**
     * List of all users
     */
    private List<User> allUsers;

    /**
     * user that this class is managing
     */
    /**
     * creates a usermanager with mContext and filepath to read from file
     * @param mContext
     * @param filePath
     * @throws IOException
     * @throws ClassNotFoundException
     */
    UserManager(Context mContext, String filePath) throws IOException,ClassNotFoundException{
        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(mContext.getFilesDir(),filePath);
        if (file.exists()) {
            loadFromFile(mContext,filePath);
        } else {
            file.createNewFile();
            allUsers = new ArrayList<User>();

        }

    }

    /**
     * saves instance of the particular UserManager and store as a file
     * @param mContext
     * @param filePath
     */
    public void saveToFile(Context mContext,String filePath){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    mContext.openFileOutput(filePath, Context.MODE_PRIVATE));
            outputStream.writeObject(allUsers);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * loads any UserManager from previous runs of code to view the allUsers List
     * @param mContext
     * @param filePath
     */
    public void loadFromFile(Context mContext, String filePath){
        try {
            InputStream inputStream = mContext.openFileInput(filePath);
            if (inputStream != null) {
                InputStream buffer = new BufferedInputStream(inputStream);
                ObjectInput input = new ObjectInputStream(buffer);
                allUsers = (List<User>) input.readObject();
                input.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("user activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("user activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("user activity", "File contained unexpected data type: " + e.toString());
        }
    }
    public List<User> getAllUsers(){
        return allUsers;
    }
    public void addUser(User user) {
        setUser(user);
        allUsers.add(user);
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return user;
    }


}
