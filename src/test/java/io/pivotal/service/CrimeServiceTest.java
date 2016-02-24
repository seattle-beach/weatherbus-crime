package io.pivotal.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.pivotal.response.input.CrimeResponse;
import io.pivotal.response.output.CrimeInfo;
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
    public void getCrimeInfo_shouldReturnAccurateCrimeInfoForInput() throws Exception {
        List<CrimeInfo.Offense> offenses = new ArrayList<>();
        offenses.add(new CrimeInfo.Offense("STOLEN PROPERTY",1));
        offenses.add(new CrimeInfo.Offense("RECKLESS BURNING", 1));
        offenses.add(new CrimeInfo.Offense("CAR PROWL", 2));

        Type listOfCrimeResponses = new TypeToken<ArrayList<CrimeResponse>>() {}.getType();
        List<CrimeResponse> responses = gson.fromJson(new FileReader("src/test/resources/input/DataSeattle.json"), listOfCrimeResponses);
        when(service.getCrimeResponses(QueryFormatUtilities.formatWhere(10, 15))).thenReturn(responses);
        CrimeInfo expected = new CrimeInfo(responses.size(), "CAR PROWL", 1, offenses);
        assertEquals(expected, subject.getCrimeInfo(10,15));
    }
}