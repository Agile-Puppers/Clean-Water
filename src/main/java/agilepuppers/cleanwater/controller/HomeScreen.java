package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import javafx.fxml.FXML;

public class HomeScreen {

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleLogout() {
        App.current.setUser(null);
        App.current.setScene("LoginScreen");
    }

}
