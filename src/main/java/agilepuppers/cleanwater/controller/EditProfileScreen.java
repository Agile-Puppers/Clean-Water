package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.user.UserAccount;
import agilepuppers.cleanwater.model.user.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditProfileScreen extends Controller implements FormScreen {

    @FXML private TextField titleField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;

    /**
     * Initializes the Edit Profile screen
     */
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

    /**
     * Sets the scene to the Logout screen and logs the User out
     */
    @FXML
    private void handleLogout() {
        App.current.setUser(null);
        App.current.setScene("LoginScreen");
    }

    /**
     * Closes the Edit Profile page and goes to the Home Screen
     */
    @FXML
    private void closeEditProfile() {
        App.current.setScene("HomeScreen");
    }

    /**
     * Updates the User's profile based on text fields
     */
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

        App.accountDatabase.queueUpdateEntry(App.current.getUser());
        try {
            App.accountDatabase.flushQueue();
        } catch (IOException e) {
            App.err.error("Could not update profile");
        }

        App.current.setScene("HomeScreen");
    }

    @Override
    public void displayMessage(String message) {
        // no implementation needed
    }
}
