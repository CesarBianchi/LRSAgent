package br.com.lrsbackup.LRSAgent.utils;

public class LRSSpecialCharsTranslate {
	
	private String charFrom = new String();
	private String charDest = new String();
	
	public LRSSpecialCharsTranslate() {
		super();
	}

	public LRSSpecialCharsTranslate(String charFrom, String charDest) {
		super();
		this.charFrom = charFrom;
		this.charDest = charDest;
	}

	public String getCharFrom() {
		return charFrom;
	}

	public void setCharFrom(String charFrom) {
		this.charFrom = charFrom;
	}

	public String getCharDest() {
		return charDest;
	}

	public void setCharDest(String charDest) {
		this.charDest = charDest;
	}
	
	
	
}
