package com.Elvis.TireChange.controller;

import com.Elvis.TireChange.service.ListFilter;
import com.Elvis.TireChange.model.FullModel;
import com.Elvis.TireChange.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
public class WorkshopController {

    @Autowired
    ListFilter modelFilter;

    @GetMapping("getTimes")
    public List<FullModel> getAvailableTimes(@RequestBody UserData userData) {
        try {
            return modelFilter.getFilteredList(userData);
        } catch (JAXBException | RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
