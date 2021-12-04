package br.com.lrsbackup.LRSAgent.core;

import br.com.lrsbackup.LRSAgent.utils.LRSWelcomeShow;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;

public class LRSAgentJobs {

	
	public static void startJobs() {
		
		LRSWelcomeShow welcomeShow = new LRSWelcomeShow();
		welcomeShow.showConsoleWelcomeMessage();
		/*
		//Start agent to check file system
        new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
					startFileSystemAgent();
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
            }
        }).start();
        
        */
        
		//Start agent to check files to upload
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	try {
					startUploadProcess();
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
		    }
		}).start();
		
		return;
	}
	
	private static void startFileSystemAgent() throws InterruptedException {
		
		LRSAgentFileSystem newAgentFileMonitor = new LRSAgentFileSystem();
		while (true) {
  
			try {
				newAgentFileMonitor.startMonitor();
			} catch(Exception e) {
				new LRSConsoleOut("WARNING: LRS Manager not found or not ready");
			}
			Thread.sleep(180000);
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
			Thread.sleep(180000);
		}
	}
	
}
