package br.com.lrsbackup.LRSAgent.utils;

public class LRSManagerAddress {
	
	private String LRSManagerURI = new String();

	public LRSManagerAddress() {
		super();
		this.setLRSManagerURI();
	}

	private void setLRSManagerURI() {
		this.LRSManagerURI = "http://192.168.0.101:6001/LRSManager";
		
	}

	public String getLRSManagerURI() {
		return LRSManagerURI;
	}
	
	
	
}
