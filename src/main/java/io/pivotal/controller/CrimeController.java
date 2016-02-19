package io.pivotal.controller;

import io.pivotal.service.CrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api")
@Controller
public class CrimeController {
    @Autowired
    private CrimeService service;

    @RequestMapping("/")
    public @ResponseBody String getNumber() {
        return Integer.toString(service.getNumberOfCrimes());
    }
}
