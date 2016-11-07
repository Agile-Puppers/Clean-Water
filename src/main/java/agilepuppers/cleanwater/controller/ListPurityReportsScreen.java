package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterPurityReport;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListPurityReportsScreen extends Controller {

    @FXML private TableView tableView;

    @FXML
    private void initialize() {
        //load reports from model
        List<WaterPurityReport> reports;

        try {
            reports = App.purityReportDatabase.queryAllEntries();
        } catch (IOException e) {
            App.err.error("Could not load reports.");
            reports = new ArrayList<>();
        }

        //set up table view
        ObservableList<WaterPurityReport> observable = FXCollections.observableArrayList(reports);
        tableView.setItems(observable);

        TableColumn<WaterPurityReport, String> idColumn = new TableColumn<>("Report ID");
        idColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getReportId()));

        TableColumn<WaterPurityReport, String> nameColumn = new TableColumn<>("Author Name");
        nameColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getAuthorDisplayName()));

        TableColumn<WaterPurityReport, String> latColumn = new TableColumn<>("Latitude");
        latColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getLatitude()));

        TableColumn<WaterPurityReport, String> lonColumn = new TableColumn<>("Longitude");
        lonColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getLongitude()));

        TableColumn<WaterPurityReport, String> safetyColumn = new TableColumn<>("Safety Rating");
        safetyColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getSafetyRating().getFriendlyString()));

        TableColumn<WaterPurityReport, String> contaminantColumn = new TableColumn<>("Contaminant PPM");
        contaminantColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getContaminantPPM()));

        TableColumn<WaterPurityReport, String> virusColumn = new TableColumn<>("Virus PPM");
        virusColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().getVirusPPM()));

        tableView.getColumns().addAll(idColumn, nameColumn, latColumn, lonColumn, safetyColumn, contaminantColumn, virusColumn);
    }

    @FXML
    public void backPressed() {
        App.current.setScene("HomeScreen");
    }

}
