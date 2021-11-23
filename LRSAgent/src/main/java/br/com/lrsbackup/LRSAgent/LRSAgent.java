package br.com.lrsbackup.LRSAgent;

import java.io.IOException;

import br.com.lrsbackup.LRSAgent.core.LRSAgentCore;
import br.com.lrsbackup.LRSAgent.utils.LRSWelcomeShow;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;

public class LRSAgent {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {    
		
		LRSWelcomeShow welcomeShow = new LRSWelcomeShow();
		welcomeShow.showConsoleWelcomeMessage();
		
		
		
		LRSAgentCore newAgent = new LRSAgentCore();
		while (true) {
  
			try {
				newAgent.startMonitor();
			} catch(Exception e) {
				new LRSConsoleOut("WARNING: LRS Manager not found or not ready");
			}
			Thread.sleep(10000);
		}
}
}
