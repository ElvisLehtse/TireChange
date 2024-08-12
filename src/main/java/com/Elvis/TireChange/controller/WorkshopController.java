package com.Elvis.TireChange.controller;

import com.Elvis.TireChange.component.WorkshopHandler;
import com.Elvis.TireChange.model.*;
import com.Elvis.TireChange.service.ListFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<FullModel> getAvailableTimes(@RequestBody UserGetRequestData userData) {
        try {
            return modelFilter.getFilteredList(userData);
        } catch (JAXBException | RuntimeException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("postManchester")
    public PostRequestReplyModel bookManchester(@RequestBody UserPostRequestForManchester userData) {
        return workshopHandler.postManchesterWorkshopData(userData);
    }

    @PutMapping("putLondon")
    public PostRequestReplyModel bookLondon(@RequestBody UserPutRequestForLondon userData) {
        return workshopHandler.putLondonWorkshopData(userData);
    }
}
