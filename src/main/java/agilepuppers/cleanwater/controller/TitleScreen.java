package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class TitleScreen extends Controller {

    @FXML private Text title;

    @FXML
    private void initialize() {

        // this will override any text if previously set, so don't.
        // change it here instead
        title.setText(App.NAME);

    }

    /**
     * Takes user to the login screen
     */
    @FXML
    private void handleLogin() {
        App.current.setScene("LoginScreen");
    }

    /**
     * Takes user to the registration screen
     */
    @FXML
    private void handleRegister() {
        App.current.setScene("RegisterScreen");
    }

}
