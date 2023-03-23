package com.test.timezone.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.test.timezone.model.TimeResponse;
import com.test.timezone.model.WorldTimeApiResponse;

@ExtendWith(MockitoExtension.class)
public class TimeControllerTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private TimeController timeController;

	@Test
	public void testGetTimeWithValidTimezone() throws JsonMappingException, JsonProcessingException {
		// Arrange
		String timezone = "America/New_York";
		WorldTimeApiResponse apiResponse = new WorldTimeApiResponse();
		apiResponse.setAbbreviation("EDT");
		apiResponse.setTimezone("America/New_York");
		apiResponse.setDatetime(LocalDateTime.now().toString());

		ResponseEntity<Object> response = timeController.getTime(timezone);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		TimeResponse timeResponse = (TimeResponse) response.getBody();
		assertEquals("EDT", timeResponse.getAbbreviation());
		assertEquals("America/New_York", timeResponse.getTimezone());
	}

	@Test
	public void testGetTimeWithInvalidTimezone() throws JsonMappingException, JsonProcessingException {
		// Arrange
		String timezone = "America/Kansas";

		// Act
		ResponseEntity<Object> response = timeController.getTime(timezone);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Invalid timezone", response.getBody());
	}

	@Test
	public void testGetTimeWithInvalidUSTimezone() throws JsonMappingException, JsonProcessingException {
		// Arrange
		String timezone = "London/Timezone";

		// Act
		ResponseEntity<Object> response = timeController.getTime(timezone);

		// Assert
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Not a US Timezone timezone", response.getBody());
	}

}