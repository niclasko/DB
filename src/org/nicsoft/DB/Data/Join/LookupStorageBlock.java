package org.nicsoft.DB.Data.Join;

public class LookupStorageBlock<T> {
	
	private LookupStorage lookupStorage;
	private int from;
	private int to;
	private int highWaterMark;
	private LookupStorageBlock currentBlock;
	private LookupStorageBlock nextBlock;
	
	public LookupStorageBlock(LookupStorage lookupStorage, int from, int to) {
		this.lookupStorage = lookupStorage;
		this.from = from;
		this.to = to;
		this.highWaterMark = 0;
		this.currentBlock = this;
		this.nextBlock = null;
	}
	
	public int from() {
		return this.currentBlock.from();
	}
	
	public int to() {
		return this.currentBlock.to();
	}
	
	public int size() {
		return (this.to - this.from);
	}
	
	public int highWaterMark() {
		return this.currentBlock.highWaterMark;
	}
	
	public void incrementHighWaterMark() {
		
		this.highWaterMark++;
		
		if(this.highWaterMark == this.size()) {
			
			this.nextBlock =
				new LookupStorageBlock(
					this.lookupStorage,
					this.lookupStorage.rowIndexesOffset() + this.to(),
					this.lookupStorage.rowIndexesOffset() + this.to() + this.size()
				);
			
			this.currentBlock = this.nextBlock;
			
		}
		
	}
	
	public LookupStorageBlock currentBlock() {
		return this.currentBlock;
	}
	
	public LookupStorageBlock nextBlock() {
		return this.nextBlock;
	}
	
	public void write(Integer rowIndex) {
		
		this.lookupStorage.writeToRowIndexes(
			this.currentBlock().from() + this.highWaterMark,
			rowIndex
		);
		
		this.incrementHighWaterMark();

	}
	
}