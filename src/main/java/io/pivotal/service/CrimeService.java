package io.pivotal.service;

import io.pivotal.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrimeService {
    @Autowired
    IDataSeattleService service;

    public int getNumberOfCrimes(double latitude, double longitude) {
        return service.getCrimeResponse(Utilities.formatWhere(latitude, longitude)).size();
    }
}
