package io.pivotal.service;

import com.google.gson.Gson;
import io.pivotal.response.CrimeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
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
        when(service.getCrimeResponse(anyString())).thenReturn(responses);
        assertEquals(responses.size(), subject.getNumberOfCrimes());
    }
}