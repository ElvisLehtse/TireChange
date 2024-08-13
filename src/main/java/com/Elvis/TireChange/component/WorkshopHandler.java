package com.Elvis.TireChange.component;

import com.Elvis.TireChange.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class handles communication between workshop APIs by sending GET, POST and PUT requests and returns
 * a response message to WorkshopController class.
 */

@Component
public class WorkshopHandler {

    @Autowired
    RestTemplate restTemplate;

    /**
     * Sends a GET request to Manchester workshop API.
     * Returns the total list of all the times.
     */
    //GET API data as JSON
    public List<ManchesterModel> getManchesterWorkshopData() throws RuntimeException {
        String url = "http://localhost:9004/api/v2/tire-change-times?from=2006-01-02";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ManchesterModel[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), ManchesterModel[].class);
        if (response.getBody() == null) throw new RuntimeException();
        return Arrays.stream(response.getBody()).toList();
    }

    /**
     * Sends a GET request to London workshop API.
     * Returns the total list of all the times.
     */
    //GET API data as XML
    public List<LondonModel> getLondonWorkshopData() throws JAXBException, RuntimeException {
        String url = "http://localhost:9003/api/v1/tire-change-times/available?from=2006-01-02&until=2030-01-02";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        String xmlString = response.getBody();
        if (xmlString == null) throw new RuntimeException();
        JAXBContext context = JAXBContext.newInstance(LondonTireChangeTimesResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xmlString);
        return ((LondonTireChangeTimesResponse) unmarshaller.unmarshal(reader)).getLondonModel();

    }

    /**
     * Sends a POST request to Manchester workshop API.
     * If there is an available time to book, returns a successful message.
     * If an error occurs or the time is already booked, returns an unsuccessful message.
     */
    //POST API data as JSON
    public RequestReplyModel postManchesterWorkshopData(UserPostRequestForManchester userData) {
        String url = STR."http://localhost:9004/api/v2/tire-change-times/\{userData.getId()}/booking";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = STR."{\"contactInformation\": \"\{userData.getContactInformation()}\"}";
        try {
            ResponseEntity<Optional<ManchesterModel>> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(requestBody, headers), new ParameterizedTypeReference<>() {});
            ManchesterModel manchesterModel = response.getBody().orElse(new ManchesterModel(0, "", false));
            return new RequestReplyModel(manchesterModel.getTime(), true);
        } catch (HttpClientErrorException e) {
            return new RequestReplyModel(Objects.requireNonNull(e.getResponseBodyAs(ManchesterBadRequest.class)).getMessage(), false);
        }
    }

    /**
     * Sends a PUT request to London workshop API.
     * If there is an available time to book, returns a successful message.
     * If an error occurs or the time is already booked, returns an unsuccessful message.
     */
    //PUT API data as XML
    public RequestReplyModel putLondonWorkshopData(UserPutRequestForLondon userData) throws JAXBException {
        String url = STR."http://localhost:9003/api/v1/tire-change-times/\{userData.getUuid()}/booking";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        String requestBody = STR."<?xml version=\"1.0\" encoding=\"UTF-8\"?> <london.tireChangeBookingRequest> <contactInformation>\{userData.getContactInformation()}</contactInformation> </london.tireChangeBookingRequest>";
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(requestBody, headers), String.class);
            String xmlString = response.getBody();
            if (xmlString == null) throw new RuntimeException();
            JAXBContext context = JAXBContext.newInstance(LondonTireChangeBookingResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xmlString);
            return new RequestReplyModel(((LondonTireChangeBookingResponse) unmarshaller.unmarshal(reader)).getTime(), true);
        } catch (HttpClientErrorException e) {
            String xmlString = e.getResponseBodyAs(String.class);
            if (xmlString == null) throw new RuntimeException();
            JAXBContext context = JAXBContext.newInstance(LondonBadRequest.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xmlString);
            return new RequestReplyModel(Objects.requireNonNull((LondonBadRequest) unmarshaller.unmarshal(reader)).getError(), false);
        }
    }
}
