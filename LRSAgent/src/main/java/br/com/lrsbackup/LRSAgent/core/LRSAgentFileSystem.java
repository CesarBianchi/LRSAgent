package br.com.lrsbackup.LRSAgent.core;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.lrsbackup.LRSAgent.utils.LRSFileDetails;
import br.com.lrsbackup.LRSAgent.utils.LRSManagerAddress;
import br.com.lrsbackup.LRSManager.enums.LRSOptionsCloudProvider;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSQueueFileForm;
import br.com.lrsbackup.LRSManager.services.model.LRSProtectedDirServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSQueueFileServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSActivePublicClouds;

public class LRSAgentFileSystem {
	

	private String cBaseURI = new LRSManagerAddress().getLRSManagerURI();
	
	
	public void startMonitor() throws InterruptedException, IOException{
		RestTemplate restTemplate = new RestTemplate();
		
		while (true) {
			
			new LRSConsoleOut("");
			new LRSConsoleOut("");
			new LRSConsoleOut("##############################################################################################");	
			new LRSConsoleOut("################################ BEGIN FILE MONITOR CYCLE ####################################");
			new LRSConsoleOut("##############################################################################################");
			new LRSConsoleOut("");
			
			//1* - Get the list of protected directories
			LRSProtectedDirServiceModel protectedDirs = restTemplate.getForObject(cBaseURI.concat("/protecteddirs/v1/getall"), LRSProtectedDirServiceModel.class);
			
			//2* - Get what cloud providers is On.
			LRSActivePublicClouds publicClouds = new LRSActivePublicClouds();
			new LRSConsoleOut("AWS IS ON: ".concat(String.valueOf(publicClouds.isAwsOn()).toUpperCase()));
			new LRSConsoleOut("AZURE IS ON: ".concat(String.valueOf(publicClouds.isAzureOn()).toUpperCase()));
			new LRSConsoleOut("ORACLE IS ON: ".concat(String.valueOf(publicClouds.isOracleOn()).toUpperCase()));
			new LRSConsoleOut("TOTAL LOCAL DIRECTORIES MONITORED: ".concat(Integer.toString(protectedDirs.directories.size())));
			
			//2* For each directory returned, verify all files present.
			for (int nI = 0; nI < protectedDirs.directories.size(); nI++) {
				
				String cDirPath = protectedDirs.directories.get(nI).getOriginPath();
				new LRSConsoleOut("WORKING OVER DIRECTORY: ".concat(cDirPath));

				List<String> listOfFiles = new ArrayList<>();
				Files.walk(Paths.get(cDirPath))
		        .filter(Files::isRegularFile)
		        .forEach(x -> listOfFiles.add(x.toString()));
		
				new LRSConsoleOut("TOTAL FILES OF DIRECTORY: ".concat(Integer.toString(listOfFiles.size())));
				
				//3* For each file, add to LRSManager
				for (int nJ = 0; nJ < listOfFiles.size(); nJ++) {
					
					String fileName = listOfFiles.get(nJ);
					
					LRSFileDetails fileDetails = new LRSFileDetails(fileName);
					
					String cPureFileName = fileName.replaceAll(cDirPath.concat("/"),"");
					String destinationFileName = new String();
					String cCloudProvider = new String();
					String storageRepoName = protectedDirs.directories.get(nI).getStorageRepositoryName();
					
					//3.1 - If AWS is ON.
					if (publicClouds.isAwsOn()) {
						destinationFileName = protectedDirs.directories.get(nI).getDestinationPath_AWS().concat(cPureFileName);
						cCloudProvider = LRSOptionsCloudProvider.AWS.toString();
						
						//4* - Send the file
						sendNewFile(fileName, destinationFileName, cCloudProvider,fileDetails,storageRepoName);
					}
				
					//3.2 - If Azure is ON.
					if (publicClouds.isAzureOn()) {
						destinationFileName = protectedDirs.directories.get(nI).getDestinationPath_Azure().concat(cPureFileName);
						cCloudProvider = LRSOptionsCloudProvider.AZURE.toString();
						
						//4* - Send the file
						sendNewFile(fileName, destinationFileName, cCloudProvider,fileDetails,storageRepoName);
					}
					
					//3.3 - If Oracle is ON.
					if (publicClouds.isOracleOn()) {
						destinationFileName = protectedDirs.directories.get(nI).getDestinationPath_Oracle().concat(cPureFileName);
						cCloudProvider = LRSOptionsCloudProvider.ORACLE.toString();
						
						//4* - Send the file
						sendNewFile(fileName, destinationFileName, cCloudProvider,fileDetails,storageRepoName);
					}
					
				}
				
			}
			
			new LRSConsoleOut("Sleeping time! I'll return to working in three minutes. Be patience and drink a beer!");	
			Thread.sleep(1800000);
			
		}
		
	}
	
	private void sendNewFile(String fileName, String destinationFileName, String cCloudProvider, LRSFileDetails fileDetails, String storageRepoName) throws InterruptedException {
		
		try {
			//Check if the file already exists in LRS Manager
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builderURI = UriComponentsBuilder.fromHttpUrl(cBaseURI.concat("/queue/v1/getbyfullfilename")).queryParam("fullfilename", fileName);
			LRSQueueFileServiceModel fileExists = restTemplate.getForObject(builderURI.toUriString(), LRSQueueFileServiceModel.class);
			if (fileExists.directories.size() <= 0) {
				
				//5* Send filename to LRSManager to be will mapped by backup engine.
				try {
					LRSQueueFileForm filetoUpload = new LRSQueueFileForm();
					filetoUpload.setOriginalfullname(fileName);
					filetoUpload.setDestinationFileName(destinationFileName);
					filetoUpload.setCloudProvider(cCloudProvider);
					filetoUpload.setCreationDateTime(fileDetails.getCreationDateTime());
					filetoUpload.setStorageRepoName(storageRepoName);
					filetoUpload.setSize(fileDetails.getSize());
					
					LRSQueueFileServiceModel response = restTemplate.postForObject(cBaseURI.concat("/queue/v1/inserttolist"), filetoUpload, LRSQueueFileServiceModel.class);
					
					new LRSConsoleOut("File ".concat(fileName).concat(" successfully sent to LRS Manager Service."));	
				}
				catch(Exception e) {
					new LRSConsoleOut("File ".concat(fileName).concat(" skipped. Already present in LRS Manager environment."));
				}
			} else {
				new LRSConsoleOut("File ".concat(fileName).concat(" skipped. Already present in LRS Manager environment."));
			}
		}
		catch(Exception e) {
			new LRSConsoleOut("WARNING: Something was wrong while trying sent file ".concat(fileName).concat(" We will try again in next working cycle"));
		}
		Thread.sleep(500);
		
		return;
	}

}
