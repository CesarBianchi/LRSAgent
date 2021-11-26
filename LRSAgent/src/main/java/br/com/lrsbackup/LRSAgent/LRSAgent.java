package br.com.lrsbackup.LRSAgent;

import java.io.IOException;

import br.com.lrsbackup.LRSAgent.core.LRSAgentCore;
import br.com.lrsbackup.LRSAgent.utils.LRSWelcomeShow;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;

public class LRSAgent {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {    
		
		LRSWelcomeShow welcomeShow = new LRSWelcomeShow();
		welcomeShow.showConsoleWelcomeMessage();
		
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
		        
        
    }
		
		
	private static void startFileSystemAgent() throws InterruptedException {
		
		LRSAgentCore newAgent = new LRSAgentCore();
		while (true) {
  
			try {
				newAgent.startUploadProcess();
			} catch(Exception e) {
				new LRSConsoleOut("WARNING: LRS Manager not found or not ready");
			}
			Thread.sleep(10000);
		}
		
	}
	
	
	private static void startUploadProcess() throws InterruptedException {
		
	}
	
	
	
	
}
