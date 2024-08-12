package com.Elvis.TireChange.component;

import com.Elvis.TireChange.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.parser.Entity;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

@Component
public class WorkshopHandler {

    @Autowired
    RestTemplate restTemplate;

    //GET API data as JSON
    public List<ManchesterModel> getManchesterWorkshopData() {
        String url = "http://localhost:9004/api/v2/tire-change-times?from=2006-01-02";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ManchesterModel[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), ManchesterModel[].class);
        if (response.getBody() == null) throw new RuntimeException();
        return Arrays.stream(response.getBody()).toList();
    }

    //GET API data as XML
    public List<LondonModel> getLondonWorkshopData() throws JAXBException {
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

    public PostRequestReplyModel postManchesterWorkshopData(int id) {
        String contactName = "testName";
        String url = STR."http://localhost:9004/api/v2/tire-change-times/\{id}/booking";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = STR."{\"contactInformation\": \"\{contactName}\"}";
        ResponseEntity<ManchesterModel> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(requestBody, headers), ManchesterModel.class);
            return new PostRequestReplyModel(response.getBody().getTime(), true);
        } catch (HttpClientErrorException e) {
            return new PostRequestReplyModel(e.getResponseBodyAs(ManchesterBadRequest.class).getMessage(), false);
        }
    }
}
