package agilepuppers.cleanwater;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void setScene(String viewName, Stage stage) {
        try {
            Parent p = FXMLLoader.load(App.class
                    .getResource("/fxml/scenes/" + viewName.trim() + ".fxml"));
            Scene s = new Scene(p);
            stage.setScene(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setScene(String viewName) {
        setScene(viewName, primaryStage);
    }

}
