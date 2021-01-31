import model.TravelData;
import model.ZoneTariffData;
import service.TotalTravelCost;
import utils.ProcessTravelDetails;

import java.io.IOException;
import java.util.List;

public class TigerCardClient {

    public static void main(String[] args) {

        ProcessTravelDetails processTravelDetails = new ProcessTravelDetails();

        String tariffFilename = "/tariff";
        String travelDataFilename = "/travelData";

        ZoneTariffData zoneTariffData = null;
        try {
            zoneTariffData = processTravelDetails.fillZoneTariffData(tariffFilename);
        } catch (IOException e) {
            System.out.println("Error while reading TariffData" + e.getMessage());
            e.printStackTrace();
        }

        List<TravelData> travelDataList = null;
        try {
            travelDataList = processTravelDetails.processTravelData(zoneTariffData, travelDataFilename);
        } catch (IOException e) {
            System.out.println("Error while reading TravelData" + e.getMessage());
            e.printStackTrace();
        }

        if (zoneTariffData == null || travelDataList == null) {
            System.out.println("Error in reading files");
        } else {

            TotalTravelCost totalTravelCost = new TotalTravelCost(travelDataList, zoneTariffData);
            System.out.println("Total cost: " + totalTravelCost.calculateTotalFare());

        }
    }
}
