package agilepuppers.cleanwater;

import agilepuppers.cleanwater.model.AccountDatabase;
import agilepuppers.cleanwater.model.UserAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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

        AccountDatabase.setFile("./accounts.txt");

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        load();

        this.primaryStage = primaryStage;

        // load first scene
        // not using setScene method because root cannot be null
        primaryStage.setScene(new Scene(getScene("LoginScreen"), 900, 550));

        this.primaryStage.setTitle(App.NAME);

        // show window
        this.primaryStage.show();
    }


    /**
     * Sets the current scene in the stage specified to the scene specified.
     * Instead of setting the scene of the root, however, this method replaces
     * the "root" of the scene so the size of the window does not jarringly
     * change.
     *
     * @param viewName  The name of the view to be loaded from the fxml file of the same name
     * @param stage     The stage to set the scene of
     */
    public void setScene(String viewName, Stage stage) {
        stage.getScene().setRoot(getScene(viewName));
    }

    /**
     * Sets the current scene in the main window of the application (primaryStage).
     *
     * @param viewName  The name of the view to be loaded from the fxml file of the same name
     */
    public void setScene(String viewName) {
        setScene(viewName, this.primaryStage);
    }

    private Parent getScene(String viewName) {
        URL url = App.class.getResource("/fxml/scenes/" + viewName.trim() + ".fxml");
        try {
            return FXMLLoader.load(url);
        } catch (IOException e) {
            App.current.error(App.FATAL, "Could not access scene");
        }
        return null;
    }

    public UserAccount getUser() {
        return this.user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public static final int RECOVERABLE = 0;
    public static final int FATAL = 1;

    public void error(int errorType, String msg) {
        if (errorType == RECOVERABLE) {
            System.out.println("Error message: " + msg);
            App.current.user = null;
            App.current.setScene("LoginScreen");
        } else {
            System.exit(0);
        }
    }

    public void error(String msg) {
        error(RECOVERABLE, msg);
    }

    public void error(int errorType) {
        error(errorType, "");
    }

    public void error() {
        error(FATAL);
    }

}
