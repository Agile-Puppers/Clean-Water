package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterCondition;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import agilepuppers.cleanwater.model.report.WaterType;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.JavascriptObject;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import netscape.javascript.JSObject;

import java.io.IOException;

public class CreateSourceReportScreen extends Controller implements FormScreen, MapComponentInitializedListener {

    @FXML private RadioButton waterTypeBottled;
    @FXML private RadioButton waterTypeWell;
    @FXML private RadioButton waterTypeStream;
    @FXML private RadioButton waterTypeLake;
    @FXML private RadioButton waterTypeSpring;
    @FXML private RadioButton waterTypeOther;

    @FXML private RadioButton waterConditionPotable;
    @FXML private RadioButton waterConditionWaste;
    @FXML private RadioButton waterConditionTreatableClear;
    @FXML private RadioButton waterConditionTreatableMuddy;

    @FXML private GoogleMapView mapView;

    private GoogleMap map;

    private Marker draggableMarker;

    @FXML
    private void initialize() {
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

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLong(33.776262, -84.396962));
        markerOptions.getJSObject().setMember("draggable", true);

        draggableMarker = new Marker(markerOptions);

        map.addMarker(draggableMarker);

    }

    @FXML
    private void cancelReport() {
        App.current.setScene("HomeScreen");
    }

    @FXML
    private void saveReport() {

        WaterType waterType;
        if (waterTypeBottled.isSelected()) waterType = WaterType.BOTTLED;
        else if (waterTypeWell.isSelected()) waterType = WaterType.WELL;
        else if (waterTypeStream.isSelected()) waterType = WaterType.STREAM;
        else if (waterTypeLake.isSelected()) waterType = WaterType.LAKE;
        else if (waterTypeSpring.isSelected()) waterType = WaterType.SPRING;
        else waterType = WaterType.OTHER;

        WaterCondition waterCondition;
        if (waterConditionPotable.isSelected())
            waterCondition = WaterCondition.POTABLE;
        else if (waterConditionWaste.isSelected())
            waterCondition = WaterCondition.WASTE;
        else if (waterConditionTreatableClear.isSelected())
            waterCondition = WaterCondition.TREATABLE_CLEAR;
        else waterCondition = WaterCondition.TREATABLE_MUDDY;


        double lat = Double.valueOf(map.getJSObject().eval(draggableMarker.getVariableName() + ".getPosition().lat()").toString());
        double lon = Double.valueOf(map.getJSObject().eval(draggableMarker.getVariableName() + ".getPosition().lng()").toString());

        WaterSourceReport report = new WaterSourceReport(App.current.getUser(), lat, lon, waterType, waterCondition);

        try {
            App.sourceReportDatabase.addEntry(report);
        } catch (IOException e) {
            App.err.error("Could not save Water Source Report");
        }

        App.current.setScene("HomeScreen");
    }

    @Override
    public void displayMessage(String message) {

    }

}
