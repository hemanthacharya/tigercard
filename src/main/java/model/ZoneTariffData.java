package model;

import enums.Zones;

import java.util.Map;

public class ZoneTariffData {

    private Map<Map<Zones, Zones>, PriceData> tariffData;

    public void setTariffData(Map<Map<Zones, Zones>, PriceData> tariffData) {
        this.tariffData = tariffData;
    }

    public Map<Map<Zones, Zones>, PriceData> getTariffData() {
        return tariffData;
    }

    public final class PriceData {

        private int PeakPrice;
        private int OffPeakPrice;
        private int weekPriceCap;
        private int dailyPriceCap;

        public PriceData(int peakPrice, int offPeakPrice, int dailyPriceCap, int weekPriceCap) {
            PeakPrice = peakPrice;
            OffPeakPrice = offPeakPrice;
            this.weekPriceCap = weekPriceCap;
            this.dailyPriceCap = dailyPriceCap;
        }

        public int getPeakPrice() {
            return PeakPrice;
        }

        public int getOffPeakPrice() {
            return OffPeakPrice;
        }

        public int getWeekPriceCap() {
            return weekPriceCap;
        }

        public int getDailyPriceCap() {
            return dailyPriceCap;
        }
    }
}
