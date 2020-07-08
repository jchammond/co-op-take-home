package com.validity.monolithstarter.rest;

import com.validity.monolithstarter.service.DuplicateFinderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import org.json.simple.JSONObject;

@RestController
@RequestMapping("/api")
public class DuplicateFinderController {

    @Inject
    private DuplicateFinderService duplicateFinderService;

    @GetMapping("/hello")
    public JSONObject findDuplicates() {
        return duplicateFinderService.findDuplicates();
    }
}
