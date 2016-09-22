package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LoginScreen {

    @FXML
    private Text title;

    @FXML
    private void initialize() {
        title.setText(App.NAME);
    }

}
