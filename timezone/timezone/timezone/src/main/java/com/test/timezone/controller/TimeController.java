package com.test.timezone.controller;

import java.time.DateTimeException;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.timezone.model.TimeResponse;
import com.test.timezone.model.WorldTimeApiResponse;

@RestController
public class TimeController {

	private static final String WORLD_TIME_API_URL = "http://worldtimeapi.org/api/timezone/";

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/time")
	public ResponseEntity<Object> getTime(@RequestParam("timezone") String timezone) {

		ZoneId zoneId;
		if (!timezone.startsWith("America")) {
			return ResponseEntity.badRequest().body("Not a US Timezone timezone");
		}
		try {
			zoneId = ZoneId.of(timezone);
		} catch (Exception e) {

			return ResponseEntity.badRequest().body("Invalid timezone");
		}

		// Make API call to worldtimeapi.org
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = WORLD_TIME_API_URL + timezone;
		WorldTimeApiResponse response = restTemplate.getForObject(apiUrl, WorldTimeApiResponse.class);
		TimeResponse finalResponse = new TimeResponse(response.getAbbreviation(), response.getTimezone(),
				response.getDatetime());
		return ResponseEntity.ok(finalResponse);
	}
}
