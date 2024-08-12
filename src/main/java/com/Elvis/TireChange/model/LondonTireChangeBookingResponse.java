package com.Elvis.TireChange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "tireChangeBookingResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class LondonTireChangeBookingResponse {

    @XmlElement(name = "uuid")
    String uuid;
    @XmlElement(name = "time")
    String time;
}
