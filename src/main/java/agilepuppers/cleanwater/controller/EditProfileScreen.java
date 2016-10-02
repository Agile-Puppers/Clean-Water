package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.UserAccount;
import agilepuppers.cleanwater.model.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditProfileScreen {

    @FXML private TextField titleField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;

    private void initialize() {
        String username = App.current.getUser().getUsername();
    }

    @FXML
    private void handleLogout() {
        App.current.setUser(null);
        App.current.setScene("LoginScreen");
    }

    @FXML
    private void closeEditProfile() {
        App.current.setScene("HomeScreen");
    }

    @FXML
    private void updateProfile() {
        String title = (titleField.getText() == null) ? titleField.getText() : "";
        String name = (nameField.getText() == null) ? nameField.getText() : "";
        String email = (emailField.getText() == null) ? emailField.getText() : "";
        String address = (addressField.getText() == null) ? addressField.getText() : "";

        App.current.getUser().getProfile().setTitle(title);
        App.current.getUser().getProfile().setName(name);
        App.current.getUser().getProfile().setEmail(email);
        App.current.getUser().getProfile().setAddress(address);
        App.current.setScene("HomeScreen");
    }

}