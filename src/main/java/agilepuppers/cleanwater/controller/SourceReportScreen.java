package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Created by cal on 10/6/16.
 */
public class SourceReportScreen extends Controller {

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
        System.out.println("Saving report");
    }

}
