package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

        map = mapView.createMap(mapOptions);

        try {
            List<WaterSourceReport> sourceReports = App.sourceReportDatabase.queryAllEntries();
            for (WaterSourceReport report : sourceReports) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLong(report.getLatitude(), report.getLongitude()));
                map.addMarker(new Marker(markerOptions));
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
