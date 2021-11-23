package br.com.lrsbackup.LRSAgent.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.lrsbackup.LRSManager.util.LRSApplicationVersion;
import br.com.lrsbackup.LRSManager.util.LRSConsoleOut;

public class LRSWelcomeShow {
	private List<String> message = new ArrayList<>();

	public LRSWelcomeShow() {
		super();
		
		this.setWelcomeMessage();
		
	}

	public void showConsoleWelcomeMessage() {
		
		for (int nI = 1; nI < message.size(); nI++) {
			new LRSConsoleOut(message.get(nI));
		}
	}
	
	private void setWelcomeMessage() {
		LRSApplicationVersion appDetails = new LRSApplicationVersion();
		
		message.add("");

		message.add(" _      _____   _____                         _   ");
		message.add("| |    |  __ \\ / ____|  /\\                   | |  ");
		message.add("| |    | |__) | (___   /  \\   __ _  ___ _ __ | |_ ");
		message.add("| |    |  _  / \\___ \\ / /\\ \\ / _` |/ _ \\ '_ \\| __|");
		message.add("| |____| | \\ \\ ____) / ____ \\ (_| |  __/ | | | |_ ");
		message.add("|______|_|  \\_\\_____/_/    \\_\\__, |\\___|_| |_|\\__|");
		message.add("                               __/ |               ");
		message.add("                              |___/                ");
		message.add("");
		message.add("");
		message.add("VERSION: ".concat(appDetails.getServiceVersion()));
		message.add("A Part of 'LRS Backup Application'");
		message.add("Developed by: Cesar Bianchi");
		message.add("Contact: cesar_bianchi@hotmail.com");
		message.add("Licensed by GNU General Public License v3.0 ");
		message.add("");

		
	}
	
	
}
