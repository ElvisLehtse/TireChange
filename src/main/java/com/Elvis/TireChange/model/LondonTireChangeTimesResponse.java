package com.Elvis.TireChange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "tireChangeTimesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class LondonTireChangeTimesResponse {

    @XmlElement(name = "availableTime")
    private List<LondonModel> londonModel;
}
