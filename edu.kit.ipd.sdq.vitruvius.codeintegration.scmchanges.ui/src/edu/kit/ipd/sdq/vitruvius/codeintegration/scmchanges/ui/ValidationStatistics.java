package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui;

public class ValidationStatistics {
	
	private int validExtractions = 0;
	
	private int totalExtractions = 0;
	
	public void addValidExtractions(int valid) {
		validExtractions = validExtractions + valid;
	}
	
	public void addTotalExtractions(int total) {
		totalExtractions = totalExtractions + total;
	}

	public int getValidExtractions() {
		return validExtractions;
	}

	public int getTotalExtractions() {
		return totalExtractions;
	}
}
