package io.pivotal.response.output;

import lombok.Data;

@Data
public class CrimeWrapper {
    private final CrimeData data;

    @Data
    public static class CrimeData {
        private final int numberOfCrimes;
    }
}
