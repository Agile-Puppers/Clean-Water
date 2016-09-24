package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Created by ridoymajumdar on 9/23/16.
 */
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
