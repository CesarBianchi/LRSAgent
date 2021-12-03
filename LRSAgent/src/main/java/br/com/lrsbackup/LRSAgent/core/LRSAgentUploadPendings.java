package br.com.lrsbackup.LRSAgent.core;

import org.springframework.web.client.RestTemplate;

import br.com.lrsbackup.LRSAgent.utils.LRSManagerAddress;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;

public class LRSAgentUploadPendings {

	private String cBaseURI = new LRSManagerAddress().getLRSManagerURI();
	
	public LRSAgentUploadPendings() {
		super();
	}

	public void refreshUploadPendings() throws InterruptedException {
		
		while (true) {
		
			new LRSConsoleOut("");
			new LRSConsoleOut("");
			new LRSConsoleOut("###################################################################################################");	
			new LRSConsoleOut("################################### BEGIN REFRESH UPLOAD CYCLE ####################################");
			new LRSConsoleOut("###################################################################################################");
			new LRSConsoleOut("");
			
			
			RestTemplate restTemplate = new RestTemplate();
			LRSUploadFileServiceModel uploadsPending = restTemplate.getForObject(cBaseURI.concat("/queue/v1/uploadpendings"), LRSUploadFileServiceModel.class);
		
			for (int nI = 0; nI < uploadsPending.getMessages().getMessages().size(); nI++) {
				new LRSConsoleOut("UPLOAD CYCLE MESSAGE: ".concat(uploadsPending.getMessages().getMessages().get(nI).getMessage()));	
			}
			
			Thread.sleep(180000);
		}
	}
	
}
