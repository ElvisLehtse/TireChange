package com.Elvis.TireChange.service;

import com.Elvis.TireChange.component.WorkshopHandler;
import com.Elvis.TireChange.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class ListFilter {

    @Autowired
    WorkshopHandler workshopHandler;
    @Autowired
    Reader reader;

    private Date getDate (int DateModifier) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, DateModifier);
        return calendar.getTime();
    }

    private List<FullModel> getFullList() throws JAXBException, RuntimeException, IOException {
        List<WorkshopModel> workshopList = reader.readWorkshopConfiguration();
        List<FullModel> fullModel = new ArrayList<>();

        List<LondonModel> listOfLondonTimes = workshopHandler.getLondonWorkshopData();
        List<ManchesterModel> listOfManchesterTimes = workshopHandler.getManchesterWorkshopData();

        for(LondonModel model : listOfLondonTimes) {
            fullModel.add(new FullModel(model.getUuid(), workshopList.getFirst().getWorkshopName(), workshopList.getFirst().getWorkshopAddress(),
                    workshopList.getFirst().getVehicleList(), true, ZonedDateTime.parse(model.getTime())));
        }
        for (ManchesterModel model : listOfManchesterTimes) {
            fullModel.add(new FullModel(String.valueOf(model.getId()), workshopList.getLast().getWorkshopName(), workshopList.getLast().getWorkshopAddress(),
                    workshopList.getLast().getVehicleList(), model.isAvailable(), ZonedDateTime.parse(model.getTime())));
        }

        return fullModel;
    }

    public List<FullModel> getFilteredList (UserGetRequestData userData) throws JAXBException, RuntimeException, IOException {
        Date startDate = getDate(userData.getStartDateModifier());
        Date endDate = getDate(userData.getEndDateModifier() + 1);

        List<FullModel> fullList = getFullList();
        return fullList.stream()
                .filter(FullModel::isAvailability)
                .filter(e -> userData.getWorkshop().contains(e.getWorkshopName()))
                .filter(e -> e.getCarType().stream().anyMatch(userData.getCarType()::contains))
                .filter(e -> startDate.toInstant().isBefore(e.getTime().toInstant()))
                .filter(e -> endDate.toInstant().isAfter(e.getTime().toInstant()))
                .toList();
    }
}
