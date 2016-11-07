package agilepuppers.cleanwater.test;

import agilepuppers.cleanwater.model.report.SafetyRating;
import agilepuppers.cleanwater.model.report.WaterPurityReport;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Cal Stephens on 11/6/16.
 */
public class WaterPurityReportTest {

    private double DOUBLE_COMPARISON_DELTA = 0.0000001;

    private HashMap<String, String> getValidHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put(WaterPurityReport.TIME_CREATED_KEY, new Date().toString());
        data.put(WaterPurityReport.LAT_KEY, "10.0");
        data.put(WaterPurityReport.LON_KEY, "10.0");
        data.put(WaterPurityReport.SAFETY_KEY, "SAFE");
        data.put(WaterPurityReport.CONTAMINANT_KEY, "100.0");
        data.put(WaterPurityReport.VIRUS_KEY, "10.5");

        return data;
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullHashMap() {
        WaterPurityReport report = new WaterPurityReport(null);
    }

    @Test
    public void testConstructorUsingValidInput() {
        HashMap<String, String> data = this.getValidHashMap();

        //verify constructor returns object
        WaterPurityReport report = new WaterPurityReport(data);
        Assert.assertNotNull(report);

        //verify object contains expected values
        Assert.assertEquals(Double.toString(report.getVirusPPM()), data.get(WaterPurityReport.VIRUS_KEY));
        Assert.assertEquals(Double.toString(report.getContaminantPPM()), data.get(WaterPurityReport.CONTAMINANT_KEY));
        Assert.assertEquals(report.getSafetyRating().toString(), data.get(WaterPurityReport.SAFETY_KEY));
    }

    @Test
    public void testConstructorUsingInvalidVirusPPM() {
        HashMap<String, String> data = this.getValidHashMap();
        data.put(WaterPurityReport.VIRUS_KEY, "not an integer");

        WaterPurityReport report = new WaterPurityReport(data);
        Assert.assertNotNull(report);
        Assert.assertEquals(report.getVirusPPM(), 0.0, DOUBLE_COMPARISON_DELTA);
    }

    @Test
    public void testConstructorUsingInvalidContaminantPPM() {
        HashMap<String, String> data = this.getValidHashMap();
        data.put(WaterPurityReport.CONTAMINANT_KEY, "not an integer");

        WaterPurityReport report = new WaterPurityReport(data);
        Assert.assertNotNull(report);
        Assert.assertEquals(report.getContaminantPPM(), 0.0, DOUBLE_COMPARISON_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingInvalidSafetyRating() {
        HashMap<String, String> data = this.getValidHashMap();
        data.put(WaterPurityReport.SAFETY_KEY, "invalid safety rating");

        new WaterPurityReport(data);
    }

}
