package com.test.timezone.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetTimeWithValidTimezone() {
		// Arrange
		String timezone = "America/New_York";

		// Act
		ResponseEntity<Object> response = restTemplate.getForEntity("/time?timezone=" + timezone, Object.class);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		LinkedHashMap<String, String> timeResponse = (LinkedHashMap<String, String>) response.getBody();
		assertEquals("EDT", timeResponse.get("abbreviation"));
		assertEquals("America/New_York", timeResponse.get("timezone"));
	}

}
