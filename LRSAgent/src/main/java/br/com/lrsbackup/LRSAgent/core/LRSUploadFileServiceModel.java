package br.com.lrsbackup.LRSAgent.core;

import br.com.lrsbackup.LRSManager.util.LRSResponseInfo;
import br.com.lrsbackup.LRSManager.util.LRSResponseMessages;

public class LRSUploadFileServiceModel {
	public LRSResponseInfo responseInfo = new LRSResponseInfo();
	public LRSUploadFileFormDAO fileToUpload = new LRSUploadFileFormDAO();
	public LRSResponseMessages messages = new LRSResponseMessages();
	
	public LRSUploadFileServiceModel() {
		super();
	}

	public LRSUploadFileServiceModel(LRSResponseInfo responseInfo, LRSUploadFileFormDAO fileToUpload, LRSResponseMessages messages) {
		this.responseInfo = responseInfo;
		this.fileToUpload = fileToUpload;
		this.messages = messages;
	}

	public LRSResponseInfo getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(LRSResponseInfo responseInfo) {
		this.responseInfo = responseInfo;
	}

	public LRSUploadFileFormDAO getFileToUpload() {
		return fileToUpload;
	}

	public void setFileToUpload(LRSUploadFileFormDAO fileToUpload) {
		this.fileToUpload = fileToUpload;
	}

	public LRSResponseMessages getMessages() {
		return messages;
	}

	public void setMessages(LRSResponseMessages messages) {
		this.messages = messages;
	}
	
	
	
}
