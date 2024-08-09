package com.Elvis.TireChange;

import com.Elvis.TireChange.component.WorkshopHandler;
import com.Elvis.TireChange.model.FilterModel;
import com.Elvis.TireChange.model.FullModel;
import com.Elvis.TireChange.model.LondonModel;
import com.Elvis.TireChange.model.ManchesterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListFilter {

    @Autowired
    WorkshopHandler workshopHandler;

    private List<FullModel> getFullList() {
        List<FullModel> fullModel = new ArrayList<>();
        try {
            List<LondonModel> listOfLondonTimes = workshopHandler.getLondonWorkshopData();
            List<ManchesterModel> listOfManchesterTimes = workshopHandler.getManchesterWorkshopData();

            for(LondonModel model : listOfLondonTimes) {
                fullModel.add(new FullModel(model.getUuid(), "London", "14 Bury New Rd, Manchester", List.of("Car", "Truck"), true, ZonedDateTime.parse(model.getTime())));
            }
            for (ManchesterModel model : listOfManchesterTimes) {
                fullModel.add(new FullModel(String.valueOf(model.getId()), "Manchester", "1A Gunton Rd, London", List.of("Car"), model.isAvailable(), ZonedDateTime.parse(model.getTime())));
            }

        } catch (JAXBException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return fullModel;
    }

    public List<FullModel> getFilteredList (FilterModel userData) {
        List<FullModel> fullList = getFullList();
        return fullList.stream()
                .filter(e -> userData.getWorkshop().contains(e.getWorkshopName()))
                .filter(e -> e.getCarType().stream().anyMatch(userData.getCarType()::contains))
                .filter(e -> ZonedDateTime.ofInstant(userData.getStartDate().toInstant(), e.getTime().getZone()).isBefore(e.getTime()))
                .filter(e -> ZonedDateTime.ofInstant(userData.getEndDate().toInstant(), e.getTime().getZone()).isAfter(e.getTime()))
                .toList();
    }
}
