package com.iglomo.sms.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iglomo.sms.webservice.model.RequestItem;
import com.iglomo.sms.webservice.model.SMSRequest;

public class SendList {

	public static List<SMSRequest> getSendList(){
		List<SMSRequest> resultList = new ArrayList<SMSRequest>();

			String SMSAccount="85256093232";
			String SMSPass="85256093232";
			
			List<String> SMSmsgs = new ArrayList<String>();
			SMSmsgs.add("中華電信環球卡用戶在美國以AT＆T撥當地只要NT4元/分，上網NT2元/MB，接聽台灣門號來電NT4元/分。洽中華客服0928000107");
			

			Set<String> prefile = new HashSet<String>();
			//20150921 test data
			prefile.add("88690501");//1.	0905010000~0905019999
			prefile.add("88691000");//2.	0910000000~0910009999
			prefile.add("88691100");//3.	0911000000~0911009999
			prefile.add("88691200");//4.	0912000000~0912009999
			prefile.add("88691900");//5.	0919000000~0919009999
			prefile.add("88692100");//6.	0921000000~0921009999
			prefile.add("88692800");//7.	0928000000~0928009999
			prefile.add("88693200");//8.	0932000000~0932009999
			prefile.add("88693300");//9.	0933000000~0933009999
			prefile.add("88693400");//10.	0934000000~0934009999
			prefile.add("88693700");//11.	0937000000~0937009999
			prefile.add("88696300");//12.	0963000000~0963009999
			prefile.add("88696351");//13.	0963510000~0963519999
			prefile.add("88696500");//14.	0965000000~0965009999
			prefile.add("88697200");//15.	0972000000~0972009999
			prefile.add("88697270");//16.	0972700000~0972709999
			prefile.add("88697400");//17.	0974000000~0974009999
			prefile.add("88697500");//18.	0975000000~0975009999
			prefile.add("88697800");//19.	0978000000~0978009999
			prefile.add("88698421");//20.	0984210000~0984219999
			prefile.add("88698800");//21.	0988000000~0988009999

			
			Set<String> elseNumber = new HashSet<String>();
			//自己
			//elseNumber.add("886989235253");
		

			for(String SMSmsg:SMSmsgs){
				//同號段區間門號
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
						resultList.add(reqItem);
					}
				}
				
				//零散門號
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
					resultList.add(reqItem);
				}
			}
		return resultList;
	}
}
