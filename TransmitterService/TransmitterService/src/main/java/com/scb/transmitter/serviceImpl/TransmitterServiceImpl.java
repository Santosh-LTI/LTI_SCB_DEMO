package com.scb.transmitter.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.scb.transmitter.model.RequestData;
import com.scb.transmitter.model.ResponseMessage;
import com.scb.transmitter.model.TransmitterModel;
import com.scb.transmitter.repository.TransmitterRepository;
//import com.scb.model.CustomerRequestData;
//import com.scb.service.JMSService;
import com.scb.transmitter.service.TransmitterService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TransmitterServiceImpl implements TransmitterService {
//	@Autowired
//	private JmsTemplate jmsTemplate; 
//	
//	@Autowired
//	private Queue queue;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	@Autowired
	private TransmitterRepository transmitterRepository;
	
	@Autowired
	private ExternalServiceInvoker externalServiceInvoker;
	
	@Autowired
	private ExternalServiceConfig externalServiceConfig;
	
	@Value("${msbif.lti.downstreamServiceURL}")
	String httpsURL;
	
//	@Override
//	public void sendMessage(CustomerRequestData customerRequestData) {
//		log.info("CustomerRequestData before posting::::"+customerRequestData);
//		
//		try {
//			jmsTemplate.convertAndSend(queue, customerRequestData);
//			log.info("Successfully posted");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public ResponseMessage transmitMessage(RequestData requestData) {
		log.info("Request Data received : " + requestData);
		
		//get transmitter method
		List<TransmitterModel> list = getTransmitterByType(getMetadataModel(requestData));
		ResponseMessage responseMessage = new ResponseMessage();
		log.info("List of transmitter methods: " +  list.size());
		
		if(list == null || list.isEmpty()) {
			responseMessage.setResponseCode(400);
			responseMessage.setResponseMessage("No transmitter method avaiable for Transaction Type :" 
					+ requestData.getTransactionType() + " and Transaction Sub Type : " 
					+ requestData.getTransactionSubType());
		}
		
		TransmitterModel transmitterModel = list.get(0);
		log.info("Transmitter method : " + transmitterModel.getTransmitterMethod());
		
		if (transmitterModel.getTransmitterMethod().equalsIgnoreCase("HTTP")) {
			//do somthing
			log.info("DownstreamServiceURL : " + externalServiceConfig.getDownstreamServiceURL());
			
			ResponseEntity<ResponseMessage> re = externalServiceInvoker.serviceApiCall(requestData, transmitterModel.getUrl());//externalServiceConfig.getDownstreamServiceURL());
			responseMessage = re.getBody();
			log.info("ResponseMessage from downstream service : " + responseMessage);
		} else if (transmitterModel.getTransmitterMethod().equalsIgnoreCase("JMS")) {
			//do something
		} else {
			responseMessage.setResponseCode(400);
			responseMessage.setResponseMessage("Invalid Transmitter method : " + transmitterModel.getTransmitterMethod());
		}
		
		return responseMessage;
	}
	
	@Override
	public TransmitterModel addTransmitterData(TransmitterModel transmitterData) {
		transmitterData.setTransmitterId(getTransmitterId());
		transmitterData.setCreatedOn(getCurrentDateTime());
		transmitterData.setUpdatedOn(getCurrentDateTime());
		
		TransmitterModel transmitterModel = null;
		try {
			transmitterModel = transmitterRepository.findByTransmitterId(transmitterData.getTransmitterId());
		} catch (NoSuchElementException ex) {
			log.info("Error in finding transmitterModel" + ex.getMessage());
		}
		
		
		if (transmitterModel != null) {
			//metadataModel = new MetadataModel();
			log.info("Metadata already exists in db");
		} else {
			log.info("Metadata deatils being saved in db");

			transmitterModel = transmitterRepository.save(transmitterData);
			log.info("Metadata saved in db");
		}
		
		return transmitterModel;
	}

	@Override
	public ResponseMessage modifyTransmitterData(TransmitterModel transmitterData) {
		log.info("Transmitter Data received: " + transmitterData);
		transmitterData.setUpdatedOn(getCurrentDateTime());
		ResponseMessage rm = new ResponseMessage();
		int updateCount = 0;
		
		try {
			updateCount = transmitterRepository.updateById(transmitterData.getTransactionType(), 
					transmitterData.getTransactionSubType(), transmitterData.getTransmitterMethod(),
					transmitterData.getUpdatedOn(), transmitterData.getTransmitterId());
			if (updateCount > 0) {
				rm.setResponseCode(200);
				rm.setResponseMessage("Record updated successfully.");
				log.info("Transmitter updated in db");
			} else {
				rm.setResponseCode(700);
				rm.setResponseMessage("Update failed. Record id did not match");
			}
			
		} catch (NoSuchElementException ex) {
			log.info("Error in finding Transmitter" + ex.getMessage());
			rm.setResponseCode(900);
			rm.setResponseMessage("Update failed. No Such Element Exception: " + ex.getMessage());
		}

		return rm;
	}

	@Override
	public List<TransmitterModel> getTransmitterByType(TransmitterModel transactionData) {
		log.info("Received transactionData : " + transactionData);
		List<TransmitterModel> obj = transmitterRepository.findByType(transactionData.getTransactionType(), transactionData.getTransactionSubType());
		log.info("Returned from DB  : " + obj);
		return obj;
	}

	@Override
	public TransmitterModel getTransmitterById(long transmitterId) {
		TransmitterModel obj = transmitterRepository.findByTransmitterId(transmitterId);
		log.info("Returned from DB  : " + obj);
		return obj;
	}
	
	@Override
	public List<TransmitterModel> getTransmitterByMethod(TransmitterModel transactionData) {
		List<TransmitterModel> obj = transmitterRepository.findByMethod(transactionData.getTransactionType(), 
				transactionData.getTransactionSubType(), transactionData.getTransmitterMethod());
		return obj;
	}

	@Override
	public List<TransmitterModel> getAllTransmitterData() {
		List<TransmitterModel> list = new ArrayList<TransmitterModel>();
		transmitterRepository.findAll().forEach(e -> list.add(e));
		log.info("Returned from DB  : " + list.size());
		return list;
	}

	private TransmitterModel getMetadataModel(RequestData requestData) {
		return new TransmitterModel().builder().transactionType(requestData.getTransactionType())
				.transactionSubType(requestData.getTransactionSubType()).transmitterMethod(requestData.getPayloadFormat())
				.build();
	}
	
	public long getTransmitterId() {
		Random random = new Random(System.nanoTime() % 100000);
		long uniqueMetadataId = random.nextInt(1000000000);
		return uniqueMetadataId;
	}
	
	public String getCurrentDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.toString();
	}

	@Override
	public String getTransmitByType(Map<String, String> requestMap, String transmittertype) {
		String response = null;
		if(transmittertype!=null&&transmittertype.equals("https")) {
			HttpHeaders requestHeaders = new HttpHeaders();
	        //set up HTTP Basic Authentication Header
	        requestHeaders.add("contentType", "Application/json");
	        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

	        //request entity is created with request headers
	        HttpEntity requestEntity = new HttpEntity<>("test", requestHeaders);
	       try {
	    	   ResponseEntity<String> st = restTemplate.exchange(
	        		httpsURL,
	                HttpMethod.POST,
	                requestEntity,
	                String.class
	        );
	        response=  st.getBody();
	       }catch(Exception ex) {
	    	   System.out.println("rest call for https service"+ex);
	       }
		}else if(transmittertype.equals("JMS")) {
			HttpHeaders requestHeaders = new HttpHeaders();
	        //set up HTTP Basic Authentication Header
	        requestHeaders.add("contentType", "Application/json");
	        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

	        //request entity is created with request headers
	        HttpEntity requestEntity = new HttpEntity<>("test", requestHeaders);
	       try {
	    	   ResponseEntity<String> st = restTemplate.exchange(
	        		httpsURL,
	                HttpMethod.POST,
	                requestEntity,
	                String.class
	        );
	        response=  st.getBody();
	       }catch(Exception ex) {
	    	   System.out.println("rest call for JMS service"+ex);
	       }
		}
		return response;
	}

}