package com.scb.HttpsService.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class HttpsRequestServiveImpl implements HttpsRequestService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.scb.HttpsService.service.HttpsRequestService#publishHttpsRequest(java.
	 * util.Map, java.lang.String)
	 */
	@Override
	public String publishHttpsRequest(Map<String, String> requestMap, String httpsServiceReqBody) {
		// TODO Auto-generated method stub
		return "https request successfully validated";
	}

}
