package br.com.lrsbackup.LRSAgent;

import java.io.IOException;

import br.com.lrsbackup.LRSAgent.core.LRSAgentFileSystem;
import br.com.lrsbackup.LRSAgent.core.LRSAgentJobs;
import br.com.lrsbackup.LRSAgent.utils.LRSWelcomeShow;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;

public class LRSAgent {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {    
		
		LRSAgentJobs agentJobs = new LRSAgentJobs();
		agentJobs.startJobs();
		
    }
	
	
}
