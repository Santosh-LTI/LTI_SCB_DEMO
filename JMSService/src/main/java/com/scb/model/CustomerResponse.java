package com.scb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) @ToString


public class CustomerResponse {
	
	private long responseCode;
	private String responseMessage;
	private CustomerRequestData customerRequestData;
	public long getResponseCode() {
		return responseCode;
	}
	/*public void setResponseCode(long responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public CustomerRequestData getCustomerRequestData() {
		return customerRequestData;
	}
	public void setCustomerRequestData(CustomerRequestData customerRequestData) {
		this.customerRequestData = customerRequestData;
	}
*/
}
