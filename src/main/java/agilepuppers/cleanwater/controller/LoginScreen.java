package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LoginScreen extends Controller {

    @FXML
    private Text title;

    @FXML
    private void initialize() {

        // this will override any text if previously set, so don't.
        // change it here instead
        title.setText(App.NAME);

    }

    @FXML
    private void handleLogin() {
        // create new user account with username and password
        // then attempt login
        // then change screen based on success/failure
    }

    @FXML
    private void handleRegister() {
        // ..
    }

}
