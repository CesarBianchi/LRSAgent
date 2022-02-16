package br.com.lrsbackup.LRSAgent.core;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lrsbackup.LRSAgent.utils.LRSWelcomeShow;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSManagerAddress;

public class LRSAgentJobs {

	private static boolean renameFilesEnabled;
	
	public LRSAgentJobs() {
		super();
		checkDirectoriesNames();
	}

	public static void startJobs() {
		
		LRSWelcomeShow welcomeShow = new LRSWelcomeShow();
		welcomeShow.showConsoleWelcomeMessage();
		
		//Start agent to check file system
        new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
					startFileSystemAgent();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }).start();
        
        
        
		//Start agent to check files to upload
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	try {
					startUploadProcess();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    }
		}).start();
		
		return;
	}
	
	private static void startFileSystemAgent() throws InterruptedException {
		
		LRSAgentFileSystem newAgentFileMonitor = new LRSAgentFileSystem(renameFilesEnabled);
		while (true) {
  
			try {
				newAgentFileMonitor.startMonitor();
			} catch(Exception e) {
				new LRSConsoleOut("WARNING: LRS Manager not found or not ready");
			}
		}
		
	}
	
	private static void startUploadProcess() throws InterruptedException {
		LRSAgentUploadPendings newAgentUploadPendings = new LRSAgentUploadPendings();
		
		while (true) {
			
			try {
				newAgentUploadPendings.refreshUploadPendings();
			} catch(Exception e) {
				new LRSConsoleOut("WARNING: LRS Manager not found or not ready");
			}
		}
	}

	private void checkDirectoriesNames() {
		
		try {
			renameFilesIfActive();
		} catch(Exception e) {
			new LRSConsoleOut("WARNING: LRS Manager not found or not ready");
		}
		
		return;
	}
	
	private void renameFilesIfActive() {
		String cBaseURI = new LRSManagerAddress().getLRSManagerURI();
		
		
		//Check if the option is Enabled in LRSManager
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builderURI = UriComponentsBuilder.fromHttpUrl(cBaseURI.concat("/parameters/v1/getbyname")).queryParam("name", "RenameIrregularDirNames");
		LRSParameterServiceModel renameDirIsActive = restTemplate.getForObject(builderURI.toUriString(), LRSParameterServiceModel.class);
	
		if (renameDirIsActive != null) {
			if (renameDirIsActive.parameters.get(0).getValue().trim().toUpperCase().equals("TRUE")) {
				this.renameFilesEnabled = true;
			} else {
				this.renameFilesEnabled = false;
			}
		}
		
		return;
	}
	
}
