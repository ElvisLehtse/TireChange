package com.Elvis.TireChange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FullModel {

    String id;
    String workshopName;
    String workshopAddress;
    List<String> carType;
    boolean availability;
    ZonedDateTime time;
}
