package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class HomeScreen implements MapComponentInitializedListener {

    @FXML private Label usernameLabel;

    @FXML private GoogleMapView mapView;

    private GoogleMap map;

    /**
     * Initializes Home screen
     */
    @FXML
    private void initialize() {
        String username = App.current.getUser().getUsername();
        this.usernameLabel.setText(username);

        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {

        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(0, 0))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(2);

        map = mapView.createMap(mapOptions);
    }

    /**
     * Navigates to the Login screen and logs the user out
     */
    @FXML
    private void handleLogout() {
        App.current.setUser(null);
        App.current.setScene("LoginScreen");
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
        App.current.setScene("EditSourceReportScreen");
    }

    /**
     * Navgates to the screen to view all Source Reports
     */
    @FXML
    private void viewReports() { App.current.setScene("ViewSourceReportsScreen"); }

}
