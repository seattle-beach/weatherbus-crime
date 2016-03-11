package io.pivotal.controller;

import io.pivotal.response.output.CrimeDetail;
import io.pivotal.response.output.CrimeInfo;
import io.pivotal.service.CrimeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utilities.TestUtilities;

import java.util.ArrayList;
import java.util.List;

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

    List<CrimeInfo.Offense> offenses = new ArrayList<>();

    @Before
    public void setup() {
        offenses.add(new CrimeInfo.Offense("CAR PROWL", 2));
        offenses.add(new CrimeInfo.Offense("STOLEN PROPERTY",1));
        offenses.add(new CrimeInfo.Offense("RECKLESS BURNING", 1));

        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
    }

    @Test
    public void getInfo_withNoParams_shouldDefaultToPivotalOffice() throws Exception {
        when(service.getCrimeInfo(47.599189, -122.333888)).thenReturn(new CrimeInfo(4, "CAR PROWL", 1, offenses));
        mockMvc.perform(get("/api/info")).andExpect(json().isEqualTo(TestUtilities.jsonFileToString("src/test/resources/output/CrimeInfo.json")));
    }

    @Test
    public void getInfo() throws Exception {
        when(service.getCrimeInfo(10, 15)).thenReturn(new CrimeInfo(4, "CAR PROWL", 1, offenses));
        mockMvc.perform(get("/api/info?lat=10&lng=15")).andExpect(json().isEqualTo(TestUtilities.jsonFileToString("src/test/resources/output/CrimeInfo.json")));
    }

    @Test
    public void getDetail() throws Exception {
        CrimeDetail response = new CrimeDetail("all", 8, "MEH CRIME", new ArrayList<CrimeDetail>() {{
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
        when(service.getCrimeDetail(10, 15)).thenReturn(response);
        mockMvc.perform(get("/api/detail?lat=10&lng=15")).andExpect(json().isEqualTo(TestUtilities.jsonFileToString("src/test/resources/output/CrimeDetail.json")));
    }
}
