package io.pivotal.response.output;

import lombok.Data;

@Data
public class CrimeInfo {
    private final int numberOfCrimes;
    private final String mostFrequentCrimeType;
    private final int numberOfViolentCrimes;
}
