package io.pivotal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrimeService {
    @Autowired
    IDataSeattleService service;

    public int getNumberOfCrimes() {
        String where = "within_circle(location,%f,%f,150) AND occurred_date_or_date_range_start>%s";
        return service.getCrimeResponse(String.format(where, 47.599189, -122.333888, "'2015-08-17T00:00:00.000'")).size();
    }
}
