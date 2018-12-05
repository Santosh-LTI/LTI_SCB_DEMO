package com.scb.service;

import org.springframework.stereotype.Service;

import com.scb.model.CustomerRequestData;
import com.scb.model.CustomerResponse;

@Service
public interface CustomerRequestService {
	
	public CustomerResponse customerRequestHandleService(CustomerRequestData customerRequestData);

	
}
