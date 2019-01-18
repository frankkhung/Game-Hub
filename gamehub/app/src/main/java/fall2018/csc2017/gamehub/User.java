package fall2018.csc2017.gamehub;

import java.io.Serializable;

/**user class for login
 *
 */
public class User implements Serializable{
    /**
     * The username and password of that particular user
     */
    private String userName;
    private String password;

    /**
     * A new user with username userName and password password
     * @param userName the username of user
     * @param password the password of user
     */
    public User(String userName, String password){
        super();
        this.userName = userName;
        this.password = password;
    }

    /**
     * checkuser checks whether the user's username is equal to userName and same for password
     * @param userName username to check
     * @param password password to check
     * @return a boolean indicating whether or not the username and password match
     */
    public boolean checkUser(String userName, String password){
        return (getUserName().equals(userName) && getPassword().equals(password));
    }

    /**returns the userName of a particular instance of User since it holds private
     *
     * @return the userName
     */
    public String getUserName(){
        return userName;
    }

    /**returns the password of a particular instance of User since it holds private
     *
     * @return the password
     */
    public String getPassword(){
        return password;
    }
    

    /**
     * override the toString method
     *
     * @return String
     */
    @Override
    public String toString(){
        return userName + password;
    }
}
