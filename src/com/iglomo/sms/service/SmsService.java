package com.iglomo.sms.service;

import java.rmi.RemoteException;
import java.util.List;

import com.iglomo.sms.webservice.model.SMSContent;

public interface SmsService {

	public abstract String send(String requestXML) throws RemoteException;

	public String query(String account, String sdate, String edate,String phone);

	public String delete(List<SMSContent> list);

	public String change(List<SMSContent> list,String newDate);

}