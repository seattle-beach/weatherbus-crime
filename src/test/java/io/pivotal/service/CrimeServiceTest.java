package io.pivotal.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.pivotal.response.input.CrimeResponse;
import io.pivotal.utilities.QueryFormatUtilities;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CrimeServiceTest {
    @Mock
    IDataSeattleService service;

    @InjectMocks CrimeService subject;

    Gson gson = new Gson();

    @Test
    public void getNumberOfCrimes_shouldReturnATotalOfCrimesInTheArea() throws Exception {
        List<CrimeResponse> responses = gson.fromJson(new FileReader("src/test/resources/input/DataSeattle.json"), List.class);
        when(service.getCrimeResponses(QueryFormatUtilities.formatWhere(10,15))).thenReturn(responses);
        assertEquals(responses.size(), subject.getNumberOfCrimes(10, 15));
    }

    @Test
    public void getCrimeInfo_shouldReturnAccurateCrimeInfoForInput() throws Exception {
        Type listOfCrimeResponses = new TypeToken<ArrayList<CrimeResponse>>() {}.getType();
        List<CrimeResponse> responses = gson.fromJson(new FileReader("src/test/resources/input/DataSeattle.json"), listOfCrimeResponses);
        when(service.getCrimeResponses(QueryFormatUtilities.formatWhere(10, 15))).thenReturn(responses);
        assertEquals(responses.size(), subject.getCrimeInfo(10,15).getNumberOfCrimes());
        assertEquals("CAR PROWL", subject.getCrimeInfo(10,15).getMostFrequentCrimeType());
        assertEquals(1, subject.getCrimeInfo(10,15).getNumberOfViolentCrimes());
    }
}