package io.pivotal.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.pivotal.response.input.CrimeResponse;
import io.pivotal.response.output.CrimeDetail;
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

    double latitude = 10;
    double longitude = 15;

    @Test
    public void getCrimeInfo_shouldReturnAccurateCrimeInfoForInput() throws Exception {
        List<CrimeInfo.Offense> offenses = new ArrayList<>();
        offenses.add(new CrimeInfo.Offense("STOLEN PROPERTY",1));
        offenses.add(new CrimeInfo.Offense("RECKLESS BURNING", 1));
        offenses.add(new CrimeInfo.Offense("CAR PROWL", 2));

        Type listOfCrimeResponses = new TypeToken<ArrayList<CrimeResponse>>() {}.getType();
        List<CrimeResponse> responses = gson.fromJson(new FileReader("src/test/resources/input/DataSeattle.json"), listOfCrimeResponses);
        when(service.getCrimeResponses(QueryFormatUtilities.formatWhere(latitude, longitude))).thenReturn(responses);
        CrimeInfo expected = new CrimeInfo(responses.size(), "CAR PROWL", 1, offenses);
        assertEquals(expected, subject.getCrimeInfo(latitude,longitude));
    }

    @Test
    public void getCrimeDetail_shouldReturnDetailedInfoForEachCrimeType() {
        CrimeDetail expected = new CrimeDetail("all", 8, "MEH CRIME", new ArrayList<CrimeDetail>() {{
            add(new CrimeDetail("violent", 3, "BAD CRIME", new ArrayList<CrimeDetail>() {{
                add(new CrimeDetail("HORRIBLE CRIME", 1, null, null));
                add(new CrimeDetail("BAD CRIME", 2, null, null));
            }}));
            add(new CrimeDetail("regular", 1, "OKAY CRIME", new ArrayList<CrimeDetail>() {{
                add(new CrimeDetail("OKAY CRIME", 1, null, null));
            }}));
            add(new CrimeDetail("mild", 4, "MEH CRIME", new ArrayList<CrimeDetail>() {{
                add(new CrimeDetail("MEH CRIME", 4, null, null));
            }}));
        }});
        assertEquals(expected, subject.getCrimeDetail(latitude,longitude));
    }
}