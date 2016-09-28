package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.AccountDatabase;
import agilepuppers.cleanwater.model.AuthorizationLevel;
import agilepuppers.cleanwater.model.UserAccount;
import agilepuppers.cleanwater.model.UserProfile;
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

    @FXML
    private void handleCancel() {
        App.current.setScene("LoginScreen");
    }

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

        Pattern f = Pattern.compile("^[a-zA-Z0-9]{2,20}$");

        if (!f.matcher(username).matches()) return; // username regex check
        if (!f.matcher(password).matches()) return; // password regex check

        if (AccountDatabase.getUserAccount(username) != null) return; // check for taken username

        UserAccount user = new UserAccount(1, username, password, auth, new UserProfile());
        AccountDatabase.addAccount(user);

        //log in the user and move on to the home screen
        App.current.setUser(user);
        App.current.setScene("HomeScreen");
    }

}
