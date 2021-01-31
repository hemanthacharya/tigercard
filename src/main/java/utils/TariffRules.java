package utils;

import enums.Zones;

import java.time.DayOfWeek;
import java.time.LocalTime;

/*

A utility class to check if the Tariff rules like off peak and peak hours are checked for a particular traveldata
before applying price
 */
public class TariffRules {

    static LocalTime weekdayPeakStartMorning = LocalTime.parse("07:00");
    static LocalTime weekdayPeakEndMorning = LocalTime.parse("10:30");
    static LocalTime weekdayPeakStartEvening = LocalTime.parse("17:00");
    static LocalTime weekdayPeakEndEvening = LocalTime.parse("20:00");


    static LocalTime weekendPeakStartMorning = LocalTime.parse("09:00");
    static LocalTime weekendPeakEndMorning = LocalTime.parse("11:00");
    static LocalTime weekendPeakStartEvening = LocalTime.parse("18:00");
    static LocalTime weekendPeakEndEvening = LocalTime.parse("22:00");


    public boolean is_off_peak_hour(DayOfWeek dayOfWeek, String time, String fromZone, String toZone) {

        LocalTime travelTime = LocalTime.parse(time);

        if (!is_peak_hour(dayOfWeek, time) || (!Zones.Zone1.level.equals(fromZone) && Zones.Zone1.level.equals(toZone)
                && isTravelTimeInOffPeak(travelTime, isWeekDay(dayOfWeek)))) {
            return true;
        }

        return false;
    }

    private boolean is_peak_hour(DayOfWeek dayOfWeek, String time) {

        LocalTime travelTime = LocalTime.parse(time);

        if (isTravelTimeInPeak(travelTime, isWeekDay(dayOfWeek))) return true;

        return false;
    }

    private static boolean isTravelTimeInPeak(LocalTime travelTime, boolean isWeekday) {

        if (isWeekday) {
            if (travelTime.equals(weekdayPeakStartMorning) || travelTime.equals(weekdayPeakEndMorning) ||
                    travelTime.equals(weekdayPeakStartEvening) || travelTime.equals(weekdayPeakEndEvening) ||
                    (travelTime.isAfter(weekdayPeakStartMorning) && travelTime.isBefore(weekdayPeakEndMorning)) ||
                    (travelTime.isAfter(weekdayPeakStartEvening) && travelTime.isBefore(weekdayPeakEndEvening))) {
                return true;
            }

        } else {
            if (travelTime.equals(weekendPeakStartMorning) || travelTime.equals(weekendPeakEndMorning) ||
                    travelTime.equals(weekendPeakStartEvening) || travelTime.equals(weekendPeakEndEvening) ||
                    (travelTime.isAfter(weekendPeakStartMorning) && travelTime.isBefore(weekendPeakEndMorning)) ||
                    (travelTime.isAfter(weekendPeakStartEvening) && travelTime.isBefore(weekendPeakEndEvening))) {
                return true;
            }
        }

        return false;
    }

    private static boolean isTravelTimeInOffPeak(LocalTime travelTime, boolean isWeekday) {

        if (isWeekday) {
            if (travelTime.equals(weekdayPeakStartEvening) || travelTime.equals(weekdayPeakEndEvening) ||
                    (travelTime.isAfter(weekdayPeakStartEvening) && travelTime.isBefore(weekdayPeakEndEvening))) {
                return true;
            }

        } else {
            if (travelTime.equals(weekendPeakStartEvening) || travelTime.equals(weekendPeakEndEvening) ||
                    (travelTime.isAfter(weekendPeakStartEvening) && travelTime.isBefore(weekendPeakEndEvening))) {
                return true;
            }
        }

        return false;
    }

    private boolean isWeekDay(DayOfWeek dayOfWeek) {

        int val = dayOfWeek.getValue();

        if (val >= 1 && val <= 5) {
            return true;
        }

        return false;
    }

}
