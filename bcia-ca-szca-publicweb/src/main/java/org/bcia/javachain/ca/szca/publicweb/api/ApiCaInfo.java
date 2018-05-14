package org.bcia.javachain.ca.szca.publicweb.api;

import com.google.gson.JsonObject;

public class ApiCaInfo {
	private com.google.gson.JsonObject json = new JsonObject(); 
	
	public JsonObject toJsonObject() {
		json.addProperty("CAName", caname);
		json.addProperty("CAChain",cachain);
		json.addProperty("Cert",cert);
		if(serverInfo!=null)
			json.add("ServerInfo",serverInfo);
		return json;
	}
	
	private String cert="";
	private String cachain="";
	private String caname="";
	private JsonObject serverInfo  ;
	 
	public String getCachain() {
		return cachain;
	}
	public void setCachain(String cachain) {
		this.cachain = cachain;
	}
	public String getCaname() {
		return caname;
	}
	public void setCaname(String caname) {
		this.caname = caname;
	}
	public String getCert() {
		return cert;
	}
	public void setCert(String cert) {
		this.cert = cert;
	}
	 
	public void setServerInfo(JsonObject serverInfo) {
		this.serverInfo = serverInfo;
	}
	
	 
	  
	   
}
