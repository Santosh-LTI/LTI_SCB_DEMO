package com.scb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import com.scb.model.ProcessFlow;
@RepositoryRestResource
public interface ProcessFlowRepository extends JpaRepository<ProcessFlow, Long>{
	@Query(value="SELECT * FROM processflow sd WHERE sd.processName = ?1",nativeQuery=true)
	List<ProcessFlow> findProcessFlowByName(String processName);
	@Query(value="SELECT processName FROM processflow",nativeQuery=true)
	List<String> findlistofProcessFlowName();
	@Query(value="SELECT processName FROM processflow pf WHERE pf.processId = ?1",nativeQuery=true)
	String findProcessFlowName(long processId);

}
