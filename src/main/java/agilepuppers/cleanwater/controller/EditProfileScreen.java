package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.AccountDatabase;
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

    @FXML
    private void initialize() {
        UserAccount user = App.current.getUser();
        if (user != null && user.getProfile() != null) {
            UserProfile profile = user.getProfile();

            titleField.setText(profile.getTitle());
            nameField.setText(profile.getName());
            emailField.setText(profile.getEmail());
            addressField.setText(profile.getAddress());
        }
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
        String title = (titleField.getText() != null) ? titleField.getText() : "";
        String name = (nameField.getText() != null) ? nameField.getText() : "";
        String email = (emailField.getText() != null) ? emailField.getText() : "";
        String address = (addressField.getText() != null) ? addressField.getText() : "";

        UserProfile profile = App.current.getUser().getProfile();
        profile.setTitle(title);
        profile.setName(name);
        profile.setEmail(email);
        profile.setAddress(address);

        //remove and resave the account
        AccountDatabase.removeAccount(App.current.getUser().getUsername());
        AccountDatabase.addAccount(App.current.getUser());

        App.current.setScene("HomeScreen");
    }

}