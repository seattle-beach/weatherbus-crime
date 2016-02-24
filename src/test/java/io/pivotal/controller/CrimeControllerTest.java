package io.pivotal.controller;

import io.pivotal.response.output.CrimeInfo;
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
    @Mock
    CrimeInfo info;

    @InjectMocks
    CrimeController subject;

    private MockMvc mockMvc;

    @Test
    public void getInfo_withNoParams_shouldDefaultToPivotalOffice() throws Exception {
        when(service.getCrimeInfo(47.599189, -122.333888)).thenReturn(new CrimeInfo(4, "CAR PROWL", 1));
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        mockMvc.perform(get("/api/info")).andExpect(json().isEqualTo(TestUtilities.jsonFileToString("src/test/resources/output/CrimeInfo.json")));
    }

    @Test
    public void getInfo() throws Exception {
        when(service.getCrimeInfo(10,15)).thenReturn(new CrimeInfo(4, "CAR PROWL", 1));
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        mockMvc.perform(get("/api/info?lat=10&lng=15")).andExpect(json().isEqualTo(TestUtilities.jsonFileToString("src/test/resources/output/CrimeInfo.json")));
    }
}
