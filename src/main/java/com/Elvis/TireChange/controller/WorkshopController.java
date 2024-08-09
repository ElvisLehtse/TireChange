package com.Elvis.TireChange.controller;

import com.Elvis.TireChange.ListFilter;
import com.Elvis.TireChange.model.FilterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkshopController {

    @Autowired
    ListFilter modelFilter;

    @GetMapping("getTimes")
    public void getAvailableTimes(@RequestBody FilterModel userData) {
        modelFilter.getFilteredList(userData);
    }
}
