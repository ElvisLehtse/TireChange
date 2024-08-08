package com.Elvis.TireChange;

import com.Elvis.TireChange.component.WorkshopHandler;
import com.Elvis.TireChange.model.LondonModel;
import com.Elvis.TireChange.model.ManchesterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBException;
import java.util.List;

@SpringBootApplication
public class TireChangeApplication implements CommandLineRunner {

	@Autowired
	WorkshopHandler workshopHandler;

	public static void main(String[] args) {
		SpringApplication.run(TireChangeApplication.class, args);
	}

	public void run(String... args) {

		System.out.println("\n\n --- London times --- \n\n");

		try {
			List<LondonModel> listOfLondonTimes = workshopHandler.getLondonWorkshopData();
			for(LondonModel model : listOfLondonTimes) {
				System.out.println(STR."Service UUID: \{model.getUuid()}");
				System.out.println(STR."Time: \{model.getTime()}\n");
			}

			System.out.println("\n\n --- Manchester times --- \n\n");

			List<ManchesterModel> listOfManchesterTimes = workshopHandler.getManchesterWorkshopData();
			for (ManchesterModel model : listOfManchesterTimes) {
				System.out.println(STR."Service ID: \{model.getId()}");
				System.out.println(STR."Time: \{model.getTime()}");
				System.out.println(STR."Availability: \{model.isAvailability()}\n");
			}
		} catch (JAXBException | RuntimeException e) {
			System.out.println(e.getMessage());
		}

    }
}
