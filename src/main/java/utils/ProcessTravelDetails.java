package utils;

import enums.Zones;
import model.TravelData;
import model.ZoneTariffData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*

Utility class to fill the travel details in corresponding models

ZoneTariffData - contains the tariff rules and capped price for every applicable combination of zones
List<TravelData> - contains the list of travel data converted to a TravelData model

 */
public class ProcessTravelDetails {

    public ZoneTariffData fillZoneTariffData(String filename) throws IOException {

        InputStream inputStream = getClass().getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        reader.readLine(); //ignore header

        ZoneTariffData zoneTariffData = new ZoneTariffData();
        Map<Map<Zones,Zones>, ZoneTariffData.PriceData> tariffData = new HashMap<>();

        for (String line; (line = reader.readLine()) != null;) {

            String[] values = line.split(",");

            String[] zone = values[0].split("-");

            Map<Zones,Zones> zonemap = new HashMap<>();
            zonemap.put(Zones.fromString(zone[0]), Zones.fromString(zone[1]));


            ZoneTariffData.PriceData priceData = zoneTariffData.new PriceData(Integer.valueOf(values[1]).intValue(), Integer.valueOf(values[2]).intValue(),
                    Integer.valueOf(values[3]).intValue(), Integer.valueOf(values[4]).intValue());

            tariffData.put(zonemap, priceData);

        }

        zoneTariffData.setTariffData(tariffData);
        return zoneTariffData;
    }

    public List<TravelData> processTravelData(ZoneTariffData zoneTariffData, String filename) throws IOException {

        InputStream inputStream = getClass().getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        reader.readLine(); //ignore header

        List<TravelData> travelDataList = new ArrayList<>();
        TravelData travelData;

        for (String line; (line = reader.readLine()) != null;) {

            String[] values = line.split(",");
            travelData = new TravelData(DayOfWeek.valueOf(values[0].toUpperCase()), LocalTime.parse(values[1]),
                    Zones.fromString(values[2]), Zones.fromString(values[3]));

            travelData.setPrice(determineAppropriatePrice(values, zoneTariffData));

            travelDataList.add(travelData);
        }

        return travelDataList;
    }

    public int determineAppropriatePrice(String[] values, ZoneTariffData zoneTariffData) {

        int price = 0;

        TariffRules tariffRules = new TariffRules();

        Map<Map<Zones, Zones>, ZoneTariffData.PriceData> tariffData = zoneTariffData.getTariffData();

        Zones fromZone = Zones.fromString(values[2]);
        Zones toZone = Zones.fromString(values[3]);

        Map<Zones,Zones> zonemap = new HashMap<>();
        zonemap.put(fromZone,toZone);

        ZoneTariffData.PriceData priceData = tariffData.get(zonemap);

        if (tariffRules.is_off_peak_hour(DayOfWeek.valueOf(values[0].toUpperCase()), values[1], fromZone.level, toZone.level)) {
            price = priceData.getOffPeakPrice();
        } else {
            price = priceData.getPeakPrice();
        }

        return price;
    }
}
