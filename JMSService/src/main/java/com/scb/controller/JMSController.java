package com.scb.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scb.model.CustomerRequestData;
import com.scb.service.JMSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class JMSController {
	
	
	@Autowired
	private JMSService jmsService;

	@RequestMapping(value = "/publishMessage", produces = { "application/json", "application/xml" })
	public ResponseEntity<Void> publishMessage(@RequestBody CustomerRequestData customerRequestData){
		jmsService.sendMessage(customerRequestData);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
