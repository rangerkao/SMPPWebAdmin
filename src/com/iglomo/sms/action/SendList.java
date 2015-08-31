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

			String SMSAccount="TaishinBank";
			String SMSPass="TaishinBank";
			
			List<String> SMSmsgs = new ArrayList<String>();
			/*SMSmsgs.add("測試簡訊1_親愛的客戶您好，感謝您申請台新個人網路/行動銀行，"
					+ "成功加入次日起30天內您尚未登入行動銀行，完成登入及一筆跨轉交易享晶彩點66點回饋，"
					+ "詳官網。 https://www.taishinbank.com.tw/WB/app/");
			
			SMSmsgs.add("測試簡訊2_台新貴賓您好，您信用卡單筆預借現金額度為NT30000元，將隨時依信用狀況調整，"
					+ "欲確認請撥卡背電話。手續費為每筆金額3％，最低100元 ");
			
			SMSmsgs.add("測試簡訊3_【台新銀行面試邀約】:應徵者您好,因電話連絡不到您,本行欲邀請您前來參加面試,"
					+ "若有意願煩請您回電");*/
			
			SMSmsgs.add("測試簡訊4_台新銀行理財商品處國際市場資訊: 美股連三漲，GE宣布將回購股票並退出風險較高的金融業務激勵大盤，"
					+ "道瓊漲0.6%、標普漲0.5%至2102點。"
					+ "道瓊歐洲600漲0.9%續創新高。俄股跌0.4%、巴西股漲0.8%。美國油氣鑽井平台數連降18週，創1986年來最大降幅，"
					+ "布蘭特原油漲2.7%至58.1美元、西德州漲1.9%至51.8美元。南非幣貶0.5%至12。"
					+ "隨上證昨漲1.9%至4034點，香港國企股近期動能亦增強，昨漲1.7%，港交所CEO李小加稱，"
					+ "未來滬港通投資額度會至少增20%~30%。惠譽(FITCH)對韓國及巴西債信展望一升一降，瑞銀分析師表示希臘近期違約機率已升高至50%以上。 "
					+ "美元兌離岸人民幣 (-0.0001=6.2178) "
					+ "美元兌人民幣 (0.0028=6.2087) 歐元兌美元 (-0.0055=1.0604) 美元兌日圓 (-0.3600=120.22) "
					+ "美元兌南非幣 (0.0554=11.9962) 美元兌新台幣 (0.0980=31.226) 美元對新幣 (-0.0018=1.3664) "
					+ "澳幣兌美元 (-0.0010=0.7682) 黃金(美元/盎司) (1.1%=1207.6) 西德州中級原油(美元/桶) (2.3%=57.9) "
					+ "布蘭特油價(美元/桶) (1.7%=51.6) 美國10年期公債殖利率 (-1.23bps=1.947%) :"
					+ "本訊息僅供內部教育訓練使用，請勿外流");
			

			Set<String> prefile = new HashSet<String>();
			/*prefile.add("88690501");//1.	0905010000~0905019999
			prefile.add("88691000");//2.	0910000000~0910009999
			prefile.add("88691100");//3.	0911000000~0911009999
			prefile.add("88691200");//4.	0912000000~0912009999
			prefile.add("88691900");//5.	0919000000~0919009999
			prefile.add("88692100");//6.	0921000000~0921009999
*/			
			
			Set<String> elseNumber = new HashSet<String>();
			/*elseNumber.add("886972713897");
			elseNumber.add("886933828632");
			elseNumber.add("886921612770");
			elseNumber.add("886911988232");
			elseNumber.add("886928834046");*/
			
			//20150825 台新銀行 測試
			//自己
			elseNumber.add("886989235253");
			//Yvonne 
			//elseNumber.add("886972900642");
			//elseNumber.add("886921612770");
			//小道
			//elseNumber.add("886972713897");
			//台新
			/*elseNumber.add("886928609952");
			elseNumber.add("886933892886");
			elseNumber.add("886920433191");
			elseNumber.add("886912199780");
			elseNumber.add("886922966585");
			elseNumber.add("886937073507");
			elseNumber.add("886932313926");
			elseNumber.add("886932916992");
			elseNumber.add("886920591737");
			elseNumber.add("886983789027");
			elseNumber.add("886932012810");
			elseNumber.add("886926853772");
			elseNumber.add("886936166789");
			elseNumber.add("886939702228");
			elseNumber.add("886936051161");
			elseNumber.add("886928801558");
			elseNumber.add("886916243660");
			elseNumber.add("886920943149");
			elseNumber.add("886926338350");
			elseNumber.add("886928523343");
			elseNumber.add("886935966187");
			elseNumber.add("886935605683");
			elseNumber.add("886939981385");
			elseNumber.add("886920030938");
			elseNumber.add("886937095023");
			elseNumber.add("886922021869");
			elseNumber.add("886932350391");
			elseNumber.add("886958161951");
			elseNumber.add("886919028132");
			elseNumber.add("886917183385");
			elseNumber.add("886921072128");
			elseNumber.add("886933750997");
			elseNumber.add("886926831138");
			elseNumber.add("886919110502");
			elseNumber.add("886927074234");
			elseNumber.add("886912025584");
			elseNumber.add("886933154161");
			elseNumber.add("886911277700");
			elseNumber.add("886912499798");
			elseNumber.add("886955150005");
			elseNumber.add("886918283449");
			elseNumber.add("886911288809");
			elseNumber.add("886938131766");
			elseNumber.add("886930908689");
			elseNumber.add("886939699001");
			elseNumber.add("886919272020");
			elseNumber.add("886920433500");
			elseNumber.add("886921842401");
			elseNumber.add("886937021336");
			elseNumber.add("886933703090");
			elseNumber.add("886915855532");
			elseNumber.add("886970117888");
			elseNumber.add("886912550681");
			elseNumber.add("886923355348");
			elseNumber.add("886920433717");
			elseNumber.add("886910387561");
			elseNumber.add("886912968697");
			elseNumber.add("886913658616");
			elseNumber.add("886922112608");
			elseNumber.add("886931732597");
			elseNumber.add("886920433918");
			elseNumber.add("886922000817");
			elseNumber.add("886912762985");
			elseNumber.add("886929483266");
			elseNumber.add("886988225965");
			elseNumber.add("886920433816");
			elseNumber.add("886930069333");
			elseNumber.add("886910373830");
			elseNumber.add("886917777734");
			elseNumber.add("886953257999");
			elseNumber.add("886911504413");
			elseNumber.add("886939112226");
			elseNumber.add("886953575617");
			elseNumber.add("886935247222");
			elseNumber.add("886932185644");
			elseNumber.add("886928839705");
			elseNumber.add("886919898064");
			elseNumber.add("886933022214");
			elseNumber.add("886938038028");
			elseNumber.add("886936318413");
			elseNumber.add("886960843344");
			elseNumber.add("886910238238");
			elseNumber.add("886972900025");
			elseNumber.add("886972713897");
			elseNumber.add("886972900642");
			elseNumber.add("85266406642");
			elseNumber.add("8613910486642");
			elseNumber.add("6584780622");
			elseNumber.add("66901980642");
			elseNumber.add("6285574900642");
			elseNumber.add("886921612770");
			elseNumber.add("886920055951");
			elseNumber.add("886935629639");*/
			
			
			
			
			
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
