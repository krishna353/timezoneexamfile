package com.org.timezone.controller;

import java.time.DateTimeException;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.org.timezone.model.TimeResponse;
import com.org.timezone.model.WorldTimeApiResponse;

@RestController
public class TimeController {

	private static final String WORLD_TIME_API_URL = "http://worldtimeapi.org/api/timezone/";

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/time")
	public ResponseEntity<Object> getTime(@RequestParam("timezone") String timezone) {
		try {
			ZoneId zoneId = ZoneId.of(timezone);
			if (!zoneId.getId().startsWith("America")) {
				return ResponseEntity.badRequest().body("Not a US timezone");
			}
			WorldTimeApiResponse worldTimeApiResponse = restTemplate.getForObject(WORLD_TIME_API_URL + timezone,
					WorldTimeApiResponse.class);
			TimeResponse finalResponse = new TimeResponse(worldTimeApiResponse.getAbbreviation(),
					worldTimeApiResponse.getTimezone(), worldTimeApiResponse.getDatetime());
			return ResponseEntity.ok(finalResponse);
		} catch (DateTimeException e) {
			return ResponseEntity.badRequest().body("Invalid timezone: " + timezone);
		} catch (RestClientException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching time");
		}
	}
}
