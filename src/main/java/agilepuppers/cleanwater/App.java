package agilepuppers.cleanwater;

import agilepuppers.cleanwater.controller.Controller;
import agilepuppers.cleanwater.model.ErrorHandler;
import agilepuppers.cleanwater.model.Logger;
import agilepuppers.cleanwater.model.TextDatabase;
import agilepuppers.cleanwater.model.report.WaterPurityReport;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import agilepuppers.cleanwater.model.user.AuthorizationLevel;
import agilepuppers.cleanwater.model.user.UserAccount;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    public static TextDatabase<WaterPurityReport> purityReportDatabase;
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

        this.accountDatabase = new TextDatabase<>("./db/accounts", UserAccount.USERNAME_KEY, UserAccount.factory);
        this.sourceReportDatabase = new TextDatabase<>("./db/waterSourceReports", WaterSourceReport.ID_KEY, WaterSourceReport.factory);
        this.purityReportDatabase = new TextDatabase<>("./db/waterPurityReports", WaterPurityReport.ID_KEY, WaterPurityReport.factory);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        load();

        this.primaryStage = primaryStage;

        // load first scene
        // not using setScene method because root cannot be null
        //primaryStage.setScene(new Scene(getScene("TitleScreen"), 900, 550));
        this.setScene("TitleScreen");

        this.primaryStage.setTitle(App.NAME);

        // show window
        this.primaryStage.show();
    }

    /**
     * Sets the current scene in the main window of the application (primaryStage).
     *
     * @param viewName The name of the view to be loaded from the fxml file of the same name
     */
    public void setScene(String viewName) {
        this.setScene(viewName, this.primaryStage, null);
    }

    /**
     * Sets the current scene in the main window of the application (primaryStage).
     *
     * @param viewName The name of the view to be loaded from the fxml file of the same name
     * @param userData An object to pass to the new scene's controller
     */
    public void setScene(String viewName, Object userData) {
        this.setScene(viewName, this.primaryStage, userData);
    }

    /**
     * Sets the current scene in the main window of the application (primaryStage).
     *
     * @param viewName The name of the view to be loaded from the fxml file of the same name
     * @param stage The stage (window) to operate on
     * @param userData An object to pass to the new scene's controller
     */
    public void setScene(String viewName, Stage stage, Object userData) {
        URL url = App.class.getResource("/fxml/scenes/" + viewName.trim() + ".fxml");
        try {
            //attempt to load the .fxml
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();

            //pass user data
            Object controller = loader.getController();
            if (userData != null && controller instanceof Controller) {
                ((Controller) controller).receiveUserData(userData);
            }

            //present controller
            if (stage.getScene() == null) {
                stage.setScene(new Scene(parent, 900, 550));
            } else {
                stage.getScene().setRoot(parent);
            }


        } catch (IOException e) {
            e.printStackTrace();
            App.err.fatalError("Could not access scene");
        }
    }

    public UserAccount getUser() {
        return this.user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public boolean userHasAuthorizationLevel(AuthorizationLevel level, String ifError) {
        AuthorizationLevel user = this.getUser().getAuthorization();

        if (user.isAtLeast(level)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Not Authorized");
            alert.setContentText(ifError);
            alert.showAndWait();

            return false;
        }
    }

    public MapOptions commonMapOptions() {
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(33.776262, -84.396962)) // GaTech campus coords
                .mapType(MapTypeIdEnum.ROADMAP) // not "satellite" view
                .overviewMapControl(true)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(14);

        return mapOptions;
    }

}
