package model;

import enums.Zones;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TravelData {

    private DayOfWeek dayOfWeek;
    private LocalTime travelTime;
    private Zones fromZone;
    private Zones toZone;
    private int price;

    public TravelData(DayOfWeek dayOfWeek, LocalTime travelTime, Zones fromZone, Zones toZone) {
        this.dayOfWeek = dayOfWeek;
        this.travelTime = travelTime;
        this.fromZone = fromZone;
        this.toZone = toZone;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getTravelTime() {
        return travelTime;
    }

    public Zones getFromZone() {
        return fromZone;
    }

    public Zones getToZone() {
        return toZone;
    }

    public void setPrice(int price) {this.price = price; }

    public int getPrice() {
        return price;
    }
}
