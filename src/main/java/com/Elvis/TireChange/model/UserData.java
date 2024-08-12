package com.Elvis.TireChange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserData {

    List<String> workshop;
    List<String> carType;
    int startDateModifier;
    int endDateModifier;
}