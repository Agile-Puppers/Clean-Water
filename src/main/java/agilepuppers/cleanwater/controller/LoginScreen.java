package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.user.UserAccount;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginScreen extends Controller implements FormScreen {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Text formFeedback;

    /**
     * Logs a User into the system
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        UserAccount user = validate(username.trim(), password);

        if (user == null) {
            displayMessage("Invalid Username or Password");
        } else {
            App.current.setUser(user);
            App.current.setScene("HomeScreen");
        }
    }

    /**
     * Validates the username and password against the user account database
     *
     * @param username the username to validate against
     * @param password the password to validate
     * @return the user account belonging to the username if it validates, otherwise null
     */
    private UserAccount validate(String username, String password) {
        try {
            UserAccount account = App.accountDatabase.queryEntry(username);

            if (account != null && account.getPassword() != null && password.equals(account.getPassword())) {
                return account;
            }
        } catch (IOException e) {
            App.err.error("Could not validate login info");
        }

        return null;
    }

    /**
     * Takes user back to title screen
     */
    @FXML
    private void handleCancel() {
        App.current.setScene("TitleScreen");
    }

    @Override
    public void displayMessage(String message) {
        formFeedback.setText(message);
        formFeedback.setFill(Paint.valueOf("red"));
    }
}
