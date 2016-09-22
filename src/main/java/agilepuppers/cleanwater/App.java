package agilepuppers.cleanwater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static final String NAME = "Clean Water";

    private Stage primaryStage;

    public static void main(String[] args) {
        // Launch the application
        // equivalent to Application.launch() since it is a static method
        launch();
    }

    private void load() {
        // anything you want to load/do before starting the application, put here
        SceneSwitcher.setPrimaryStage(primaryStage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        load();

        this.primaryStage = primaryStage;

        primaryStage.setTitle(App.NAME);

        // load first scene
        SceneSwitcher.setScene("LoginScreen");

        // show window
        primaryStage.show();
    }

}
