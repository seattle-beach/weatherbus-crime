package io.pivotal.service;

import com.google.common.collect.Maps;
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

    private final List<String> violentCrimeNames = new ArrayList<>(Arrays.asList(
            "ASSAULT", "BURGLARY", "HOMICIDE", "INJURY",
            "RECKLESS BURNING", "ROBBERY", "THREATS", "WEAPON", "BURGLARY-SECURE PARKING-RES", "EXTORTION"
    ));

    private final List<String> regularCrimeNames = new ArrayList<>(Arrays.asList(
            "ANIMAL COMPLAINT", "WARRANT ARREST", "VIOLATION OF COURT ORDER", "VEHICLE THEFT", "TRESPASS",
            "THEFT OF SERVICES", "STOLEN PROPERTY", "STAY OUT OF AREA OF DRUGS", "SHOPLIFTING", "ROBBERY",
            "PURSE SNATCH", "PROSTITUTION", "PROPERTY DAMAGE", "PICKPOCKET", "OTHER PROPERTY", "NARCOTICS", "OBSTRUCT",
            "MAIL THEFT", "LIQUOR VIOLATION", "ILLEGAL DUMPING", "FRAUD", "ELUDING", "DUI", "DISTURBANCE",
            "DISORDERLY CONDUCT", "CAR PROWL", "BIKE THEFT"
    ));

    private final List<String> incidentNames = new ArrayList<>(Arrays.asList(
            "VIOLATION OF COURT ORDER", "TRAFFIC", "RECOVERED PROPERTY", "PUBLIC NUISANCE", "LOST PROPERTY",
            "LOITERING", "[INC - CASE DC USE ONLY]", "FORGERY", "FIREWORK", "FALSE REPORT", "ESCAPE",
            "EMBEZZLE", "DISPUTE", "COUNTERFEIT", "BIAS INCIDENT", "ANIMAL COMPLAINT"
    ));

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

        Map<String, Integer> crimeCounts = new HashMap<>();

        for(CrimeResponse response : responses) {
            if (crimeCounts.containsKey(response.getSummarized_offense_description())) {
                crimeCounts.put(response.getSummarized_offense_description(),
                        crimeCounts.get(response.getSummarized_offense_description()) + 1);
            } else {
                crimeCounts.put(response.getSummarized_offense_description(), 1);
            }
        }

        List<CrimeDetail> violentCrimes = new ArrayList<>();
        List<CrimeDetail> regularCrimes = new ArrayList<>();
        List<CrimeDetail> mildCrimes = new ArrayList<>();

        int maxCount = -1;
        String maxType = "";
        for(String crimeType : crimeCounts.keySet()) {
            CrimeDetail crime = new CrimeDetail(crimeType, crimeCounts.get(crimeType), null, null);
            if(violentCrimeNames.contains(crime.getType())) {
                violentCrimes.add(crime);
            } else if(regularCrimeNames.contains(crime.getType())) {
                regularCrimes.add(crime);
            } else {
                mildCrimes.add(crime);
            }
            if (crimeCounts.get(crimeType) > maxCount) {
                maxCount = crimeCounts.get(crimeType);
                maxType = crimeType;
            }
        }

        CrimeDetail detailViolent = new CrimeDetail("violent", countCrimes(violentCrimes), getPopularType(violentCrimes), violentCrimes);
        CrimeDetail detailRegular = new CrimeDetail("regular", countCrimes(regularCrimes), getPopularType(regularCrimes), regularCrimes);
        CrimeDetail detailMild = new CrimeDetail("mild", countCrimes(mildCrimes), getPopularType(mildCrimes), mildCrimes);

        return new CrimeDetail("all", responses.size(), maxType, new ArrayList<>(Arrays.asList(
                detailViolent, detailRegular, detailMild
        )));
    }

    private int countCrimes(List<CrimeDetail> crimeDetails) {
        int count = 0;
        for (CrimeDetail crimeDetail : crimeDetails) {
            count += crimeDetail.getTotal();
        }
        return count;
    }

    private String getPopularType(List<CrimeDetail> crimeDetails) {
        int maxCount = -1;
        String maxType = "";
        for (CrimeDetail crimeDetail : crimeDetails) {
            if (crimeDetail.getTotal() > maxCount) {
                maxCount = crimeDetail.getTotal();
                maxType = crimeDetail.getType();
            }
        }
        return maxType;
    }
}
