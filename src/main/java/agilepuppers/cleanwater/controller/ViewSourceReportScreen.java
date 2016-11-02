package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import agilepuppers.cleanwater.model.user.AuthorizationLevel;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewSourceReportScreen extends Controller implements MapComponentInitializedListener {

    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label dateLabel;
    @FXML private Label typeLabel;
    @FXML private Label conditionLabel;

    @FXML private GoogleMapView mapView;

    private WaterSourceReport report;
    private GoogleMap map;
    private Marker marker;



    // Configuring map

    @FXML
    public void initialize() {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {
        this.map = mapView.createMap(App.current.commonMapOptions().zoom(17));
        this.updateMarker();
    }

    private void updateMarker() {
        if (map == null || report == null) return;

        MarkerOptions options = new MarkerOptions();
        LatLong position = new LatLong(report.getLatitude(), report.getLongitude());
        options.position(position);

        //remove previous marker if it exists
        if (marker != null) {
            this.marker.getJSObject().eval(this.marker.getVariableName() + ".setMap(null)");
        }

        this.marker = new Marker(options);
        map.addMarker(marker);

        map.setCenter(position);
    }


    // Configuring for Water Source Report

    @Override
    public void receiveUserData(Object data) {
        if (data != null && data instanceof WaterSourceReport) {
            this.updateForReport((WaterSourceReport) data);
        }
    }

    private void updateForReport(WaterSourceReport report) {
        this.report = report;

        idLabel.setText("Report " + report.getReportId());
        nameLabel.setText("Reported by " + report.getAuthorDisplayName());
        dateLabel.setText(report.getTimeCreated().toString());

        typeLabel.setText(report.getWaterType().getFriendlyString());
        conditionLabel.setText(report.getWaterCondition().getFriendlyString());

        this.updateMarker();
    }


    // User Interaction

    @FXML
    public void backClicked() {
        App.current.setScene("HomeScreen");
    }

    @FXML
    public void addPurityReport() {
        String message = "You must be a Worker or higher to submit a Purity Report.";

        if (App.current.userHasAuthorizationLevel(AuthorizationLevel.WORKER, message)) {
            App.current.setScene("CreatePurityReportScreen", this.report);
        }
    }

    @FXML
    public void viewHistoricalReport() {
        String message = "You must be a Manager or higher to view this Historical Report.";

        if (App.current.userHasAuthorizationLevel(AuthorizationLevel.MANAGER, message)) {
            App.current.setScene("HistoricalQualityScreen", this.report);
        }
    }

}
