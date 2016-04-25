package com.iglomo.sms.webservice.model;

public class SMSContent {
	 String msgid;
	 Integer seq;
	 String schedule;
	 String phoneno;
	 String msgbody;
	 Integer tries;
	 Integer status;
	 String donetime;
	 String rspid;
	 String sendtime;
	 
	 public SMSContent(){
		 
	 }
	 
	public SMSContent(String msgid, Integer seq, String schedule,
			String phoneno, String msgbody, Integer tries, Integer status,
			String donetime, String rspid, String sendtime) {
		super();
		this.msgid = msgid;
		this.seq = seq;
		this.schedule = schedule;
		this.phoneno = phoneno;
		this.msgbody = msgbody;
		this.tries = tries;
		this.status = status;
		this.donetime = donetime;
		this.rspid = rspid;
		this.sendtime = sendtime;
	}





	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getMsgbody() {
		return msgbody;
	}
	public void setMsgbody(String msgbody) {
		this.msgbody = msgbody;
	}
	public Integer getTries() {
		return tries;
	}
	public void setTries(Integer tries) {
		this.tries = tries;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDonetime() {
		return donetime;
	}
	public void setDonetime(String donetime) {
		this.donetime = donetime;
	}
	public String getRspid() {
		return rspid;
	}
	public void setRspid(String rspid) {
		this.rspid = rspid;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	 
	 

}
