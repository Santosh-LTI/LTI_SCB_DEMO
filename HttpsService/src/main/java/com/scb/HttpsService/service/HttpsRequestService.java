package com.scb.HttpsService.service;

import java.util.Map;

public interface HttpsRequestService {
	/**
	 * @param requestMap
	 * @param httpsServiceReqBody
	 * @return
	 */
	public String publishHttpsRequest(Map<String, String> requestMap, String httpsServiceReqBody);

}
