package br.com.lrsbackup.LRSAgent.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;
import br.com.lrsbackup.LRSManager.util.LRSOperationalSystem;

public class LRSRenameFileLibrary {

	private String oldName = new String();
	private String newName = new String();
	private ArrayList<LRSSpecialCharsTranslate> specialChars = new ArrayList<>();
	
	public LRSRenameFileLibrary() {
		this.specialChars = getSpecialCharsList();
		
	}

	public LRSRenameFileLibrary(String oldName, String newName) {
		this.specialChars = getSpecialCharsList();
		this.oldName = oldName;
		this.newName = newName;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	private ArrayList<LRSSpecialCharsTranslate> getSpecialCharsList(){

		ArrayList<LRSSpecialCharsTranslate> specialCharsList = new ArrayList<>();
		LRSSpecialCharsTranslate lowerChar = new LRSSpecialCharsTranslate();
		LRSSpecialCharsTranslate upperChar = new LRSSpecialCharsTranslate();
		
		specialCharsList.add(new LRSSpecialCharsTranslate("á","a"));
		specialCharsList.add(new LRSSpecialCharsTranslate("à","a"));
		specialCharsList.add(new LRSSpecialCharsTranslate("ã","a"));
		specialCharsList.add(new LRSSpecialCharsTranslate("é","e"));
		specialCharsList.add(new LRSSpecialCharsTranslate("ẽ","e"));
		specialCharsList.add(new LRSSpecialCharsTranslate("í","i"));
		specialCharsList.add(new LRSSpecialCharsTranslate("ĩ","i"));	
		specialCharsList.add(new LRSSpecialCharsTranslate("ó","o"));
		specialCharsList.add(new LRSSpecialCharsTranslate("õ","o"));
		specialCharsList.add(new LRSSpecialCharsTranslate("ú","u"));
		specialCharsList.add(new LRSSpecialCharsTranslate("ũ","u"));
		specialCharsList.add(new LRSSpecialCharsTranslate("ç","c"));

		
		//Add in the same list the same chars in Upper Case!
		ArrayList<LRSSpecialCharsTranslate> lowerChars = (ArrayList<LRSSpecialCharsTranslate>) specialCharsList.clone();
		for (int nI=0; nI< lowerChars.size(); nI++) {
			
			lowerChar = lowerChars.get(nI);
			upperChar.setCharFrom(lowerChar.getCharFrom().toUpperCase());
			upperChar.setCharDest(lowerChar.getCharDest().toUpperCase());
			
			specialCharsList.add(upperChar);
		}
		
		return specialCharsList;
	}
	
	public void fixDir(String rootPath) throws IOException {
		boolean restart = true;
		String delimiter = new LRSOperationalSystem().getFilePathSeparator();
		String[] onePath;
		String cPathToRename = new String("");
		
		new LRSConsoleOut("Fix Directory and Files names STARTED over ".concat(rootPath));
		
		while (restart) {
			
			//Get a list of dirs and files inside rootPath
			List<String> listOfFiles = new ArrayList<>();
			Files.walk(Paths.get(rootPath))
	        .filter(Files::isRegularFile)
	        .forEach(x -> listOfFiles.add(x.toString()));
			
			//Set the flag of restart as False
			restart = false;
			
			new LRSConsoleOut(" Starting analysis over ".concat(rootPath));
			
			for (int nFiles = 0; nFiles < listOfFiles.size(); nFiles++) {
			
				onePath = listOfFiles.get(nFiles).split(delimiter);
				cPathToRename = "";
				
				new LRSConsoleOut(" Starting analysis over ".concat(rootPath));
	    						
			    for (int nSubFolders = 0; nSubFolders < onePath.length; nSubFolders++) {
			    	
			    	new LRSConsoleOut(" I'll check if needs rename ".concat(cPathToRename.concat(onePath[nSubFolders])));
			    	
			    	if (this.needToRename(onePath[nSubFolders])) {
			    		
			    		new LRSConsoleOut(" I'll try rename ".concat(cPathToRename.concat(onePath[nSubFolders])));
			    		
			    		this.setOldName(cPathToRename.concat(onePath[nSubFolders]));
			    		this.setNewName(cPathToRename.concat(this.renameTo(onePath[nSubFolders])));
			    		this.renameFile();
			    		
			    		restart = true;
			    		break;
			    	} else {
			    		cPathToRename = cPathToRename.concat(onePath[nSubFolders]).concat(delimiter);
			    		new LRSConsoleOut(" Not needed rename ".concat(cPathToRename.concat(onePath[nSubFolders])));
			    		restart = false;
			    	}
			    }
			    
			    if (restart) {
			    	break;
			    }
			}
			
		}
		new LRSConsoleOut("Fix Directory and Files names FINISHED over ".concat(rootPath));
	}
	
	public boolean needToRename(String sourceFile) {
		boolean needed = false;
		
		for (int nI = 0; nI < this.specialChars.size(); nI++) {
			if (sourceFile.trim().contains(this.specialChars.get(nI).getCharFrom()) ) {
				needed = true;
				break;
			}
		}	
		return needed;
	}
	
	public String renameTo(String sourceFile) {
	
		String originalName = new String(sourceFile);
		
		for (int nI = 0; nI < this.specialChars.size(); nI++) {
			
			if (originalName.trim().contains(this.specialChars.get(nI).getCharFrom()) ) {
				originalName = originalName.replace(this.specialChars.get(nI).getCharFrom(), this.specialChars.get(nI).getCharDest());
			}
		}	
		return originalName;
	}
	
	
	public boolean renameFile() {
		boolean success = false;
		
		File sourceFile = new File(this.oldName);
		File destFile = new File(this.newName);
		
		if (sourceFile.renameTo(destFile)) {
		    new LRSConsoleOut("File ".concat(this.oldName).concat(" renamed successfully to ").concat(this.newName));
			success = true;
		} else {
			new LRSConsoleOut("File ".concat(this.oldName).concat(" renamed FAIL to ").concat(this.newName));
			success = false;
		}
		
		return success;		
	}
	
}
