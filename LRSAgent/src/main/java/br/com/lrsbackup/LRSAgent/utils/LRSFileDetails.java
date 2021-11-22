package br.com.lrsbackup.LRSAgent.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;

public class LRSFileDetails {
	private LocalDateTime creationDateTime;;
	private long size;
	
	public LRSFileDetails() {
		super();
	}

	public LRSFileDetails(LocalDateTime creationDate, long size) {
		this.creationDateTime = creationDate;
		this.size = size;
	}

	public LRSFileDetails(String fileName) throws IOException {
				
		Path file = Paths.get(fileName);
		BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(file, BasicFileAttributeView.class);
        
        //get basic attributes
        BasicFileAttributes basicFileAttributes = fileAttributeView.readAttributes();
        
        this.creationDateTime = LocalDateTime.parse(basicFileAttributes.creationTime().toString().substring(0, 19));
        this.size = Files.size(file);
		
	}



	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(LocalDateTime creationDate) {
		this.creationDateTime = creationDate;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	
}
