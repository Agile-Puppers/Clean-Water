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

    public static boolean validRegistrationInfo(String un, String pw) {
        // Regex matches any alphanumeric string between 2 and 20 characters long
        Pattern f = Pattern.compile("^[a-zA-Z0-9]{2,20}$");

        if (!f.matcher(un).matches()) {
            return false;
        }
        if (!f.matcher(pw).matches()) {
            return false;
        }

        return true;
    }

    private boolean availableToRegister(String un) {
        try {
            return App.accountDatabase.queryEntry(un) != null;
        } catch (IOException e) {
            e.printStackTrace();
            App.err.fatalError("Could not query the accounts database");
        }
        return false;
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

        if (!RegisterScreen.validRegistrationInfo(username, password)) {
            displayMessage("Username and password can only contain letters, numbers, and be 2 - 20 characters long");
            return;
        }

        if (!availableToRegister(username)) { // check for taken username
            displayMessage("This username is taken, be more creative man");
            return;
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
