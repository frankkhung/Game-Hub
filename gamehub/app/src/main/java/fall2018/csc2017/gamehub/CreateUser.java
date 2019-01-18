package fall2018.csc2017.gamehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * a class that create the user by creating the Name and Password
 *
 */
public class CreateUser extends AppCompatActivity {
    private final String SAVE_FILENAME = "save_user";
    private LogInActivity logInActivity;
    /**
     * the User that the user will create
     */
    private User user;

    /**
     * the user manager for the users that are created
     */
    private UserManager userManager;

    /**
     * Assigning the type of Name as EditText, which is the Name for the user
     */
    private EditText Name;
    /**
     * Assigning the type of Password as EditTex, which is the password for the user
     */
    private EditText Password;

    /**
     * @param savedInstanceState
     * Creating the button that links to the Xml file which has the blank to type in Name and
     * Password
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = (UserManager)getIntent().getSerializableExtra("userManager");
        setContentView(fall2018.csc2017.gamehub.R.layout.create_user);
        Name = findViewById(fall2018.csc2017.gamehub.R.id.usernameC);
        Password = findViewById(fall2018.csc2017.gamehub.R.id.passwordC);
        addCreatedUserListener();
    }

    /**
     *
     * The button that creates User with the given user information
     */
    private void addCreatedUserListener() {
        Button loginButton = findViewById(fall2018.csc2017.gamehub.R.id.createNewUser);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                user = new User(Name.getText().toString(), Password.getText().toString());
                checksValidation();

            }
        });
    }

    /**
     * checks if the user with the new information can be created. otherwise display error msgs
     */
    private void checksValidation(){
        boolean doesExist = false;
        boolean userInList = false;
        if (user.getUserName().equals("")||user.getPassword().equals("")) {
            makeToastEmptyText();
        }
        else if (userManager.getAllUsers() != null) {
            for (User users : userManager.getAllUsers()) {
                if (users.getUserName().equals(user.getUserName())) {
                    userInList = true;
                    makeToastExistsText();
                }
            }
            if (!userInList){
                userManager.addUser(user);
                userManager.saveToFile(this,SAVE_FILENAME);
                Intent intent = new Intent(CreateUser.this, GameHub.class);
                intent.putExtra("userManager",userManager);
                startActivity(intent);
            }
        }
    }

    /**
     * toast that displays user to enter a valid username/password
     */
    private void makeToastEmptyText() {
        Toast.makeText(this, "Please enter a valid username/password",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * toast that displays user to enter another username since it already exists
     */
    private void makeToastExistsText() {
        Toast.makeText(this, "User with Username already exists",
                Toast.LENGTH_SHORT).show();
    }
}
