package io.pivotal.controller;

import io.pivotal.output_response.CrimeWrapper;
import io.pivotal.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CrimeController {
    @Autowired
    private CrimeService service;

    @RequestMapping(path = "/", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public CrimeWrapper getNumber() {
        return new CrimeWrapper(new CrimeWrapper.CrimeData(service.getNumberOfCrimes()));
    }
}
