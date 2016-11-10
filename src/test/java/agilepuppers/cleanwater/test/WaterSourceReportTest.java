package agilepuppers.cleanwater.test;

import agilepuppers.cleanwater.model.report.WaterCondition;
import agilepuppers.cleanwater.model.report.WaterSourceReport;
import agilepuppers.cleanwater.model.report.WaterType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Nathan Lai on 11/7/2016.
 */
public class WaterSourceReportTest {
    private HashMap<String, String> getValidHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put(WaterSourceReport.TIME_CREATED_KEY, new Date().toString());
        data.put(WaterSourceReport.LAT_KEY, "10.0");
        data.put(WaterSourceReport.LON_KEY, "10.0");
        data.put(WaterSourceReport.TYPE_KEY, "BOTTLED");
        data.put(WaterSourceReport.CONDITION_KEY, "POTABLE");

        return data;
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullHashMap() {
        new WaterSourceReport(null);
    }

    @Test
    public void testConstructorUsingValidInput() {
        HashMap<String, String> data = this.getValidHashMap();

        //verify constructor returns object
        WaterSourceReport report = new WaterSourceReport(data);
        Assert.assertNotNull(report);

        //verify object contains expected values
        Assert.assertEquals(report.getWaterType().toString(), data.get(WaterSourceReport.TYPE_KEY));
        Assert.assertEquals(report.getWaterCondition().toString(), data.get(WaterSourceReport.CONDITION_KEY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingInvalidWaterType() {
        HashMap<String, String> data = this.getValidHashMap();
        data.put(WaterSourceReport.TYPE_KEY, "not a WaterType");

        WaterSourceReport report = new WaterSourceReport(data);
        Assert.assertNotNull(report);
        Assert.assertEquals(report.getWaterType(), WaterType.BOTTLED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorUsingInvalidWaterCondition() {
        HashMap<String, String> data = this.getValidHashMap();
        data.put(WaterSourceReport.CONDITION_KEY, "not a WaterCondition");

        WaterSourceReport report = new WaterSourceReport(data);
        Assert.assertNotNull(report);
        Assert.assertEquals(report.getWaterCondition(), WaterCondition.POTABLE);
    }
}
