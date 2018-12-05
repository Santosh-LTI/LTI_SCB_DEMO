package com.scb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.scb.model.ProcessFlow;
import com.scb.model.ProcessFlowSequence;
import com.scb.model.ResponseMessage;
import com.scb.model.ServiceDetail;
import com.scb.service.MainService;

import lombok.extern.log4j.Log4j2;


@Controller
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConfigServiceController {
	@Autowired
	private MainService mainservice;


	@PostMapping("/AddProcessFlowSequence")
	public ResponseEntity<Void> addProcessFlowSequence(@RequestBody ProcessFlowSequence processflowsequence, UriComponentsBuilder builder) {
				System.out.println("done");
                boolean flag = mainservice.addProcessFlowSequence(processflowsequence);
                if (flag == false) {
        	   return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/ProcessFlowSequence/{id}").buildAndExpand(processflowsequence).toUri());
                return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	@PostMapping("/AddServiceDetail")
	public ResponseEntity<Void> addServiceDetail(@RequestBody ServiceDetail servicedetail, UriComponentsBuilder builder) {
                boolean flag = mainservice.addServiceDetail(servicedetail);
                if (flag == false) {
        	   return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/ServiceDetail/{id}").buildAndExpand(servicedetail).toUri());
                return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	@PostMapping("/AddProcessFlow")
	public ResponseEntity<Void> addProcessFlow(@RequestBody ProcessFlow processflow, UriComponentsBuilder builder) {
                boolean flag = mainservice.addProcessFlow(processflow);
                if (flag == false) {
        	   return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/ProcessFlow/{id}").buildAndExpand(processflow).toUri());
                return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/ProcessFlowSequences")
	public ResponseEntity<List<ProcessFlowSequence>> getAllProcessFlowSequence() {
		log.info(" Get All Process Flow Sequence received: ");
		List<ProcessFlowSequence> list = mainservice.getAllProcessFlowSequence();
		log.info("Process Flow Sequence received: " + list);
		return new ResponseEntity<List<ProcessFlowSequence>>(list, HttpStatus.OK);
	}

	
	
	@GetMapping("/ProcessFlows")
	public ResponseEntity<List<ProcessFlow>> getAllProcessFlow() {
		log.info(" Get All Process Flow received: ");
		List<ProcessFlow> list = mainservice.getAllProcessFlow();
		log.info("Process Flow received: " + list);
		return new ResponseEntity<List<ProcessFlow>>(list, HttpStatus.OK);
	}

	
	@GetMapping("/ServiceDetails")
	public ResponseEntity<List<ServiceDetail>> getAllServiceDetail() {
		log.info(" Get All ServiceDetail received: ");
		List<ServiceDetail> list = mainservice.getAllServiceDetail();
		log.info("ServiceDetail received: " + list);
		return new ResponseEntity<List<ServiceDetail>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/ProcessFlowSequence/{processName}")
	public ResponseEntity<ResponseMessage> getProcessFlowSequenceByName(@PathVariable("processName") String processName) {
		log.info(" Get Process Flow Sequence received: "+processName);
		
		ProcessFlow process = mainservice.getProcessFlowByName(processName);
		
		List<ProcessFlowSequence> processflowsequence = mainservice.getProcessFlowSequenceById(process.getProcessId());
		ResponseMessage responseMessage = new ResponseMessage();
		if (processflowsequence != null || processflowsequence.isEmpty()) { 
			responseMessage.setResponseCode(400);
			responseMessage.setResponseMessage("No services found");
		} else {
			responseMessage.setResponseCode(200);
			responseMessage.setResponseMessage("List of services found : " + processflowsequence.size());
			responseMessage.setList(processflowsequence);			
		}
	
		log.info("Process Flow Sequences for " + processName + " received: " + processflowsequence);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	@GetMapping("/ProcessFlowSequence/{processId}")
	public ResponseEntity<List<ProcessFlowSequence>> getProcessFlowSequenceById(@PathVariable("processId") long processId) {
		log.info(" Get Process Flow Sequence received: "+processId);
		List<ProcessFlowSequence> processflowsequence = mainservice.getProcessFlowSequenceById(processId);
		log.info("Process Flow Sequence "+processId+" received: " + processflowsequence);
		return new ResponseEntity<List<ProcessFlowSequence>>(processflowsequence, HttpStatus.OK);
	}

	@GetMapping("/ProcessFlow/{processname}")
	public ResponseEntity<ProcessFlow> getProcessFlowByName(@PathVariable("processname") String processname) {
		log.info(" Get Process Flow received: "+processname);
		ProcessFlow processflow = mainservice.getProcessFlowByName(processname);
		log.info("Process Flow "+processname+" received: " + processflow);
		return new ResponseEntity<ProcessFlow>(processflow, HttpStatus.OK);
	}
	
	@GetMapping("/ServiceDetail/{servicename}")
	public ResponseEntity<ServiceDetail> getServiceDetailByName(@PathVariable("servicename") String servicename) {
		log.info(" Get Service Detail received: "+servicename);
		ServiceDetail servicedetail = mainservice.getServiceDetailByName(servicename);
		log.info("Service Detail "+servicename+" received: " + servicedetail);
		return new ResponseEntity<ServiceDetail>(servicedetail, HttpStatus.OK);
	}
	
	@GetMapping("ServiceDetails/id/{serviceId}")
	public ResponseEntity<ServiceDetail> getServiceDetailById(@PathVariable("serviceId") Integer serviceId) {
		ServiceDetail servicedetail = mainservice.getServiceDetailById(serviceId);
		return new ResponseEntity<ServiceDetail>(servicedetail, HttpStatus.OK);
	}


	@GetMapping("/ProcessFlows/id/{processId}")
	public ResponseEntity<ProcessFlow> getProcessFlowById(@PathVariable("processId") Integer processId) {
		ProcessFlow processflow = mainservice.getProcessFlowById(processId);
		return new ResponseEntity<ProcessFlow>(processflow, HttpStatus.OK);
	}
	


	@GetMapping("/ProcessFlowSequence/{processId}/{serviceId}")
	public ResponseEntity<ProcessFlowSequence> getProcessFlowSequenceByCompositeId(@PathVariable("processId") long processId,@PathVariable("serviceId") long serviceId) {
		log.info(" Get Process Flow Sequence received: "+processId);
		ProcessFlowSequence processflowsequence = mainservice.getProcessFlowSequenceByCompositeId(processId, serviceId);
		log.info("Process Flow Sequence "+processId+" received: " + processflowsequence);
		return new ResponseEntity<ProcessFlowSequence>(processflowsequence, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	@PutMapping("ModifyServiceDetail/{id}")
	public ResponseEntity<ServiceDetail> updateServiceDetail(@RequestBody ServiceDetail servicedetail) {
		mainservice.ModifyServiceDetail(servicedetail);
		return new ResponseEntity<ServiceDetail>(servicedetail, HttpStatus.OK);
	}
	
	
	@PutMapping("ModifyProcessFlow/{id}")
	public ResponseEntity<ProcessFlow> updateProcessFlow(@RequestBody ProcessFlow processflow) {
		mainservice.ModifyProcessFlow(processflow);
		return new ResponseEntity<ProcessFlow>(processflow, HttpStatus.OK);
	}
	
	@PutMapping("ModifyProcessFlowSequence/{processId}/{serviceId}")
	public ResponseEntity<ProcessFlowSequence> updateProcessFlowSequence(@RequestBody ProcessFlowSequence processflowsequence) {
		mainservice.ModifyProcessFlowSequence(processflowsequence);
		return new ResponseEntity<ProcessFlowSequence>(processflowsequence, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	@GetMapping("/ProcessFlowsByName/{processId}")
	public ResponseEntity<String> getProcessFlowNameById(@PathVariable("processId") Integer processId) {
		String processFlowName = mainservice.getProcessFlowNameById(processId);
		return new ResponseEntity<String>(processFlowName, HttpStatus.OK);
	}
	

	@GetMapping("ServiceDetailsByName/{serviceId}")
	public ResponseEntity<String> getServiceNameById(@PathVariable("serviceId") Integer serviceId) {
		String ServieName = mainservice.getServiceDetailNameById(serviceId);
		return new ResponseEntity<String>(ServieName, HttpStatus.OK);
	}
	@GetMapping("/ServiceDetailNames")
	public ResponseEntity<List<String>> getAllServiceDetailNames() {
		List<String> list = mainservice.getAllServiceDetailName();
		return new ResponseEntity<List<String>>(list, HttpStatus.OK);
	}
	@GetMapping("/ProcessFlowsNames")
	public ResponseEntity<List<String>> getAllProcessFlowNames() {
		List<String> list = mainservice.getAllProcessFlowName();
		return new ResponseEntity<List<String>>(list, HttpStatus.OK);
	}
}
