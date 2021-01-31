package service;

import model.TravelData;
import model.ZoneTariffData;
import org.junit.Before;
import org.junit.Test;
import utils.ProcessTravelDetails;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TotalTravelCostTest {

    private ZoneTariffData zoneTariffData = null;
    private List<TravelData> travelDataDailyList = null;
    private List<TravelData> travelDataWeeklyList = null;
    private List<TravelData> travelDataMultiWeekList = null;

    @Before
    public void setUp() {

        ProcessTravelDetails processTravelDetails = new ProcessTravelDetails();

        String tariffFilename = "/tariff";

        String travelDataDailyFilename = "/travelDataDaily";

        String travelDataWeeklyFilename = "/travelDataWeekly";

        String travelDataMultiWeekFilename = "/travelDataMultiweek";


        try {
            zoneTariffData = processTravelDetails.fillZoneTariffData(tariffFilename);
        } catch (IOException e) {
            System.out.println("Error while reading TariffData" + e.getMessage());
            e.printStackTrace();
        }

        try {
            travelDataDailyList = processTravelDetails.processTravelData(zoneTariffData, travelDataDailyFilename);
            travelDataWeeklyList = processTravelDetails.processTravelData(zoneTariffData, travelDataWeeklyFilename);
            travelDataMultiWeekList = processTravelDetails.processTravelData(zoneTariffData, travelDataMultiWeekFilename);
        } catch (IOException e) {
            System.out.println("Error while reading TravelData" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void calculateDailyTotalFare() {

        if (zoneTariffData == null || travelDataDailyList == null) {
            System.out.println("Error in reading daily traveldata files");
        } else {

            TotalTravelCost totalTravelCost = new TotalTravelCost(travelDataDailyList, zoneTariffData);
            assertEquals(totalTravelCost.calculateTotalFare(), 120);

        }
    }

    @Test
    public void calculateWeeklyTotalFare() {

        if (zoneTariffData == null || travelDataWeeklyList == null) {
            System.out.println("Error in reading weekly traveldata files");
        } else {

            TotalTravelCost totalTravelCost = new TotalTravelCost(travelDataWeeklyList, zoneTariffData);
            assertEquals(totalTravelCost.calculateTotalFare(), 200);

        }
    }

    @Test
    public void calculateMultiWeeklyTotalFare() {

        if (zoneTariffData == null || travelDataMultiWeekList == null) {
            System.out.println("Error in reading multi week traveldata files");
        } else {

            TotalTravelCost totalTravelCost = new TotalTravelCost(travelDataMultiWeekList, zoneTariffData);
            assertEquals(totalTravelCost.calculateTotalFare(), 235);

        }
    }
}