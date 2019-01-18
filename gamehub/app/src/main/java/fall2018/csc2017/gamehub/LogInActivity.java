package fall2018.csc2017.gamehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * LogIn activity for startup
 */
public class LogInActivity extends AppCompatActivity {
    /** the user class
     *
     */
    private User user;
    /**
     * the name, password, and the error messages of the LogInActivity page
     */
    private EditText name;
    private EditText password;
    private TextView errorMsg;
    private TextView errorMsg2;
    /**
     * the file used to save all the UserManager
     */
    private String SAVE_FILENAME = "save_user";
    private UserManager userManager;

    /**
     * onCreate method for when LogInActivity is shown on screen
     * initializes the name, password, the errorMsg, adds button listen for all the buttons,
     * and loads userManager file if it exists
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fall2018.csc2017.gamehub.R.layout.log_in);
        name = (EditText)findViewById(fall2018.csc2017.gamehub.R.id.username);
        password = (EditText)findViewById(fall2018.csc2017.gamehub.R.id.password);
        errorMsg = (TextView)findViewById(fall2018.csc2017.gamehub.R.id.errormsg);
        errorMsg2 = (TextView)findViewById(fall2018.csc2017.gamehub.R.id.errormsg2);
        addLogInButtonListener();
        addCreateNewUserListener();
        if (userManager == null){
            openUserManager(SAVE_FILENAME);
        }
    }

    /**
     * open the file to check for userManager in file path filepath
     * @param fliePath
     */
    private void openUserManager(String fliePath){
        try{
            userManager = new UserManager(this,fliePath);
            userManager.saveToFile(this,fliePath);
        }
        catch (IOException e){
            System.out.println("can't read or open file!");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            System.out.println("cant find  class!");
            e.printStackTrace();
        }
    }

    /**
     * button listener for the log in button
     */
    private void addLogInButtonListener() {
        Button loginButton = findViewById(fall2018.csc2017.gamehub.R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(name.getText().toString(),password.getText().toString());
                validate(name.getText().toString(),password.getText().toString());


            }
        });
    }

    /**
     * button listener for the create new user button
     */
    private void addCreateNewUserListener() {
        Button loginButton = findViewById(fall2018.csc2017.gamehub.R.id.createNewUser);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, CreateUser.class);
                intent.putExtra("userManager",userManager);
                startActivity(intent);
            }
        });
    }

    /**
     * checks if the User information that the User has input is a valid one if it exists
     * display corresponding error messages if user has mis-input the information
     * @param userName the userName to pass in to check if it's existed already and correct
     * @param password the password to pass in to check if it's existed already and correct
     */
    private void validate(String userName, String password){
        boolean userInList = false;
        userManager.loadFromFile(this,SAVE_FILENAME);
        if (userName.equals("")||password.equals("")) {
            errorMsg2.setVisibility(View.INVISIBLE);
            errorMsg.setVisibility(View.VISIBLE);
        }
        else if (userManager.getAllUsers() != null) {
            for (User users : userManager.getAllUsers()) {
                if (users.checkUser(userName, password)) {
                    userInList = true;
                    user = users;
                    userManager.setUser(user);
                    userManager.saveToFile(this, SAVE_FILENAME);
                    Intent intent = new Intent(LogInActivity.this, GameHub.class);
                    intent.putExtra("userManager",userManager);
                    startActivity(intent);
                }
            }
            if (!userInList){
                errorMsg.setVisibility(View.INVISIBLE);
                errorMsg2.setVisibility(View.VISIBLE);
            }
        }
    }

    public User getUser() {
        return user;
    }
    public UserManager getUserManager(){
        return userManager;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setUserManager(UserManager userManager){
        this.userManager = userManager;
    }
}
