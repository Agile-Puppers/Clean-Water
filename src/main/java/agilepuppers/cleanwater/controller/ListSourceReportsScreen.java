package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListSourceReportsScreen extends Controller {

    @FXML private TableView tableView;

    @FXML
    private void initialize() {
        //load reports from model
        List<WaterSourceReport> reports;

        try {
            reports = App.sourceReportDatabase.queryAllEntries();
        } catch (IOException e) {
            App.err.error("Could not load reports.");
            reports = new ArrayList<>();
        }

        //set up table view
        ObservableList<WaterSourceReport> observable = FXCollections.observableArrayList(reports);
        tableView.setItems(observable);

        TableColumn<WaterSourceReport, String> idColumn = new TableColumn<>("Report ID");
        idColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getReportId()));

        TableColumn<WaterSourceReport, String> nameColumn = new TableColumn<>("Author Name");
        nameColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getAuthorDisplayName()));

        TableColumn<WaterSourceReport, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getReportedLocation()));

        TableColumn<WaterSourceReport, String> typeColumn = new TableColumn<>("Water Type");
        typeColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getWaterType().toString()));

        TableColumn<WaterSourceReport, String> conditionColumn = new TableColumn<>("Water Condition");
        conditionColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getWaterCondition().toString()));

        tableView.getColumns().addAll(idColumn, nameColumn, locationColumn, typeColumn, conditionColumn);
    }

    @FXML
    public void backPressed() {
        App.current.setScene("HomeScreen");
    }

}
