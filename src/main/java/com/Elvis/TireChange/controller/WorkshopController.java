package com.Elvis.TireChange.controller;

import com.Elvis.TireChange.component.WorkshopHandler;
import com.Elvis.TireChange.model.*;
import com.Elvis.TireChange.service.ListFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

/**
 * WorkshopController class handles user GET, POST and PUT requests by sending user data
 * to other classes and returning a response message.
 */
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
    public RequestReplyModel bookManchester(@RequestBody UserPostRequestForManchester userData) {
        try {
            return workshopHandler.postManchesterWorkshopData(userData);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PutMapping("putLondon")
    public RequestReplyModel bookLondon(@RequestBody UserPutRequestForLondon userData) {
        try {
            return workshopHandler.putLondonWorkshopData(userData);
        } catch (JAXBException | NullPointerException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
