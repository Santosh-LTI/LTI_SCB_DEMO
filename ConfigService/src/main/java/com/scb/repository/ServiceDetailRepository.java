package com.scb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.scb.model.ServiceDetail;

@RepositoryRestResource
public interface ServiceDetailRepository extends JpaRepository<ServiceDetail, Long>{
	@Query(value="SELECT * FROM servicedetail sd WHERE sd.serviceName = ?1",nativeQuery=true)
	List<ServiceDetail> findServiceDetailByName(String serviceName);
	@Query(value="SELECT serviceName FROM servicedetail",nativeQuery=true)
	List<String> findlistofServiceDetailName();
	@Query(value="SELECT serviceName FROM servicedetail  sd WHERE sd.serviceId = ?1",nativeQuery=true)
	String findServiceDetailName(long serviceid);
	
}
