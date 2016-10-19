package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
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
     * Navgates to the screen to view all Source Reports
     */
    @FXML
    private void viewReports() { App.current.setScene("ListSourceReportsScreen"); }

}
