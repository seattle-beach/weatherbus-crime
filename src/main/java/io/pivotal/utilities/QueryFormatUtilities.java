package io.pivotal.utilities;

import org.joda.time.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryFormatUtilities {
    public static String formatWhere(double latitude, double longitude) {
        String where = "within_circle(location,%f,%f,150) AND occurred_date_or_date_range_start>'%s'";
        return String.format(where, latitude, longitude, getStartDate());
    }

    private static String getStartDate() {
        long currentTime = DateTimeUtils.currentTimeMillis();
        final long millisInDay = 24 * 60 * 60 * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        return format.format(new Date(currentTime - (180L * millisInDay)));
    }
}
