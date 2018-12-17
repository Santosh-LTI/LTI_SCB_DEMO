package com.scb.serviceImpl;

import javax.jms.JMSException;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.scb.model.CustomerRequestData;

@Component
public class JMSReceiverImpl {
	
	@JmsListener(destination = "jms.queue", containerFactory="jmsFactory")
	public void receiveMessage(final CustomerRequestData customerRequestData)throws JMSException {
		System.out.println("Recived Customer data::::"+customerRequestData.customerName);
	}

}
