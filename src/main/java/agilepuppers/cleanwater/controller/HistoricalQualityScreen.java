package agilepuppers.cleanwater.controller;

import agilepuppers.cleanwater.App;
import agilepuppers.cleanwater.model.report.WaterPurityReport;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class HistoricalQualityScreen extends Controller {

    @FXML private LineChart<String, Double> graph;
    @FXML private RadioButton showViruses;
    @FXML private RadioButton showContaminants;

    private WaterSourceReport associatedSourceReport;
    private GraphDisplay graphDisplay = GraphDisplay.VIRUSES;

    private enum GraphDisplay {
        VIRUSES("Virus PPM", WaterPurityReport::getVirusPPM),
        CONTAMINANTS("Contaminant PPM", WaterPurityReport::getContaminantPPM);

        private final String displayString;
        private final ToDoubleFunction<WaterPurityReport> attributeGetter;

        GraphDisplay(String displayString, ToDoubleFunction<WaterPurityReport> attributeGetter) {
            this.displayString = displayString;
            this.attributeGetter = attributeGetter;
        }
    }


    //Setup

    @FXML
    private void initialize() {
        this.graph.setAnimated(false);
    }

    @Override
    public void receiveUserData(Object data) {
        if (data instanceof WaterSourceReport) {
            this.associatedSourceReport = (WaterSourceReport)data;
            this.configureGraph();
        }
    }

    private static boolean approxEqual(double a, double b) {
        double difference = 0.0000001;
        return (Math.abs(a - b) < difference);
    }

    private List<WaterPurityReport> associatedPurityReports() {
        List<WaterPurityReport> associatedPurityReports = new ArrayList<>();

        try {
            List<WaterPurityReport> allPurityReports = App.purityReportDatabase.queryAllEntries();

            for (WaterPurityReport report : allPurityReports) {
                if (approxEqual(report.getLongitude(), this.associatedSourceReport.getLongitude())
                        && approxEqual(report.getLatitude(), this.associatedSourceReport.getLatitude())) {
                    associatedPurityReports.add(report);
                }
            }
        } catch (IOException e) {
            App.err.error("Could not load purity reports.");
        }

        return associatedPurityReports;
    }

    @SuppressWarnings("deprecation")
    private void configureGraph() {
        List<WaterPurityReport> associatedPurityReports = this.associatedPurityReports();

        LineChart.Series<String, Double> series = new LineChart.Series<>();
        series.setName(this.graphDisplay.displayString);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (int i = 0; i < months.length; i++) {

            final int monthNumber = i;
            double value = associatedPurityReports.stream()
                    .filter(report -> report.getTimeCreated().getMonth() == monthNumber)
                    .mapToDouble(this.graphDisplay.attributeGetter)
                    .average().orElse(0);

            LineChart.Data point = new LineChart.Data<>(months[i], value);
            series.getData().add(point);
        }

        if (this.graph.getData().size() > 0) this.graph.getData().remove(0);
        this.graph.getData().add(series);
    }


    //User Interaction

    @FXML
    private void graphSettingChanged() {
        if (showViruses.isSelected()) this.graphDisplay = GraphDisplay.VIRUSES;
        else this.graphDisplay = GraphDisplay.CONTAMINANTS;

        this.configureGraph();
    }

    @FXML
    private void backClicked() {
        App.current.setScene("ViewSourceReportScreen", this.associatedSourceReport);
    }

}
