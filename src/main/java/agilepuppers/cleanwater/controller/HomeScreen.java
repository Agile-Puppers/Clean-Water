package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeScreen {

    @FXML private Label usernameLabel;
    
    /**
     * Initializes Home screen
     */
    @FXML
    private void initialize() {
        String username = App.current.getUser().getUsername();
        this.usernameLabel.setText(username);
    }

    /**
     * Sets scene to Login screen and logs the user out
     */
    @FXML
    private void handleLogout() {
        App.current.setUser(null);
        App.current.setScene("LoginScreen");
    }
    /**
     * Sets scene to the Edit Profile screen
     */
    @FXML
    private void openEditProfile() {
        App.current.setScene("EditProfileScreen");
    }

}
