package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeScreen {

    @FXML private Label usernameLabel;

    @FXML
    private void initialize() {
        String username = App.current.getUser().getUsername();
        this.usernameLabel.setText(username);
    }

    @FXML
    private void handleLogout() {
        App.current.setUser(null);
        App.current.setScene("LoginScreen");
    }

}
