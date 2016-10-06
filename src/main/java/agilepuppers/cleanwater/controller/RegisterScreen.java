package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.user.AccountDatabase;
import agilepuppers.cleanwater.model.user.AuthorizationLevel;
import agilepuppers.cleanwater.model.user.UserAccount;
import agilepuppers.cleanwater.model.user.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.regex.Pattern;

public class RegisterScreen extends Controller {

    @FXML private Text title;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private RadioButton userButton;
    @FXML private RadioButton workerButton;
    @FXML private RadioButton managerButton;
    @FXML private RadioButton adminButton;
    
    /**
     * Returns User to login screen upon cancelling the registration process
     */
    @FXML
    private void handleCancel() {
        App.current.setScene("LoginScreen");
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

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (!f.matcher(username).matches()) {
            alert.setTitle(null);
            alert.setHeaderText("Invalid Username");
            alert.setContentText("Username can only contain: letters, numbers, and be 2 - 20 characters long.");
            alert.showAndWait();
            return; // username regex check
        }
        if (!f.matcher(password).matches()) {
            alert.setTitle(null);
            alert.setHeaderText("Invalid Password");
            alert.setContentText("Password can only contain: letters, numbers, and be 2 - 20 characters long.");
            alert.showAndWait();
            return; // password regex check
        }

        if (AccountDatabase.getUserAccount(username) != null) {
            alert.setTitle(null);
            alert.setHeaderText("Invalid Username");
            alert.setContentText("Username is taken, be more creative.");
            alert.showAndWait();
            return; // check for taken username
        }

        UserAccount user = new UserAccount(username, password, auth, new UserProfile());
        AccountDatabase.addAccount(user);

        //log in the user and move on to the home screen
        App.current.setUser(user);
        App.current.setScene("HomeScreen");
    }

}
