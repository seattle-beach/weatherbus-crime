package io.pivotal.utilities;

public class Utilities {
    public static String formatWhere(double latitude, double longitude) {
        String where = "within_circle(location,%f,%f,150) AND occurred_date_or_date_range_start>%s";
        return String.format(where, latitude, longitude, "'2015-08-17T00:00:00.000'");
    }
}
