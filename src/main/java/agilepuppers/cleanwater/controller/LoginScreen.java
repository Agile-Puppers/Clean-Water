package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.UserAccount;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginScreen extends Controller {

    @FXML private Text title;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    @FXML
    private void initialize() {

        // this will override any text if previously set, so don't.
        // change it here instead
        title.setText(App.NAME);

    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        UserAccount account = new UserAccount(username, password);

        if (account.credentialsAreValid()) {
            App.current.setUser(account);
            App.current.setScene("HomeScreen");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText("Invalid Username or Password");
            alert.setContentText("Double check you entered your credentials correctly.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleRegister() {
        // ..
    }

}
