package com.scb.config;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


@Configuration
public class JMSConfig {
	
	private static final String QUEUE_NAME="jms.queue";
	private static final String BROKER_URL = "tcp://localhost:61616"; 
	private static final String BROKER_USERNAME = "admin"; 
	private static final String BROKER_PASSWORD = "admin";
	
	
	@Bean
	public Queue createQueue(){
		return new ActiveMQQueue(QUEUE_NAME);
	}
	
	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL(BROKER_URL);
		factory.setUserName(BROKER_USERNAME);
		factory.setPassword(BROKER_PASSWORD);
		return factory;
	}
	
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
	    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	}

	
	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
        template.setMessageConverter(jacksonJmsMessageConverter());
        template.setConnectionFactory(connectionFactory());
        return template;
	}
	
	@Bean
	public DefaultJmsListenerContainerFactory  jmsFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setMessageConverter(jacksonJmsMessageConverter());
		return factory;
	}
	 
	
}
