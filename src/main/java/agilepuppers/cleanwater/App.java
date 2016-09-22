package agilepuppers.cleanwater;

import agilepuppers.cleanwater.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static final String NAME = "Clean Water";

    public static void main(String[] args) {
        // Launch the application
        // equivalent to Application.launch() since it is a static method
        launch();
    }

    private void load() {
        Controller.APP = this;

        // anything you want to load/do before starting the application, put under here

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        load();

        // set up SceneSwitcher
        SceneSwitcher.setPrimaryStage(primaryStage);

        primaryStage.setTitle(App.NAME);

        // load first scene
        SceneSwitcher.setScene("LoginScreen");

        // show window
        primaryStage.show();
    }

}
