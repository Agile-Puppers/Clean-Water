package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterCondition;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import agilepuppers.cleanwater.model.report.WaterType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditSourceReportScreen extends Controller implements FormScreen {

    @FXML private TextField locationField;

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

    @FXML
    private void cancelReport() {
        App.current.setScene("HomeScreen");
    }

    @FXML
    private void saveReport() {
        String location = locationField.getText();

        if (location == null || location.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText("Invalid Location");
            alert.setContentText("Please enter a location.");
            alert.showAndWait();
            return;
        }

        WaterType waterType;
        if (waterTypeBottled.isSelected()) waterType = WaterType.BOTTLED;
        else if (waterTypeWell.isSelected()) waterType = WaterType.WELL;
        else if (waterTypeStream.isSelected()) waterType = WaterType.STREAM;
        else if (waterTypeLake.isSelected()) waterType = WaterType.LAKE;
        else if (waterTypeSpring.isSelected()) waterType = WaterType.SPRING;
        else waterType = WaterType.OTHER;

        WaterCondition waterCondition;
        if (waterConditionPotable.isSelected()) waterCondition = WaterCondition.POTABLE;
        else if (waterConditionWaste.isSelected()) waterCondition = WaterCondition.WASTE;
        else if (waterConditionTreatableClear.isSelected()) waterCondition = WaterCondition.TREATABLE_CLEAR;
        else waterCondition = WaterCondition.TREATABLE_MUDDY;

        WaterSourceReport report = new WaterSourceReport(App.current.getUser(), location, waterType, waterCondition);

        try {
            App.sourceReportDatabase.addEntry(report);
        } catch (IOException e) {
            displayMessage("Could not save source report");
        }

        App.current.setScene("HomeScreen");
    }

    @Override
    public void displayMessage(String message) {

    }
}
