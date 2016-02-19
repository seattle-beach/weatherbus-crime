package io.pivotal.service;

import com.google.gson.Gson;
import io.pivotal.input_response.CrimeResponse;
import io.pivotal.utilities.QueryFormatUtilities;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileReader;
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
        List<CrimeResponse> responses = (List<CrimeResponse>) gson.fromJson(new FileReader("src/test/resources/input/DataSeattle.json"), List.class);
        when(service.getCrimeResponse(QueryFormatUtilities.formatWhere(10,15))).thenReturn(responses);
        assertEquals(responses.size(), subject.getNumberOfCrimes(10, 15));
    }
}