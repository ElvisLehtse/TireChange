package com.Elvis.TireChange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FilterModel {

    List<String> workshop;
    List<String> carType;
    Date startDate;
    Date endDate;
}
