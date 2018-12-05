package com.scb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scb.model.ProcessFlow;
import com.scb.model.ProcessFlowSequence;
import com.scb.model.ServiceDetail;
@Service
public interface MainService {

	boolean addProcessFlowSequence(ProcessFlowSequence processflowsequence);
	boolean addProcessFlow(ProcessFlow processflow);
	boolean addServiceDetail(ServiceDetail servicedetail);
	
	List<ProcessFlowSequence> getAllProcessFlowSequence();
	List<ProcessFlow> getAllProcessFlow();
	List<ServiceDetail> getAllServiceDetail();
	
	
	List<ProcessFlowSequence> getProcessFlowSequenceById(long processId);
	ProcessFlowSequence getProcessFlowSequenceByCompositeId(long processId,long serviceId);
	
	ProcessFlow getProcessFlowByName(String processName);
	ServiceDetail getServiceDetailByName(String serviceName);
	
	ProcessFlow getProcessFlowById(long processId);
	ServiceDetail getServiceDetailById(long serviceId);
	
	boolean ModifyServiceDetail(ServiceDetail servicedetail);
	boolean ModifyProcessFlow(ProcessFlow processflow);
	boolean ModifyProcessFlowSequence(ProcessFlowSequence processflowsequence);
	
	
	String getProcessFlowNameById(long processId);
	String getServiceDetailNameById(long serviceId);
	
	
	List<String> getAllServiceDetailName();
	List<String> getAllProcessFlowName();
}
