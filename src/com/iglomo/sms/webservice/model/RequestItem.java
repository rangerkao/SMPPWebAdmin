package com.iglomo.sms.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement(name = "ITEM")
@XmlType(propOrder = { "schedule", "isMultiple", "message", "callees", "callee"})
public class RequestItem {
	
	@NotBlank(message="MUST HAVE")
	private String callee;
	
	private List<String> callees;
	
	@NotBlank(message="MUST HAVE")
	private String isMultiple="0";
	
	@NotBlank(message="MUST HAVE")
	private String message="";
	
	public RequestItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//@Pattern(message = "0�箏�������, �澆��旖YYY/MM/DD HH24:MI:SS,",regexp="0|([2][0-1][0-9][0-9]/(0?[1-9]|1[012]/[1-9]|[12][0-9]|3[01]))")
	@NotBlank(message="MUST HAVE")
	private String schedule = "0";

	@XmlElement(name = "PHONE")
	public List<String> getCallees() {
		return callees;
	}

	@XmlElement(name = "MULTIPLE")
	public String getIsMultiple() {
		return isMultiple;
	}

	@XmlElement(name = "MSG")
	public String getMessage() {
		return message;
	}

	@XmlElement(name = "SCHEDULE")
	public String getSchedule() {
		return schedule;
	}
	
	public String getCallee() {
		return callee;
	}

	/**
	 * @param calleeList the calleeList to set
	 */
	public void setCallee(String callee) {
		this.callee = callee;
		
		List<String> c = new ArrayList<String>();
		for(String s:callee.replaceAll("\\s", ",").replace(",,", ",").replace(",,", ",").split(",")){
			c.add(s);
		}
		this.callees = c ;
		
	}
	
	/*public void setCallees(List<String> callees) {
		this.callees = callees;
	}*/

	/**
	 * @param isMultiple the isMultiple to set
	 */
	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	
	

}
