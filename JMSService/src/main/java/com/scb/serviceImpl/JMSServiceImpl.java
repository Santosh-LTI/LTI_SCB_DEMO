package com.scb.serviceImpl;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.scb.model.CustomerRequestData;
import com.scb.service.JMSService;


@Service
public class JMSServiceImpl implements JMSService{
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Queue queue;

	@Override
	public void sendMessage(CustomerRequestData customerRequestData) {
		System.out.println("CustomerRequestData before posting::::"+customerRequestData);
		try {
			jmsTemplate.convertAndSend(queue, customerRequestData);
			System.out.println("Successfully posted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
