package io.pivotal.response.output;

import lombok.Data;

import java.util.List;

@Data
public class CrimeInfo {
    private final int numberOfCrimes;
    private final String mostFrequentCrimeType;
    private final int numberOfViolentCrimes;
    private final List<Offense> offenses;

    @Data
    static public class Offense {
        private final String name;
        private final int frequency;
    }
}
