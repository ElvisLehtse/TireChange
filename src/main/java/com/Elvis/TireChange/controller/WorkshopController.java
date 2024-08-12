package com.Elvis.TireChange.controller;

import com.Elvis.TireChange.component.WorkshopHandler;
import com.Elvis.TireChange.model.PostRequestReplyModel;
import com.Elvis.TireChange.service.ListFilter;
import com.Elvis.TireChange.model.FullModel;
import com.Elvis.TireChange.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
public class WorkshopController {

    @Autowired
    ListFilter modelFilter;
    @Autowired
    WorkshopHandler workshopHandler;

    @GetMapping("getTimes")
    public List<FullModel> getAvailableTimes(@RequestBody UserData userData) {
        try {
            return modelFilter.getFilteredList(userData);
        } catch (JAXBException | RuntimeException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("postTime")
    public PostRequestReplyModel bookNewTime(@RequestBody int id) {
        return workshopHandler.postManchesterWorkshopData(id);
    }
}
