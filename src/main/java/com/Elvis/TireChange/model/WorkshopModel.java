package com.Elvis.TireChange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkshopModel {

    String workshopName;
    String workshopAddress;
    List<String> vehicleList;
}
