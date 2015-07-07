package com.iglomo.sms.service;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

/*import com.iglomo190.SMPPServicesStub;
import com.iglomo190.SMPPServicesStub.SendSMPP;
import com.iglomo190.SMPPServicesStub.SendSMPPResponse;*/

/*import com.infotech.smpp.SMPPServicesStub;
import com.infotech.smpp.SMPPServicesStub.SendSMPP;
import com.infotech.smpp.SMPPServicesStub.SendSMPPResponse;*/


import com.iglomo199.SMPPServicesStub;
import com.iglomo199.SMPPServicesStub.SendSMPP;
import com.iglomo199.SMPPServicesStub.SendSMPPResponse;


/*import com.iglomo.SMPPServicesStub;
import com.iglomo.SMPPServicesStub.SendSMPP;
import com.iglomo.SMPPServicesStub.SendSMPPResponse;*/




public class SmsServiceImpl implements SmsService {
	
	private SMPPServicesStub wsClient;
	
	
	@Override
	public String send( String requestXML) throws RemoteException{
		
		this.wsClient = new SMPPServicesStub();
		
		SendSMPP data = new SendSMPP();
		
		data.setS(requestXML);
		//data.setArgs0( requestXML);
		
		SendSMPPResponse smppResponse = this.wsClient.sendSMPP( data);
		
		String responseXML = smppResponse.get_return();
		
		return responseXML;		
		
	}
	
	
	
	
}
