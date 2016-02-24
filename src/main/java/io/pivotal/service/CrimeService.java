package io.pivotal.service;

import io.pivotal.response.input.CrimeResponse;
import io.pivotal.response.output.CrimeInfo;
import io.pivotal.utilities.QueryFormatUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class CrimeService {
    @Autowired
    IDataSeattleService service;

    private final List<String> violentCrimeNames = new ArrayList<>(
            Arrays.asList("ASSAULT", "BURGLARY", "HOMICIDE", "INJURY",
                    "RECKLESS BURNING", "ROBBERY", "THREATS", "WEAPON"));

    public CrimeInfo getCrimeInfo(double latitude, double longitude) {
        List<CrimeResponse> responses = service.getCrimeResponses(QueryFormatUtilities.formatWhere(latitude, longitude));

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
            }
            else {
                crimeNamesToFrequency.put(cr.getSummarized_offense_description(), 1);
            }
            if (crimeNamesToFrequency.get(cr.getSummarized_offense_description()) > mostFrequentCrimeCount) {
                mostFrequentCrimeName = cr.getSummarized_offense_description();
            }
            if (violentCrimeNames.contains(cr.getSummarized_offense_description())) {
                violentCrimeCount++;
            }
        }

        return new CrimeInfo(responses.size(), mostFrequentCrimeName, violentCrimeCount);
    }
}
