package agilepuppers.cleanwater;

import java.io.IOException;
import java.net.URL;

import agilepuppers.cleanwater.model.UserAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//App is a Singleton object accessed by App.current
public class App extends Application {
    
    public static final String NAME = "Clean Water";
    public static App current;
    
    private Stage primaryStage;
    private UserAccount user;

    public static void main(String[] args) {
        // Launch the application
        Application.launch();
    }

    private void load() {
        App.current = this;

        // anything you want to load/do before starting the application, put under here

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        load();
        
        this.setPrimaryStage(primaryStage);
        primaryStage.setTitle(App.NAME);

        // load first scene
        this.setScene("LoginScreen");

        // show window
        primaryStage.show();
    }
    
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Scene setScene(String viewName, Stage stage) {
        try {
            URL url = App.class.getResource("/fxml/scenes/" + viewName.trim() + ".fxml");
            Parent parent = FXMLLoader.load(url);
            Scene newScene = new Scene(parent);
            stage.setScene(newScene);
            return newScene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setScene(String viewName) {
        setScene(viewName, this.primaryStage);
    }

    public UserAccount getUser() {
        return this.user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

}
