package io.pivotal.service;

import io.pivotal.response.input.CrimeResponse;
import io.pivotal.response.output.CrimeDetail;
import io.pivotal.response.output.CrimeInfo;
import io.pivotal.utilities.QueryFormatUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class CrimeService {
    @Autowired
    IDataSeattleService service;
    private List<String> mildCrimes = new ArrayList<>(
            Arrays.asList("200", "300", "1700", "2500", "2600", "2700"));

    private List<String> regularCrimes = new ArrayList<>(
            Arrays.asList("100", "2200", "2200", "2299", "2300", "2400", "2800"));

    private List<String> violentCrimes = new ArrayList<>(
            Arrays.asList("900","1000", "1100", "1200", "1300", "1400", "1600",
                    "2000", "2100", "2900"));

    private final List<String> violentCrimeNames = new ArrayList<>(
            Arrays.asList("ASSAULT", "BURGLARY", "HOMICIDE", "INJURY",
                    "RECKLESS BURNING", "ROBBERY", "THREATS", "WEAPON"));

    public CrimeInfo getCrimeInfo(double latitude, double longitude) {
        List<CrimeResponse> responses = service.getCrimeResponses(QueryFormatUtilities.formatWhere(latitude, longitude));
        List<CrimeInfo.Offense> offenses = new ArrayList<>();

//        This is the same code as below in Java 8 style

//        final String[] mostFrequentCrime = new String[1];
//        responses.stream().collect(Collectors.groupingBy(cr -> cr.getSummarized_offense_description(), Collectors.counting()))
//                .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(1)
//                .forEach(x -> mostFrequentCrime[0] = x.getKey());

        String mostFrequentCrimeName = "";
        Integer mostFrequentCrimeCount = 0;
        Integer violentCrimeCount = 0;
        Map<String, Integer> crimeNamesToFrequency = new HashMap<>();
        for (CrimeResponse cr : responses) {
            if (crimeNamesToFrequency.containsKey(cr.getSummarized_offense_description())) {
                crimeNamesToFrequency.put(cr.getSummarized_offense_description(), crimeNamesToFrequency.get(cr.getSummarized_offense_description()) + 1);
            } else {
                crimeNamesToFrequency.put(cr.getSummarized_offense_description(), 1);
            }
            if (crimeNamesToFrequency.get(cr.getSummarized_offense_description()) > mostFrequentCrimeCount) {
                mostFrequentCrimeName = cr.getSummarized_offense_description();
                mostFrequentCrimeCount++;
            }
            if (violentCrimeNames.contains(cr.getSummarized_offense_description())) {
                violentCrimeCount++;
            }
        }

        crimeNamesToFrequency.forEach((name,frequency) -> offenses.add(new CrimeInfo.Offense(name, frequency)));
        return new CrimeInfo(responses.size(), mostFrequentCrimeName, violentCrimeCount, offenses);
    }

    public CrimeDetail getCrimeDetail(double latitude, double longitude) {
        List<CrimeResponse> responses = service.getCrimeResponses(QueryFormatUtilities.formatWhere(latitude, longitude));
        return null;
    }
}
