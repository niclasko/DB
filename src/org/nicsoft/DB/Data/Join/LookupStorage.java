package org.nicsoft.DB.Data.Join;

import java.util.HashMap;
import java.util.Vector;

public class LookupStorage {
	
	private final static int LOOKUP_BLOCK_SIZE = 10;
	
	private HashMap<Object, LookupStorageBlock> index;
	private Vector<Integer> rowIndexes;
	private int rowIndexesOffset;
	
	private LookupStorageBlock currentBlock;
	
	public LookupStorage() {
		this.index = new HashMap<Object, LookupStorageBlock>();
		this.rowIndexes = new Vector<Integer>();
		this.rowIndexesOffset = 0;
		this.currentBlock = null;
	}
	
	public void write(Object key, Integer rowIndex) {
		
		this.currentBlock = this.index.get(key);
		
		if(this.currentBlock == null) {
			
			this.index.put(
				key,
				new LookupStorageBlock(
					this,
					this.rowIndexesOffset,
					this.rowIndexesOffset + LookupStorage.LOOKUP_BLOCK_SIZE
				)
			);
			
			this.rowIndexesOffset =
				this.rowIndexesOffset + LookupStorage.LOOKUP_BLOCK_SIZE;
			
			this.currentBlock = this.index.get(key);
			
			this.rowIndexes.ensureCapacity(this.rowIndexesOffset);
			
		}
		
		this.currentBlock.write(
			rowIndex
		);
		
	}
	
	public void writeToRowIndexes(int position, Integer rowIndex) {
		this.rowIndexes.add(position, rowIndex);
	}
	
	public int rowIndexesOffset() {
		return this.rowIndexesOffset;
	}
	
}