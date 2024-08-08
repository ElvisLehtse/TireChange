package com.Elvis.TireChange.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class WorkshopController {

    @GetMapping("getTimes")
    public void getAvailableTimes(@RequestParam (value = "workshop") String workshop,
                                  @RequestParam (value = "carType") List<String> carType,
                                  @RequestParam (value = "startDate") Date startDate,
                                  @RequestParam (value = "endDate") Date endDate) {


    }
}
