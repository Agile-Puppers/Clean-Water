package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.user.AuthorizationLevel;
import agilepuppers.cleanwater.model.user.UserAccount;
import agilepuppers.cleanwater.model.user.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterScreen extends Controller implements FormScreen {

    @FXML private Text title;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private RadioButton userButton;
    @FXML private RadioButton workerButton;
    @FXML private RadioButton managerButton;
    @FXML private RadioButton adminButton;
    @FXML private Text formFeedback;
    
    /**
     * Returns user to title screen
     */
    @FXML
    private void handleCancel() {
        App.current.setScene("TitleScreen");
    }
    /**
     * Registers new User into the system
     */
    @FXML
    private void handleRegister() {
        //create new user
        AuthorizationLevel auth;
        if (adminButton.isSelected()) auth = AuthorizationLevel.ADMIN;
        else if (managerButton.isSelected()) auth = AuthorizationLevel.MANAGER;
        else if (workerButton.isSelected()) auth = AuthorizationLevel.WORKER;
        else auth = AuthorizationLevel.USER;

        String username = usernameField.getText();
        String password = passwordField.getText();

        // Regex matches any alphanumeric string between 2 and 20 characters long
        Pattern f = Pattern.compile("^[a-zA-Z0-9]{2,20}$");

        if (!f.matcher(username).matches()) {
            displayMessage("Username can only contain letters, numbers, and be 2 - 20 characters long");
            return; // username regex check
        }
        if (!f.matcher(password).matches()) {
            displayMessage("Password can only contain letters, numbers, and be 2 - 20 characters long");
            return; // password regex check
        }

        try {
            if (App.accountDatabase.queryEntry(username) != null) {
                displayMessage("This username is taken, be more creative man");
                return; // check for taken username
            }
        } catch (IOException e) {
            App.err.fatalError("Could not query the accounts database");
        }

        UserAccount user = new UserAccount(username, password, auth, new UserProfile());

        try {
            App.accountDatabase.addEntry(user);

            //log in the user and move on to the home screen
            App.current.setUser(user);
            App.current.setScene("HomeScreen");
        } catch (IOException e) {
            App.err.error("Could not create new user");
        }

    }

    @Override
    public void displayMessage(String message) {
        formFeedback.setText(message);
        formFeedback.setFill(Paint.valueOf("red"));
    }
}
