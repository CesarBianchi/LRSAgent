package br.com.lrsbackup.LRSAgent.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lrsbackup.LRSManager.enums.LRSOptionsCloudProvider;
import br.com.lrsbackup.LRSManager.persistence.controller.form.LRSQueueFileForm;
import br.com.lrsbackup.LRSManager.services.model.LRSConfigServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSProtectedDirServiceModel;
import br.com.lrsbackup.LRSManager.services.model.LRSQueueFileServiceModel;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;

public class LRSAgentCore {
	

	private String cBaseURI = new String("http://192.168.0.101:6001/LRSManager");
	
	
	public void startMonitor() throws InterruptedException, IOException {
		
		while (true) {
			
			//1* - Get the list of protected directories
			RestTemplate restTemplate = new RestTemplate();
			LRSProtectedDirServiceModel protectedDirs = restTemplate.getForObject(cBaseURI.concat("/protecteddirs/v1/getall"), LRSProtectedDirServiceModel.class);
			
			//2* - Get what cloud providers is On.
			LRSConfigServiceModel awsIsOn = restTemplate.getForObject(cBaseURI.concat("/configs/v1/awsisenabled"), LRSConfigServiceModel.class); 
			LRSConfigServiceModel azureIsOn = restTemplate.getForObject(cBaseURI.concat("/configs/v1/azureisenabled"), LRSConfigServiceModel.class); 
			LRSConfigServiceModel oracleIsOn = restTemplate.getForObject(cBaseURI.concat("/configs/v1/oracleisenabled"), LRSConfigServiceModel.class); 
			
			//2* For each directory returned, verify all files present.
			for (int nI = 0; nI < protectedDirs.directories.size(); nI++) {
				
				String cDirPath = protectedDirs.directories.get(nI).getOriginPath();
				new LRSConsoleOut("Checking directory ".concat(cDirPath));
				
				List<String> listOfFiles = new ArrayList<>();
				Files.walk(Paths.get(cDirPath))
		        .filter(Files::isRegularFile)
		        .forEach(x -> listOfFiles.add(x.toString()));
		
				//3* For each file, add to LRSManager
				for (int nJ = 0; nJ < listOfFiles.size(); nJ++) {
					
					String fileName = listOfFiles.get(nJ);
					File moreFileDetails = new File(fileName);
					
					String cPureFileName = fileName.replaceAll(cDirPath.concat("/"),"");
					String destinationFileName = new String();
					String cCloudProvider = new String();
					
					//3.1 - If AWS is ON.
					if (awsIsOn.getEnabled().toUpperCase().equals("TRUE")) {
						destinationFileName = protectedDirs.directories.get(nI).getDestinationPath_AWS().concat(cPureFileName);
						cCloudProvider = LRSOptionsCloudProvider.AWS.toString();
					}
				
					//3.2 - If Azure is ON.
					if (azureIsOn.getEnabled().toUpperCase().equals("TRUE")) {
						destinationFileName = protectedDirs.directories.get(nI).getDestinationPath_Azure().concat(cPureFileName);
						cCloudProvider = LRSOptionsCloudProvider.AZURE.toString();
					}
					
					//3.3 - If Oracle is ON.
					if (oracleIsOn.getEnabled().toUpperCase().equals("TRUE")) {
						destinationFileName = protectedDirs.directories.get(nI).getDestinationPath_Oracle().concat(cPureFileName);
						cCloudProvider = LRSOptionsCloudProvider.ORACLE.toString();
						
					}
										
					//4* - Verify if file already exists. If yes, don't send again.
					try {
						UriComponentsBuilder builderURI = UriComponentsBuilder.fromHttpUrl(cBaseURI.concat("/queue/v1/getbyfullfilename")).queryParam("fullfilename", fileName);
						LRSQueueFileServiceModel fileExists = restTemplate.getForObject(builderURI.toUriString(), LRSQueueFileServiceModel.class);
						if (fileExists.directories.size() <= 0) {
							
							//5* Send filename to LRSManager to be will mapped by backup engine.
							try {
								LRSQueueFileForm filetoUpload = new LRSQueueFileForm();
								filetoUpload.setOriginalfullname(fileName);
								filetoUpload.setDestinationFileName(destinationFileName);
								filetoUpload.setCloudProvider(cCloudProvider);
								LRSQueueFileServiceModel response = restTemplate.postForObject(cBaseURI.concat("/queue/v1/inserttolist"), filetoUpload, LRSQueueFileServiceModel.class);
								
								new LRSConsoleOut("Arquivo ".concat(fileName).concat(" enviado com sucesso para o LRS Manager."));	
							}
							catch(Exception e) {
								new LRSConsoleOut("Arquivo ".concat(fileName).concat(" desprezado. Ja mapeado no ambiente LRS Manager."));
							}
						} else {
							new LRSConsoleOut("Arquivo ".concat(fileName).concat(" desprezado. Ja mapeado no ambiente LRS Manager."));
						}
					}
					catch(Exception e) {
						new LRSConsoleOut("WARNING: Something was wrong while trying map file ".concat(fileName).concat(" We will try again in next cycle!"));
					}
					Thread.sleep(1000);
				}
				
			}
			
			Thread.sleep(5000);
			
		}
		
	}

		
}
