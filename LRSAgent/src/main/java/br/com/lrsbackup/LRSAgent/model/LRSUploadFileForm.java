package br.com.lrsbackup.LRSAgent.model;

public class LRSUploadFileForm {
	
	private String originalFileName = new String();
	private String destinationFileName = new String();
	private String publicCloud = new String();
	private String cspUserName = new String();
	private String cspUserKey = new String();
		
	public LRSUploadFileForm() {
		super();
	}
	
	public LRSUploadFileForm(String originalFileName, String destinationFileName, String publicCloud) {
		this.originalFileName = originalFileName;
		this.destinationFileName = destinationFileName;
		this.publicCloud = publicCloud;
	}
	
	public LRSUploadFileForm(String originalFileName, String destinationFileName, String publicCloud, String cspUserName, String cspUserKey) {
		this.originalFileName = originalFileName;
		this.destinationFileName = destinationFileName;
		this.publicCloud = publicCloud;
		this.cspUserName = cspUserName;
		this.cspUserKey = cspUserKey;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}
	
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
	public String getDestinationFileName() {
		return destinationFileName;
	}
	
	public void setDestinationFileName(String destinationFileName) {
		this.destinationFileName = destinationFileName;
	}
	
	public String getPublicCloud() {
		return publicCloud;
	}
	
	public void setPublicCloud(String publicCloud) {
		this.publicCloud = publicCloud;
	}

	public String getCspUserName() {
		return cspUserName;
	}

	public void setCspUserName(String cspUserName) {
		this.cspUserName = cspUserName;
	}

	public String getCspUserKey() {
		return cspUserKey;
	}

	public void setCspUserKey(String cspUserKey) {
		this.cspUserKey = cspUserKey;
	}
	
	
	
}
