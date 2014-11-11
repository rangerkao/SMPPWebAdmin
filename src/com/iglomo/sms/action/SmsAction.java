package com.iglomo.sms.action;

import java.io.StringReader;
import java.io.StringWriter;

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

import com.iglomo.sms.service.SmsService;
import com.iglomo.sms.service.SmsServiceImpl;
import com.iglomo.sms.webservice.model.SMSRequest;
import com.iglomo.sms.webservice.model.SMSResponse;
import com.opensymphony.xwork2.ActionSupport;
import com.tecnick.htmlutils.htmlentities.HTMLEntities;

@Namespace(value = "/sms")
public class SmsAction extends ActionSupport {

	private static final Logger logger = Logger.getLogger(SmsAction.class);

	private SmsService smsService = new SmsServiceImpl();
	
	@Valid
	private SMSRequest reqItem;

	private SMSResponse resItem;


	@Override
	@Action(value = "input", results = { @Result(name = "success", location = "sms-input.jsp") })
	public String execute() throws Exception {
		return SUCCESS;
	}

	@Action(value = "send", results = {
			@Result(name = "success", location = "sms-input.jsp"),
			@Result(name = "input", location = "sms-input.jsp") })
	public String send() throws Exception {
	
		try {
			
			logger.debug("Raw Input = " + JSONUtil.serialize(this.reqItem));

			//20141024修改，Remark未填入資料會產生錯誤
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


}
