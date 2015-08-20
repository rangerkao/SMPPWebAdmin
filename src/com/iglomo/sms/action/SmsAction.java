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

import com.iglomo.sms.service.SmsService;
import com.iglomo.sms.service.SmsServiceImpl;
import com.iglomo.sms.webservice.model.RequestItem;
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
		
		List<SMSRequest> lr = new ArrayList<SMSRequest>();
		//double msisdnStart = 0989090000B,msisdnEnd = 0989099999L;
		execService = Executors.newFixedThreadPool(50);
		starttime=System.currentTimeMillis();
		count=0;
		
		//excludeNumber exc = new excludeNumber();
		
		String SMSmsg = "您為了每次出國都要另行申請日租型上網而困擾嗎？申辦中華電信環球卡，不但漫遊享有激省費率；開通數據漫遊，更可在中港日韓印尼自動享有日租型吃到飽上網服務，不必每次申請。請即電洽0928000107辦理開通。";
		String SMSAccount="85256093232";
		String SMSPass="85256093232";
		

		Set<String> prefile = new HashSet<String>();
		prefile.add("88690501");//1.	0905010000~0905019999
		prefile.add("88691000");//2.	0910000000~0910009999
		prefile.add("88691100");//3.	0911000000~0911009999
		prefile.add("88691200");//4.	0912000000~0912009999
		prefile.add("88691900");//5.	0919000000~0919009999
		prefile.add("88692100");//6.	0921000000~0921009999
		
		
		Set<String> elseNumber = new HashSet<String>();
		elseNumber.add("886972713897");
		elseNumber.add("886933828632");
		elseNumber.add("886921612770");
		elseNumber.add("886911988232");
		elseNumber.add("886928834046");
		//7.	886972713897, 886933828632, 886921612770, 886911988232, 886928834046

		
		
		/*prefile.add("88690500");//1.	0905000000~0905009999
		prefile.add("88691023");//2.	0910230000~0910239999
		prefile.add("88691198");//3.	0911980000~0911989999
		prefile.add("88691205");//4.	0912050000~0912059999
		prefile.add("88691968");//5.	0919680000~0919689999
		prefile.add("88692161");//6.	0921610000~0921619999
		prefile.add("88692883");//7.	0928830000~0928839999
		prefile.add("88693207");//8.	0932070000~0932079999
		prefile.add("88693382");//9.	0933820000~0933829999
		prefile.add("88693409");//10.	0934090000~0934099999
		prefile.add("88693710");//11.	0937100000~0937109999
		prefile.add("88696311");//12.	0963110000~0963119999
		prefile.add("88696512");//13.	0965120000~0965129999
		prefile.add("88697213");//14.	0972130000~0972139999
		prefile.add("88697416");//15.	0974160000~0974169999
		prefile.add("88697515");//16.	0975150000~0975159999
		prefile.add("88697816");//17.	0978160000~0978169999
		prefile.add("88698817");//18.	0988170000~0988179999
		prefile.add("88698420");//19.	0984200000~0984209999
		prefile.add("88696350");//20.	0963500000~0963509999
		prefile.add("88697271");//21.	0972710000~0972719999
		prefile.add("88697290");//22.	0972900000~0972909999
*/		

		
		for(String s : prefile){
			for(int i = 0 ; i <=9999 ; i ++){
				String t=String.valueOf(i);
				for(int j=4-t.length();j>0;j--){
					t="0"+t;
				}
				String phoneno=s+t;
				
				/*if(exc.inExcludeNumber(phoneno)){
					System.out.println("is excluded!");
					continue;
				}*/
				
				List<String> number=new ArrayList<String>();
				number.add(phoneno);
				RequestItem r = new RequestItem();
				r.setSchedule("0");
				r.setMessage(SMSmsg);
				r.setCallee(number);
				List<RequestItem> list = new ArrayList<RequestItem>();
				list.add(r);
				SMSRequest reqItem=new SMSRequest();
				reqItem.setUsername(SMSAccount);
				reqItem.setPassowrd(SMSPass);
				reqItem.setOrgcode("0");
				reqItem.setRequestItemList(list);
				lr.add(reqItem);
			}
		}
		
		for(String phoneno : elseNumber){
			List<String> number=new ArrayList<String>();
			number.add(phoneno);
			RequestItem r = new RequestItem();
			r.setSchedule("0");
			r.setMessage(SMSmsg);
			r.setCallee(number);
			List<RequestItem> list = new ArrayList<RequestItem>();
			list.add(r);
			SMSRequest reqItem=new SMSRequest();
			reqItem.setUsername(SMSAccount);
			reqItem.setPassowrd(SMSPass);
			reqItem.setOrgcode("0");
			reqItem.setRequestItemList(list);
			lr.add(reqItem);
		}

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

	@Action(value = "send", results = {
			@Result(name = "success", location = "sms-input.jsp"),
			@Result(name = "input", location = "sms-input.jsp") })
	public String send() throws Exception {
	
		try {

			logger.debug("Raw Input = " + JSONUtil.serialize(this.reqItem));

			//20141024�ק�ARemark����J��Ʒ|���Ϳ�~
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
