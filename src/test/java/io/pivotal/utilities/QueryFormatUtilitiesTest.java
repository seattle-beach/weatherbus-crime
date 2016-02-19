package io.pivotal.utilities;

import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class QueryFormatUtilitiesTest {
    @Test
    public void getStartDate_shouldReturnADateTimeStringSixMonthsAgo() {
        DateTimeUtils.setCurrentMillisFixed(1455911743000L); //19 Feb 2016 19:55:43 GMT
        String expected = "within_circle(location,5.317000,2.413000,150) AND occurred_date_or_date_range_start>'2015-08-23T12:55:43'"; // 1 hour off because DTS in August
        assertEquals(expected,QueryFormatUtilities.formatWhere(5.317,2.413));
    }
}