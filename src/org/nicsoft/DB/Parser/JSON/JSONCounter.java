package org.nicsoft.DB.Parser.JSON;

public class JSONCounter {
	
	private int arrayCount;
	private int associativeArrayCount;
	private int arrayEntries;
	private int associativeArrayEntries;
	
	public JSONCounter() {
		this.arrayCount = 0;
		this.associativeArrayCount = 0;
		this.arrayEntries = 0;
		this.associativeArrayEntries = 0;
	}
	
	public int arrayCount() {
		return this.arrayCount;
	}
	
	public int associativeArrayCount() {
		return this.associativeArrayCount;
	}
	
	public int arrayEntries() {
		return this.arrayEntries;
	}
	
	public int associativeArrayEntries() {
		return this.associativeArrayEntries;
	}
	
	public int incrementArrayCount() {
		return this.arrayCount++;
	}
	
	public int incrementAssociativeArrayCount() {
		return this.associativeArrayCount++;
	}
	
	public int incrementArrayEntries() {
		return this.arrayEntries++;
	}
	
	public int incrementAssociativeArrayEntries() {
		return this.associativeArrayEntries++;
	}
	
	public void resetArrayEntries() {
		this.arrayEntries = 0;
	}
	
	public void resetAssociativeArrayEntries() {
		this.associativeArrayEntries = 0;
	}
	
	public String toString() {
		return
			"arrayCount: " + this.arrayCount + "\n" +
			"associativeArrayCount: " + this.associativeArrayCount + "\n" +
			"arrayEntries: " + this.arrayEntries + "\n" +
			"associativeArrayEntries: " + this.associativeArrayEntries + "\n";
	}
}