package enums;

public enum Zones {

    Zone1("1"), Zone2("2");

    public String level;

    Zones(String level) {
        this.level = level;
    }

    public static Zones fromString(String le) {
        for (Zones b : Zones.values()) {
            if (b.level.equalsIgnoreCase(le)) {
                return b;
            }
        }
        return null;
    }

}
