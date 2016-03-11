package io.pivotal.controller;

import io.pivotal.response.output.CrimeDetailResponse;
import io.pivotal.response.output.CrimeInfoResponse;
import io.pivotal.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CrimeController {

    @Autowired
    private CrimeService service;

    @RequestMapping(path = "/info", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public CrimeInfoResponse getInfo(@RequestParam(name = "lat", defaultValue = "47.599189") double latitude,
                                     @RequestParam(name = "lng", defaultValue = "-122.333888") double longitude) {
        return new CrimeInfoResponse(service.getCrimeInfo(latitude, longitude));
    }

    @RequestMapping(path = "/detail", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public CrimeDetailResponse getDetail(@RequestParam(name = "lat") double latitude,
                                       @RequestParam(name = "lng") double longitude) {
        return new CrimeDetailResponse(service.getCrimeDetail(latitude, longitude));
    }
}
