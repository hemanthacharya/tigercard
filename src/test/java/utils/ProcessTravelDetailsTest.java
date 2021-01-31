package utils;

import model.ZoneTariffData;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ProcessTravelDetailsTest {

    private ZoneTariffData zoneTariffData = null;

    @Before
    public void setUp() {

        ProcessTravelDetails processTravelDetails = new ProcessTravelDetails();

        String tariffFilename = "/tariff";

        try {
            zoneTariffData = processTravelDetails.fillZoneTariffData(tariffFilename);
        } catch (IOException e) {
            System.out.println("Error while reading TariffData" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void determineAppropriatePrice() {

        ProcessTravelDetails processTravelDetails = new ProcessTravelDetails();

        String[] values = {"Monday", "10:30", "2", "1"};

        assertEquals(35, processTravelDetails.determineAppropriatePrice(values, zoneTariffData));


        String[] values1 = {"Tuesday", "10:40", "2", "2"};

        assertEquals(20, processTravelDetails.determineAppropriatePrice(values1, zoneTariffData));

    }
}