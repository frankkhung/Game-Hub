package fall2018.csc2017.gamehub;

import org.junit.Test;

import java.util.ArrayList;

import fall2018.csc2017.gamehub.TowerOfHano.TowerOfHanoManager;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void checkUser() {

        User user1 = new User("John44", "winner");
        User user2 = new User("John44", "winner");
        assertEquals(true, user1.checkUser("John44", "winner"));
        assertEquals(true, user2.checkUser("John44", "winner"));
        User user3 = new User("John44", "Winner");
        assertEquals(false, user3.checkUser("John44", "winner"));


    }
}