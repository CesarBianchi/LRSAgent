package br.com.lrsbackup.LRSAgent.utils;

public class LRSManagerAddress {
	
	private String LRSManagerURI = new String();

	public LRSManagerAddress() {
		super();
		this.setLRSManagerURI();
	}

	private void setLRSManagerURI() {
		
		//NOTE: Don't forget to add "LRSManagerPath" as Environment Variable (including docker environments)
		//Example 01: http://<your_IP_Address>:<port>/LRSBackup/LRSManager (If you want using any API Gateway)
		//Example 01: http://<your_IP_Address>:<port>/LRSManager (If you don't want using API Gateway, call LRSManager directly)
		
		this.LRSManagerURI = System.getenv("LRSManagerPath");
		
	}

	public String getLRSManagerURI() {
		return LRSManagerURI;
	}
	
	
	
}
