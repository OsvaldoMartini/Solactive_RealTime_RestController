package com.solactive.controller;

import java.time.Duration;
import java.time.Instant;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solactive.ExecutorMultiRequests;
import com.solactive.pojos.TicksDTO;

@RestController
@RequestMapping("/")
public class TickController {

	@Autowired
	ExecutorMultiRequests executorMultiRequests;

	/*
	 * To Handle all requests for "/ticks"
	 */
	@PostMapping("/ticks")
	public ResponseEntity<?> postTicks(@Valid @RequestBody TicksDTO tick) {
		long rule60SecondsAgo = Instant.now().minus(Duration.ofSeconds(60)).toEpochMilli();
		long ruleEpochNow = Instant.now().toEpochMilli();

		if (tick.getTimeStamp() < rule60SecondsAgo) {
	        return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
		} else if (tick.getTimeStamp() > ruleEpochNow) {
	        return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
		} else {
			ExecutorMultiRequests.mainMappedTick.put(tick.getInstrument() + "-" + tick.getTimeStamp(), tick);
	        return new ResponseEntity<Object>(null, HttpStatus.CREATED);
		}
    }

}
