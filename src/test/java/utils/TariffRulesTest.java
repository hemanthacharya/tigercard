package utils;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;

import static org.junit.Assert.*;

public class TariffRulesTest {

    TariffRules trafficRules;

    @Before
    public void setUp() {
        trafficRules = new TariffRules();
    }

    @Test
    public void is_off_peak_hour() {

        String travelTime = "10:45";
        String dayOfweek = "Monday";

        assertTrue(trafficRules.is_off_peak_hour(DayOfWeek.valueOf(dayOfweek.toUpperCase()), travelTime, "2", "1"));

        travelTime = "17:30";
        dayOfweek = "Tuesday";

        assertFalse(trafficRules.is_off_peak_hour(DayOfWeek.valueOf(dayOfweek.toUpperCase()), travelTime, "1", "1"));

        travelTime = "10:30";
        dayOfweek = "Wednesday";

        assertFalse(trafficRules.is_off_peak_hour(DayOfWeek.valueOf(dayOfweek.toUpperCase()), travelTime, "1", "1"));
    }
}