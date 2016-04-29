package com.iglomo.sms.action;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import com.iglomo.sms.service.SmsService;
import com.iglomo.sms.service.SmsServiceImpl;
import com.iglomo.sms.webservice.model.RequestItem;
import com.iglomo.sms.webservice.model.SMSContent;
import com.iglomo.sms.webservice.model.SMSRequest;
import com.iglomo.sms.webservice.model.SMSResponse;
import com.opensymphony.xwork2.ActionSupport;
import com.tecnick.htmlutils.htmlentities.HTMLEntities;

@Namespace(value = "/sms")
public class SmsAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ExecutorService execService;
	private static final Logger logger = Logger.getLogger(SmsAction.class);
	private static Integer count;
	private static Long starttime;

	private SmsService smsService = new SmsServiceImpl();
	
	@Valid
	private SMSRequest reqItem;

	private SMSResponse resItem;

	public static List<String> exclusive = new ArrayList<String>();
	
	public static void setExclusive(){
	}
	
	
	public static void main(String args[]) throws Exception{
		
		setExclusive();
		
		List<SMSRequest> lr = SendList.getSendList();
		execService = Executors.newFixedThreadPool(50);
		starttime=System.currentTimeMillis();
		count=0;

		//SmsAction sa = new SmsAction();
		
		Date now = new Date();
		
		for(int i=0;i<lr.size();i++){
			/*sa.reqItem=lr.get(i);
			sa.send();  */   
			execService.execute(newSendTask(lr.get(i)));
		}
		System.out.println("execute total time:"+(new Date().getTime()-now.getTime()));
	
	}
	private static Runnable newSendTask(final SMSRequest reqItem) {
        return new Runnable() {
            public void run() {
            	SmsAction sa = new SmsAction();
            	sa.reqItem=reqItem;
    			try {
					sa.send();
					System.out.println("pass time "+(System.currentTimeMillis()-starttime));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            }
        };
	}


	@Override
	@Action(value = "input", results = { @Result(name = "success", location = "sms-input.jsp") })
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	@Action(value = "control", results = { @Result(name = "success", location = "sms-control.jsp") })
	public String control() throws Exception {
		return SUCCESS;
	}

	@Action(value = "send", results = {
			@Result(name = "success", location = "sms-input.jsp"),
			@Result(name = "input", location = "sms-input.jsp") })
	public String send() throws Exception {
	
		try {

			logger.debug("Raw Input = " + JSONUtil.serialize(this.reqItem));

			//20141024
			if(this.reqItem.getRemark()==null||"".equals(this.reqItem.getRemark()))
				this.reqItem.setRemark(" ");
			
			String requestXML = this.encodeRequest(this.reqItem);

			logger.debug("XML Input = " + requestXML);

			
			//Send SMS
			String responseXML = this.smsService.send(requestXML);

			logger.debug("Raw  Output = " + responseXML);
			if(!"".equals(responseXML)){
					this.resItem  = this.decodeResponse(responseXML);
			
					responseXML = this.encodeResponse(this.resItem);
			}
			logger.debug("XML Output = " + responseXML);

			 
			return SUCCESS;
	
		} catch (Exception e) {
			
			logger.error( e);
			
			return ERROR;
		}

	}

	/***
	 *  Convert data from bean to XML
	 */
	private String encodeRequest(SMSRequest req) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(SMSRequest.class);

		StringWriter sw = new StringWriter();

		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		m.marshal(req, sw);

		return sw.toString();

	}
	
	/***
	 *  Convert data from XML to bean.
	 *  Replace &lt; &gt; entities with < > characters.
	 */
	private SMSResponse decodeResponse(String responseXML) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(SMSResponse.class);

		Unmarshaller um = context.createUnmarshaller();

		StringReader sr = new StringReader(
				HTMLEntities.unhtmlAngleBrackets(responseXML));

		SMSResponse res = (SMSResponse) um.unmarshal(sr);

		return res;

	}

	/***
	 *  Convert data from bean to XML
	 
	 */
	private String encodeResponse(SMSResponse res) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(SMSResponse.class);

		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();

		m.marshal(res, sw);

		return sw.toString();

	}
	
	public String input;
	public String result;
	public String sdate;
	public String edate;
	public String ndate;
	public String phone;
	public String querySMSStatus(){
		
		
		
		System.out.println(input);
		result = "ajas result";
		result = smsService.query(input,sdate,edate,phone);
		return SUCCESS;
	}
	
	
	public String deleteSMS(){
		JSONArray json =new JSONArray(input);
		
		List<SMSContent> list = new ArrayList<SMSContent>();
		for(int i=0;i<json.length();i++){
			JSONObject o=json.getJSONObject(i);
			SMSContent s =new SMSContent();
			s.setMsgid(o.getString("msgid"));
			s.setSeq(o.getInt("seq"));
			list.add(s);
		}
		result = smsService.delete(list);
		
		
		return SUCCESS;
	}
	public String changeSMS(){
		JSONArray json =new JSONArray(input);
		
		List<SMSContent> list = new ArrayList<SMSContent>();
		for(int i=0;i<json.length();i++){
			JSONObject o=json.getJSONObject(i);
			SMSContent s =new SMSContent();
			s.setMsgid(o.getString("msgid"));
			s.setSeq(o.getInt("seq"));
			list.add(s);
		}
		result = smsService.change(list, ndate);
		
		
		return SUCCESS;
	}
	
	
	/**
	 * @return the reqItem
	 */
	public SMSRequest getReqItem() {
		return reqItem;
	}

	/**
	 * @param reqItem
	 *            the reqItem to set
	 */
	public void setReqItem(SMSRequest reqItem) {
		this.reqItem = reqItem;
	}

	/**
	 * @return the resItem
	 */
	public SMSResponse getResItem() {
		return resItem;
	}


	public String getInput() {
		return input;
	}


	public void setInput(String input) {
		this.input = input;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getSdate() {
		return sdate;
	}


	public void setSdate(String sdate) {
		this.sdate = sdate;
	}


	public String getEdate() {
		return edate;
	}


	public void setEdate(String edate) {
		this.edate = edate;
	}


	public String getNdate() {
		return ndate;
	}


	public void setNdate(String ndate) {
		this.ndate = ndate;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	

}
