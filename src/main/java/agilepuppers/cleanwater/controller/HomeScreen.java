package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import agilepuppers.cleanwater.model.user.AuthorizationLevel;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.util.List;

public class HomeScreen extends Controller implements MapComponentInitializedListener {

    @FXML private Label usernameLabel;

    @FXML private GoogleMapView mapView;

    private GoogleMap map;

    @FXML
    private void initialize() {
        String username = App.current.getUser().getUsername();
        this.usernameLabel.setText(username);

        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {
        map = mapView.createMap(App.current.commonMapOptions());

        try {
            List<WaterSourceReport> sourceReports = App.sourceReportDatabase.queryAllEntries();
            for (WaterSourceReport report : sourceReports) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLong(report.getLatitude(), report.getLongitude()));

                Marker marker = new Marker(markerOptions);
                map.addMarker(marker);


                map.addUIEventHandler(marker, UIEventType.click, new UIEventHandler() {
                    @Override
                    public void handle(JSObject obj) {
                        App.current.setScene("ViewSourceReportScreen", report);
                    }
                });
            }
        } catch (IOException e) {
            App.err.error("Could not load source reports");
        }

    }

    /**
     * Navigates to the Login screen and logs the user out
     */
    @FXML
    private void handleLogout() {
        App.current.setUser(null);
        App.current.setScene("TitleScreen");
    }

    /**
     * Navigates to the Edit Profile screen
     */
    @FXML
    private void openEditProfile() {
        App.current.setScene("EditProfileScreen");
    }

    /**
     * Navigates to the screen to create a Water Source Report
     */
    @FXML
    private void createSourceReport() {
        App.current.setScene("CreateSourceReportScreen");
    }

    /**
     * Navigates to the screen to create a Water Purity Report if the user is a Worker or higher
     */
    @FXML
    private void createPurityReport() {
        String message = "You must be a Worker or higher to submit a Purity Report.";

        if (userHasAuthorizationLevel(AuthorizationLevel.WORKER, message)) {
            App.current.setScene("CreatePurityReportScreen");
        }
    }

    /**
     * Navigates to the screen to view all Purity Reports is the user is a Manager or higher
     */
    @FXML
    private void viewPurityReports() {
        String message = "You must be a Manager or higher to view Purity Reports.";

        if (userHasAuthorizationLevel(AuthorizationLevel.MANAGER, message)) {
            App.current.setScene("ListPurityReportsScreen");
        }
    }

    private boolean userHasAuthorizationLevel(AuthorizationLevel level, String ifError) {
        AuthorizationLevel user = App.current.getUser().getAuthorization();

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

}
