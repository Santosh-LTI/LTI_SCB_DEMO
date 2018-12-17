package com.scb.transmitter.service;

import java.util.List;
import java.util.Map;

import com.scb.transmitter.model.RequestData;
import com.scb.transmitter.model.ResponseMessage;
import com.scb.transmitter.model.TransmitterModel;

public interface TransmitterService {
	public ResponseMessage transmitMessage(RequestData requestData);
	
	public TransmitterModel addTransmitterData(TransmitterModel transmitterData);

	public ResponseMessage modifyTransmitterData(TransmitterModel transmitterData);
	
	public List<TransmitterModel> getTransmitterByType(TransmitterModel transmitterData);
	
	public List<TransmitterModel> getTransmitterByMethod(TransmitterModel transmitterData);
	
	List<TransmitterModel> getAllTransmitterData();
	
	public TransmitterModel getTransmitterById(long transmitterId);

	public String getTransmitByType(Map<String, String> requestMap, String transmittertype);

	
}


/*
final ClientHttpRequestFactory clientHttpRequestFactory =
new MyCustomClientHttpRequestFactory(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER, serverInfo);
restTemplate.setRequestFactory(clientHttpRequestFactory);

*/