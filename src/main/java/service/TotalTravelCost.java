package service;

import enums.Zones;
import model.TravelData;
import model.ZoneTariffData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TotalTravelCost {

    private List<TravelData> travelDataList;
    private ZoneTariffData zoneTariffData;

    public TotalTravelCost(List<TravelData> travelDataList, ZoneTariffData zoneTariffData) {
        this.travelDataList = travelDataList;
        this.zoneTariffData = zoneTariffData;
    }

    public int calculateTotalFare() {

        int totalPrice = 0;
        int weeklyPrice = 0;
        int dailyPrice = 0;

        int currentDay;
        int previousDay = -1;

        Iterator<TravelData> listIterator = travelDataList.listIterator();

        Map<Map<Zones, Zones>, ZoneTariffData.PriceData> tariffData = zoneTariffData.getTariffData();

        int weekly_cap = 0;
        int daily_cap = 0;

        while (listIterator.hasNext()) {

            TravelData data = listIterator.next();
            currentDay = data.getDayOfWeek().getValue();

            Map<Zones, Zones> travelZone = new HashMap<>();
            travelZone.put(data.getFromZone(), data.getToZone());

            ZoneTariffData.PriceData priceData = tariffData.get(travelZone);

            // calculate daily prices
            if (previousDay == -1 || (currentDay == previousDay)) {

                daily_cap = daily_cap < priceData.getDailyPriceCap() ? priceData.getDailyPriceCap() : daily_cap;
                weekly_cap = weekly_cap < priceData.getWeekPriceCap() ? priceData.getWeekPriceCap() : weekly_cap;

                dailyPrice += data.getPrice();

                if (!listIterator.hasNext()) {
                    totalPrice += dailyPrice > daily_cap ? daily_cap : dailyPrice;
                }

              // calculate weekly price
            } else if ((currentDay != previousDay) && previousDay < currentDay) {

                dailyPrice = dailyPrice > daily_cap ? daily_cap : dailyPrice;

                weeklyPrice += dailyPrice;

                dailyPrice = data.getPrice();

                daily_cap = priceData.getDailyPriceCap();
                weekly_cap = weekly_cap < priceData.getWeekPriceCap() ? priceData.getWeekPriceCap() : weekly_cap;

                if (!listIterator.hasNext()) {
                    totalPrice += (weeklyPrice + dailyPrice) > weekly_cap ? weekly_cap : (weeklyPrice + dailyPrice);
                }

                // calculate multi weekly price
            } else if ((currentDay != previousDay) && previousDay > currentDay) {

                totalPrice += (weeklyPrice + dailyPrice) > weekly_cap ? weekly_cap : (weeklyPrice + dailyPrice);

                weeklyPrice = 0;
                dailyPrice = data.getPrice();
                daily_cap = priceData.getDailyPriceCap();
                weekly_cap = priceData.getWeekPriceCap();

                if (!listIterator.hasNext()) {
                    totalPrice += dailyPrice > daily_cap ? daily_cap : dailyPrice;
                }

            }

            previousDay = currentDay; // track previous day and current to see when the day ends or week ends

        }

        return totalPrice;

    }
}
