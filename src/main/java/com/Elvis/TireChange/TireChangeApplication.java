package com.Elvis.TireChange;

import com.Elvis.TireChange.model.FilterModel;
import com.Elvis.TireChange.model.FullModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TireChangeApplication implements CommandLineRunner {

	@Autowired
	ListFilter listFilter;

	public static void main(String[] args) {
		SpringApplication.run(TireChangeApplication.class, args);
	}

	public void run(String... args) {
		List<String> workshop = List.of("London", "Manchester");
		List<String> carType = List.of("Car", "Truck");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); // Set today's date

		calendar.add(Calendar.DATE, 5); // +5 days
		Date startDate = calendar.getTime();

		calendar.add(Calendar.DATE, 15); // +15 days, total of +20
		Date endDate = calendar.getTime();

		FilterModel filterModel = new FilterModel(workshop, carType, startDate, endDate);
		List<FullModel> filteredList = listFilter.getFilteredList(filterModel);
		System.out.println(filteredList.size());

		for (FullModel model : filteredList) {
			System.out.println(model.getId());
			System.out.println(model.getWorkshopName());
			System.out.println(model.getWorkshopAddress());
			System.out.println(model.isAvailability());
			System.out.println(model.getTime());
			System.out.println(model.getCarType());
			System.out.println();
		}
    }
}
