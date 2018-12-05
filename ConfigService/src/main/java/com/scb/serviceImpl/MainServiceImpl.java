package com.scb.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.scb.model.ProcessFlow;
import com.scb.model.ProcessFlowSequence;
import com.scb.model.ServiceDetail;
import com.scb.repository.ProcessFlowRepository;
import com.scb.repository.ProcessFlowSequenceRepository;
import com.scb.repository.ServiceDetailRepository;
import com.scb.service.MainService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MainServiceImpl implements MainService{
	@Autowired
	private ProcessFlowSequenceRepository processflowsequencerepository;
	@Autowired
	private ProcessFlowRepository processflowrepository;
	@Autowired
	private ServiceDetailRepository servicedetailrepository;



	@Override
	public boolean addProcessFlowSequence(ProcessFlowSequence processflowsequence) {
		log.info("ProcessFlowSequence received: " + processflowsequence);
		ProcessFlowSequence processFlowSequence = null;
		try {
		processFlowSequence = (ProcessFlowSequence) processflowsequencerepository.findById(processflowsequence.getProcessflowsequencecompositekey()).get();
		}catch (NoSuchElementException ex) {
			log.info("Error in finding Process Flow Sequence : " + ex.getMessage());
			//ex.printStackTrace();
		}
		if (processFlowSequence != null) {
			return false;
		} else {
			log.info("ProcessFlow being saved in db");
			long pid=processflowsequence.getProcessflowsequencecompositekey().getProcessId();
			String s1=processflowrepository.findProcessFlowName(pid);
			processflowsequence.setProcessName(s1);
			long sid=processflowsequence.getProcessflowsequencecompositekey().getServiceId();
			String s2=servicedetailrepository.findServiceDetailName(sid);
			processflowsequence.setServiceName(s2);
			processflowsequencerepository.save(processflowsequence);
			log.info("ProcessFlow saved in db");
			return true;
		}	}

	@Override
	public boolean addProcessFlow(ProcessFlow processflow) {
		log.info("ProcessFlow received: " + processflow);
		ProcessFlow processFlow = null;
		try {
			 processFlow = (ProcessFlow) processflowrepository.findById(processflow.getProcessId()).get();
		}catch (NoSuchElementException ex) {
			log.info("Error in finding Process Flow : " + ex.getMessage());

		}
		
		
		if (processFlow != null) {
			return false;
		} else {
			log.info("ProcessFlow being saved in db");
			
			if((processflow.getStatusName()).equals("Active")) {
				processflow.setStatus(1);
			}
			else {
				processflow.setStatus(0);
			}
			processflowrepository.save(processflow);
			log.info("ProcessFlow saved in db!");
			return true;
		}
	}

	@Override
	public boolean addServiceDetail(ServiceDetail servicedetail) {
	log.info("ServiceDetail received: " + servicedetail);
		
		ServiceDetail serviceDetail = null;
		try {
			serviceDetail = (ServiceDetail) servicedetailrepository.findById(servicedetail.getServiceId()).get();
		} catch (NoSuchElementException ex) {
			log.info("Error in finding service detail : " + ex.getMessage());
		}
		
		log.info("ServiceDetail from db: " + serviceDetail);
		
		if (serviceDetail != null ) {
			return false;
		} else {
			log.info("ServiceDetail being saved in db");
			if((servicedetail.getStatusName()).equals("Active")) {
				servicedetail.setStatus(1);
			}
			else {
				servicedetail.setStatus(0);
			}
			servicedetailrepository.save(servicedetail);
			log.info("ServiceDetail saved in db!");
			return true;
		}

	}

	@Override
	public List<ProcessFlowSequence> getAllProcessFlowSequence() {
		List<ProcessFlowSequence> list = new ArrayList<>();
		processflowsequencerepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public List<ProcessFlow> getAllProcessFlow() {
		List<ProcessFlow> list = new ArrayList<>();
		processflowrepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public List<ServiceDetail> getAllServiceDetail() {
		List<ServiceDetail> list = new ArrayList<>();
		servicedetailrepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public List<ProcessFlowSequence> getProcessFlowSequenceById(long processId) {
		List<ProcessFlowSequence> obj = processflowsequencerepository.getProcessFlowSequenceById(processId);
		return obj;

	}

	@Override
	public ProcessFlow getProcessFlowByName(String processName) {
		ProcessFlow obj = processflowrepository.findProcessFlowByName(processName).get(0);
		return obj;
	}

	@Override
	public ServiceDetail getServiceDetailByName(String serviceName) {
		ServiceDetail obj = servicedetailrepository.findServiceDetailByName(serviceName).get(0);
		return obj;
	}

	@Override
	public ProcessFlow getProcessFlowById(long processId) {
		ProcessFlow obj = processflowrepository.findById(processId).get();
		return obj;
	}

	@Override
	public ServiceDetail getServiceDetailById(long serviceId) {
		ServiceDetail obj = servicedetailrepository.findById(serviceId).get();
		return obj;
	}

	@Override
	public boolean ModifyServiceDetail(ServiceDetail servicedetail) {
		servicedetailrepository.save(servicedetail);
		return true;
	}
	
	@Override
	public boolean ModifyProcessFlow(ProcessFlow processflow) {
		processflowrepository.save(processflow);
		return true;
	}

	@Override
	public boolean ModifyProcessFlowSequence(ProcessFlowSequence processflowsequence) {
		processflowsequencerepository.save(processflowsequence);
		return true;
		
	}

	@Override
	public String getProcessFlowNameById(long processId) {
		String obj = processflowrepository.findProcessFlowName(processId);
		return obj;
	}

	@Override
	public String getServiceDetailNameById(long serviceId) {
		String obj = servicedetailrepository.findServiceDetailName(serviceId);
		return obj;
	}

	@Override
	public List<String> getAllServiceDetailName() {
		List<String> list = new ArrayList<>();
		servicedetailrepository.findlistofServiceDetailName().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public List<String> getAllProcessFlowName() {
		List<String> list = new ArrayList<>();
		processflowrepository.findlistofProcessFlowName().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public ProcessFlowSequence getProcessFlowSequenceByCompositeId(long processId, long serviceId) {
		ProcessFlowSequence obj = processflowsequencerepository.getProcessFlowSequenceByCompositeId(processId, serviceId);
		return obj;
	}
	


}
