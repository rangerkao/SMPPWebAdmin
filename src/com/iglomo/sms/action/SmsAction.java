package com.iglomo.sms.action;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.iglomo.sms.service.SmsService;
import com.iglomo.sms.service.SmsServiceImpl;
import com.iglomo.sms.webservice.model.RequestItem;
import com.iglomo.sms.webservice.model.SMSRequest;
import com.iglomo.sms.webservice.model.SMSResponse;
import com.opensymphony.xwork2.ActionSupport;
import com.tecnick.htmlutils.htmlentities.HTMLEntities;

@Namespace(value = "/sms")
public class SmsAction extends ActionSupport {
	private static ExecutorService execService;
	private static final Logger logger = Logger.getLogger(SmsAction.class);
	private static Integer count;
	private static Long starttime;

	private SmsService smsService = new SmsServiceImpl();
	
	@Valid
	private SMSRequest reqItem;

	private SMSResponse resItem;

	public static List<String> exclusive = new ArrayList<String>();
	
	public static void setex(){
		exclusive.add("886989090000");
		exclusive.add("886989090005");
		exclusive.add("886989090006");
		exclusive.add("886989090007");
		exclusive.add("886989090008");
		exclusive.add("886989090009");
		exclusive.add("886989090010");
		exclusive.add("886989090012");
		exclusive.add("886989090017");
		exclusive.add("886989090019");
		exclusive.add("886989090020");
		exclusive.add("886989090022");
		exclusive.add("886989090023");
		exclusive.add("886989090025");
		exclusive.add("886989090026");
		exclusive.add("886989090029");
		exclusive.add("886989090030");
		exclusive.add("886989090033");
		exclusive.add("886989090034");
		exclusive.add("886989090035");
		exclusive.add("886989090037");
		exclusive.add("886989090039");
		exclusive.add("886989090046");
		exclusive.add("886989090047");
		exclusive.add("886989090051");
		exclusive.add("886989090052");
		exclusive.add("886989090053");
		exclusive.add("886989090055");
		exclusive.add("886989090056");
		exclusive.add("886989090057");
		exclusive.add("886989090058");
		exclusive.add("886989090059");
		exclusive.add("886989090061");
		exclusive.add("886989090063");
		exclusive.add("886989090066");
		exclusive.add("886989090069");
		exclusive.add("886989090072");
		exclusive.add("886989090073");
		exclusive.add("886989090074");
		exclusive.add("886989090075");
		exclusive.add("886989090076");
		exclusive.add("886989090078");
		exclusive.add("886989090080");
		exclusive.add("886989090082");
		exclusive.add("886989090083");
		exclusive.add("886989090089");
		exclusive.add("886989090092");
		exclusive.add("886989090095");
		exclusive.add("886989090098");
		exclusive.add("886989090099");
	}
	
	
	public static void main(String args[]) throws Exception{
		
		setex();
		
		List<SMSRequest> lr = new ArrayList<SMSRequest>();
		//double msisdnStart = 0989090000B,msisdnEnd = 0989099999L;
		execService = Executors.newFixedThreadPool(50);
		starttime=System.currentTimeMillis();
		count=0;
		
		for(int i =5000 ;i<10000;i++){
			
			String t=String.valueOf(i);
			for(int j=4-t.length();j>0;j--){
				t="0"+t;
			}
			t="88698909"+t;
			List<String> number=new ArrayList<String>();
			if(!exclusive.contains(t))
				number.add(t);
			
			RequestItem r = new RequestItem();
			r.setSchedule("0");
			r.setMessage("最近去日韓嗎?申辦中華電信代辦環球卡，上網每日最高只要499元，撥接日韓當地及打回台灣均比一般漫遊節省約50%。詳洽02-27197171");
			r.setCallee(number);
			List<RequestItem> list = new ArrayList<RequestItem>();
			list.add(r);
			SMSRequest reqItem=new SMSRequest();
			reqItem.setUsername("8529300001");
			reqItem.setPassowrd("8529300001");
			reqItem.setOrgcode("0");
			reqItem.setRequestItemList(list);
			lr.add(reqItem);
		}
		
		
		
		
		SmsAction sa = new SmsAction();
		
		Date now = new Date();
		
		for(int i=0;i<lr.size();i++){
			/*sa.reqItem=lr.get(i);
			sa.send();  */   
			execService.execute(newSendTask(lr.get(i)));
		}
		System.out.println("發送經過時間:"+(new Date().getTime()-now.getTime()));
	
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
	private static void creatdata(){
		
	}
	

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
