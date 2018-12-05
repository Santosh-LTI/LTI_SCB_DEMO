package com.scb.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scb.model.CustomerRequestData;
import com.scb.model.CustomerResponse;
import com.scb.service.CustomerRequestService;
import com.scb.util.SCBCommonMethods;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomerRequestServiceImpl implements CustomerRequestService {

	@Autowired
	private SCBCommonMethods commonMethods;

	@Override
	@Transactional
	public CustomerResponse customerRequestHandleService(CustomerRequestData customerRequestData) {
		
		
		
		// we need to write transform logic here
		
		return null;
	}


}
