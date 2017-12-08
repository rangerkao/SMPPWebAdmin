package com.iglomo.sms.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;


/*import com.iglomo190.SMPPServicesStub;
import com.iglomo190.SMPPServicesStub.SendSMPP;
import com.iglomo190.SMPPServicesStub.SendSMPPResponse;*/



/*import com.infotech.smpp.SMPPServicesStub;
import com.infotech.smpp.SMPPServicesStub.SendSMPP;
import com.infotech.smpp.SMPPServicesStub.SendSMPPResponse;*/


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.iglomo.SMPPServicesStub;
import com.iglomo.SMPPServicesStub.SendSMPP;
import com.iglomo.SMPPServicesStub.SendSMPPResponse;

/*import com.iglomo.sms.webservice.model.SMSContent;
import com.iglomo1992.SMPPServicesStub;
import com.iglomo1992.SMPPServicesStub.SendSMPP;
import com.iglomo1992.SMPPServicesStub.SendSMPPResponse;*/

import com.iglomo.sms.webservice.model.SMSContent;





public class SmsServiceImpl implements SmsService {
	
	private SMPPServicesStub wsClient;
	
	
	@Override
	public String send( String requestXML) throws RemoteException{
		
		System.out.println("send to iglomo199");
		
		this.wsClient = new SMPPServicesStub();
		
		SendSMPP data = new SendSMPP();
		
		data.setS(requestXML);
		//data.setArgs0( requestXML);
		
		SendSMPPResponse smppResponse = this.wsClient.sendSMPP( data);
		
		String responseXML = smppResponse.get_return();
		
		return responseXML;		
		
	}
	
	@Override
	public String query(String account,String sdate,String edate,String phone){
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<SMSContent> re = new ArrayList<SMSContent>();
		try {
			Class.forName("org.postgresql.Driver");
			//Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/smppdb?charSet=UTF-8","smpper","SmIpp3r");
			conn = DriverManager.getConnection("jdbc:postgresql://10.42.1.199:5432/smppdb?charSet=UTF-8","smpper","SmIpp3r");
			String sql = "select msgid,seq,schedule,phoneno,msgbody,tries,status,donetime,rspid,sendtime "
					+ "from msgitem where 1=1 ";
			
			if(!"".equals(account)){
				sql += "and msgid in (select msgid from messages where userid = '"+account+"') ";
			}
			
			if(!"".equals(sdate) && !"".equals(edate)){
				sql += "and schedule>='"+sdate+"' and schedule<='"+edate+"' ";
			}
			if(!"".equals(phone)){
				sql += "and phoneno='"+phone+"'  ";
			}
			
			sql += " order by schedule desc  ";
			
			st = conn.createStatement();
			
			System.out.println("sql="+sql);
			rs = st.executeQuery(sql);
			

			while(rs.next()){
				SMSContent sms = new SMSContent();
				sms.setMsgid(rs.getString("msgid"));
				sms.setSeq(rs.getInt("seq"));
				sms.setSchedule(rs.getString("schedule"));
				sms.setPhoneno(rs.getString("phoneno"));
				sms.setMsgbody(rs.getString("msgbody"));
				sms.setTries(rs.getInt("tries"));
				sms.setStatus(rs.getInt("status"));
				sms.setDonetime(rs.getString("donetime"));
				sms.setSendtime(rs.getString("sendtime"));
				sms.setRspid(rs.getString("rspid"));
				re.add(sms);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return s.toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return s.toString();
		}finally{
			
				try {
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					if(conn!=null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		
		JSONArray jo = (JSONArray) JSONObject.wrap(re);
		
	
		return jo.toString();
	}
	

	@Override
	public String delete(List<SMSContent> list){
		String msg = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<SMSContent> re = new ArrayList<SMSContent>();
		try {
			Class.forName("org.postgresql.Driver");
			//Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/smppdb?charSet=UTF-8","smpper","SmIpp3r");
			conn = DriverManager.getConnection("jdbc:postgresql://192.168.10.199:5432/smppdb?charSet=UTF-8","smpper","SmIpp3r");
			String sql = "delete from msgitem where msgid='{{msgid}}' and seq={{seq}} and status = 0 ";
			
			conn.setAutoCommit(false);
			st = conn.createStatement();
			
			for(SMSContent sms:list){
				String s = sql.replace("{{msgid}}", sms.getMsgid()).replace("{{seq}}", sms.getSeq().toString());
				System.out.println("sql="+s);
				if(st.executeUpdate(s)!=1)
					msg+="The SMS id="+sms.getMsgid()+" and seq="+sms.getSeq()+" update failed.\n";				
			}
			
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return s.toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return s.toString();
		}finally{
			
				try {
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					if(conn!=null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		if("".equals(msg))
			return "All Update is success";
		
		return msg;
	}
	
	@Override
	public String change(List<SMSContent> list,String newDate){
		String msg = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<SMSContent> re = new ArrayList<SMSContent>();
		try {
			Class.forName("org.postgresql.Driver");
			//Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/smppdb?charSet=UTF-8","smpper","SmIpp3r");
			conn = DriverManager.getConnection("jdbc:postgresql://192.168.10.199:5432/smppdb?charSet=UTF-8","smpper","SmIpp3r");
			String sql = "update msgitem set schedule = '{{schedule}}' where msgid='{{msgid}}' and seq={{seq}} and status = 0 ";
			
			conn.setAutoCommit(false);
			st = conn.createStatement();
			
			for(SMSContent sms:list){
				String s = sql.replace("{{msgid}}", sms.getMsgid()).replace("{{seq}}", sms.getSeq().toString()).replace("{{schedule}}", newDate);
				System.out.println("sql="+s);
				if(st.executeUpdate(s)!=1)
					msg+="The SMS id="+sms.getMsgid()+" and seq="+sms.getSeq()+" change failed.\n";				
			}
			
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return s.toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter s = new StringWriter();
			e.printStackTrace(new PrintWriter(s));
			return s.toString();
		}finally{
			
				try {
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					if(conn!=null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		if("".equals(msg))
			return "All change is success";
		
		return msg;
		
	}
	
}
