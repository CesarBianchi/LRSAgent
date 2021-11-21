package br.com.lrsbackup.LRSAgent;

import java.io.IOException;

import br.com.lrsbackup.LRSAgent.core.LRSAgentCore;

public class LRSAgent {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {    
	    
	  LRSAgentCore newAgent = new LRSAgentCore();
	  newAgent.startMonitor();
        
	  
    }
	
}
