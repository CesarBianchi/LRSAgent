package br.com.lrsbackup.LRSAgent.model;

public class LRSUploadFileFormDAO {
	private String originalFileName = new String();
	private String destinationFileName = new String();
	private String publicCloud = new String();
	
	public LRSUploadFileFormDAO() {
		super();
	}

	public LRSUploadFileFormDAO(String originalFileName, String destinationFileName, String publicCloud) {
		super();
		this.originalFileName = originalFileName;
		this.destinationFileName = destinationFileName;
		this.publicCloud = publicCloud;
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
	
	public LRSUploadFileFormDAO convert(LRSUploadFileForm pFileFull) {
		
		LRSUploadFileFormDAO localDao = new LRSUploadFileFormDAO();
		
		localDao.setDestinationFileName(pFileFull.getDestinationFileName());
		localDao.setOriginalFileName(pFileFull.getOriginalFileName());
		localDao.setPublicCloud(pFileFull.getPublicCloud());
		
		return localDao;
	}
}
