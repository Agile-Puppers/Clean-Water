package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.*;
import agilepuppers.cleanwater.model.user.UserAccount;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreatePurityReportScreen extends Controller implements FormScreen, MapComponentInitializedListener {

    @FXML private RadioButton safetyRatingSafe;
    @FXML private RadioButton safetyRatingTreatable;
    @FXML private RadioButton safetyRatingUnsafe;

    @FXML private TextField contaminantPPMField;
    @FXML private TextField virusPPMField;

    @FXML private Label errorLabel;

    @FXML private GoogleMapView mapView;

    private GoogleMap map;
    private Marker draggableMarker;
    private WaterSourceReport associatedSourceReport;

    //Setup

    @FXML
    private void initialize() {
        errorLabel.setText(null);
        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {
        map = mapView.createMap(App.current.commonMapOptions());

        MarkerOptions markerOptions = new MarkerOptions();
        if (this.associatedSourceReport != null) {
            LatLong pos = new LatLong(associatedSourceReport.getLatitude(), associatedSourceReport.getLongitude());
            markerOptions.position(pos);
        } else {
            markerOptions.position(new LatLong(33.776262, -84.396962));
            markerOptions.getJSObject().setMember("draggable", true);
        }

        draggableMarker = new Marker(markerOptions);

        map.addMarker(draggableMarker);
    }

    @Override
    public void receiveUserData(Object data) {
        if (data instanceof WaterSourceReport) {
            this.associatedSourceReport = (WaterSourceReport)data;
        }
    }


    //User Interaction

    private void goBack() {
        if (this.associatedSourceReport != null) {
            App.current.setScene("ViewSourceReportScreen", this.associatedSourceReport);
        } else {
            App.current.setScene("HomeScreen");
        }
    }

    @FXML
    private void cancelReport() {
        goBack();
    }

    @FXML
    private void saveReport() {

        SafetyRating safetyRating;
        if (this.safetyRatingSafe.isSelected()) safetyRating = SafetyRating.SAFE;
        else if (this.safetyRatingTreatable.isSelected()) safetyRating = SafetyRating.TREATABLE;
        else safetyRating = SafetyRating.UNSAFE;

        double contaminantPPM;
        try {
            contaminantPPM = Double.valueOf(this.contaminantPPMField.getText());
        } catch (NumberFormatException e) {
            this.displayMessage("Contaminant PPM must be a number.");
            return;
        }

        double virusPPM;
        try {
            virusPPM = Double.valueOf(this.virusPPMField.getText());
        } catch (NumberFormatException e) {
            this.displayMessage("Virus PPM must be a number.");
            return;
        }

        double lat = Double.valueOf(map.getJSObject().eval(draggableMarker.getVariableName() + ".getPosition().lat()").toString());
        double lon = Double.valueOf(map.getJSObject().eval(draggableMarker.getVariableName() + ".getPosition().lng()").toString());

        int newID = 0;
        try {
            newID = App.purityReportDatabase.queryAllEntries().size();
        } catch (IOException e) {
            App.err.error("Could not query database");
            goBack();
        }

        UserAccount user = App.current.getUser();
        WaterPurityReport report = new WaterPurityReport(newID, user, lat, lon, safetyRating, contaminantPPM, virusPPM);

        try {
            App.purityReportDatabase.addEntry(report);
        } catch (IOException e) {
            App.err.error("Could not save Water Source Report");
        }

        goBack();
    }

    @Override
    public void displayMessage(String message) {
        errorLabel.setText(message);
    }

}
