package com.lx.hd.webscket.bean;

public class SocketLoginData {
private String sessionid;
private String accountid;
private String accountname;
private int ismobile=0;  //是否是手机  一般都是0
private String biaoshi;

public SocketLoginData(String sessionid, String accountid, String accountname,
		int ismobile, String biaoshi) {
	super();
	this.sessionid = sessionid;
	this.accountid = accountid;
	this.accountname = accountname;
	this.ismobile = ismobile;
	this.biaoshi = biaoshi;
}
public SocketLoginData() {
	super();
	// TODO Auto-generated constructor stub
}
public String getSessionid() {
	return sessionid;
}
public void setSessionid(String sessionid) {
	this.sessionid = sessionid;
}
public String getaccountid() {
	return accountid;
}
public void setaccountid(String accountid) {
	this.accountid = accountid;
}
public String getaccountname() {
	return accountname;
}
public void setaccountname(String accountname) {
	this.accountname = accountname;
}
public int getIsmobile() {
	return ismobile;
}
public void setIsmobile(int ismobile) {
	this.ismobile = ismobile;
}
public String getBiaoshi() {
	return biaoshi;
}
public void setBiaoshi(String biaoshi) {
	this.biaoshi = biaoshi;
}

}
