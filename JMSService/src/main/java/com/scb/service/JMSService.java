package com.scb.service;

import com.scb.model.CustomerRequestData;

public interface JMSService {

	void sendMessage(CustomerRequestData customerRequestData);

}
