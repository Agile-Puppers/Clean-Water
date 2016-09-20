package agilepuppers.cleanwater.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.URL;

public class Main extends Application {

    private Stage mainStage;


    // Launch the application

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.mainStage = primaryStage;
        loadInitialScene();
    }

    public Stage getMainStage() {
        return this.mainStage;
    }

    public void loadInitialScene() {
        FXMLLoader loader = new FXMLLoader();
        URL fxml = Main.class.getResource("../view/welcome-screen.fxml");
        BorderPane welcomeSceneLayout = null;

        //load the FXML layout
        try {
            loader.setLocation(fxml);
            welcomeSceneLayout = loader.load();
        } catch (IOException e) {
            //application cannot continue if the FXML file can't be loaded
            System.exit(0);
        }

        Scene welcomeScene = new Scene(welcomeSceneLayout);
        mainStage.setScene(welcomeScene);
        mainStage.show();
    }

}
