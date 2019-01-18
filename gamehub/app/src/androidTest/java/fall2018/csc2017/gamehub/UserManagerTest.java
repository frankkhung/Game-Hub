package fall2018.csc2017.gamehub;

import android.content.Context;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserManagerTest {

    // test constructor
    // do we use a file path to bring up user manager.
    private String filePath = "TestUserFile";
    Context mContext;
    File file = new File(mContext.getFilesDir(),filePath);

// create AllUsers object and just populate it.

    @Test
    public void saveToFile() throws IOException,ClassNotFoundException {
        UserManager playerList = new UserManager(mContext, "TestUserFile");
        User user = new User("John44","winner");
        playerList.addUser(user);
        playerList.saveToFile(mContext,filePath);
        UserManager playList2 = new UserManager(mContext,filePath);
        playList2.loadFromFile(mContext,filePath);
        assertSame(playerList, playList2);


        // test that it throws an exception if it did not a "write" permission.
        // test that it throws an exception if it did not a "write" permission.
    }

    // write test to make sure it captures 3 types of errors.
    // test all catch exceptions.r
    @Test
    public void readFromFile() {

        // 1) give it a wrong file.
        //    trigger File Not Found

        // 2) correct file, but DID NOT HAVE read permission
        //    Permission is faulty.

        // 3) give it a file path THAT DOES NOT CONTAIN users.
    }

    @Test
    public void getAllUsers() throws IOException, ClassNotFoundException {
        UserManager playerList = new UserManager(mContext, "TestUserFile");
        User user1 = new User("John44", "winner");
        User user2 = new User("Karen1", "town");
        User user3 = new User("Peter", "Chicago");
        playerList.addUser(user1);
        playerList.addUser(user2);
        playerList.addUser(user3);
        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        assertEquals(userList, playerList.getAllUsers());
    }

    @Test
    // here it tests setUser as well
    public void addUser() throws IOException, ClassNotFoundException {
        UserManager playerList = new UserManager(mContext, "TestUserFile");
        User user1  = new User("John44", "winner" );
        playerList.addUser(user1);
        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        assertEquals(userList,playerList.getAllUsers());
    }

    @Test
    public void getUser() throws IOException, ClassNotFoundException{
        UserManager playerList = new UserManager(mContext, "TestUserFile");
        User user1  = new User("John44", "winner" );
        playerList.addUser(user1);
        assertEquals(user1, playerList.getUser());

    }

    //    @Test
//    public void setUser() {
//        User user1  = new User("John44", "winner" );
//        PlayerList.setUser(user1);
//    }
}
