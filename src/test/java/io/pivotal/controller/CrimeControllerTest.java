package io.pivotal.controller;

import io.pivotal.service.CrimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utilities.TestUtilities;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class CrimeControllerTest {
    @Mock
    CrimeService service;

    @InjectMocks
    CrimeController subject;

    private MockMvc mockMvc;

    @Test
    public void getNumber_shouldReturnTheNumberOfCrimes() throws Exception {
        when(service.getNumberOfCrimes(10,15)).thenReturn(4);
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        mockMvc.perform(get("/api/?lat=10&lng=15")).andExpect(json().isEqualTo(TestUtilities.fixtureOutputJsonFileToString("NumberCrimes")));
    }

    @Test
    public void getNumber_withNoParams_shouldDefaultToPivotalOffice() throws Exception {
        when(service.getNumberOfCrimes(47.599189, -122.333888)).thenReturn(4);
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        mockMvc.perform(get("/api/")).andExpect(json().isEqualTo(TestUtilities.fixtureOutputJsonFileToString("NumberCrimes")));
    }
}
