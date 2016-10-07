package agilepuppers.cleanwater;

import agilepuppers.cleanwater.model.ErrorHandler;
import agilepuppers.cleanwater.model.Logger;
import agilepuppers.cleanwater.model.TextDatabase;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import agilepuppers.cleanwater.model.user.UserAccount;
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

    // singletons
    public static App current;
    public static TextDatabase<UserAccount> accountDatabase;
    public static TextDatabase<WaterSourceReport> sourceReportDatabase;
    public static Logger logger = new Logger();
    public static ErrorHandler err = new ErrorHandler();

    // instance variables
    private Stage primaryStage;
    private UserAccount user;

    public static void main(String[] args) {
        // Launch the application
        Application.launch();
    }

    private void load() {
        App.current = this;

        // anything you want to load/do before starting the application, put under here

        accountDatabase = new TextDatabase<>("./db/accounts", UserAccount.USERNAME_KEY, "|", "=", UserAccount.class);
        sourceReportDatabase = new TextDatabase<>("./db/waterSourceReports", WaterSourceReport.ID_KEY, "|", "=", WaterSourceReport.class);
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
     * @param viewName The name of the view to be loaded from the fxml file of the same name
     * @param stage    The stage to set the scene of
     */
    public void setScene(String viewName, Stage stage) {
        stage.getScene().setRoot(getScene(viewName));
    }

    /**
     * Sets the current scene in the main window of the application (primaryStage).
     *
     * @param viewName The name of the view to be loaded from the fxml file of the same name
     */
    public void setScene(String viewName) {
        setScene(viewName, this.primaryStage);
    }

    private Parent getScene(String viewName) {
        URL url = App.class.getResource("/fxml/scenes/" + viewName.trim() + ".fxml");
        try {
            return FXMLLoader.load(url);
        } catch (IOException e) {
            App.err.fatalError("Could not access scene");
        }
        return null;
    }

    public UserAccount getUser() {
        return this.user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

}
