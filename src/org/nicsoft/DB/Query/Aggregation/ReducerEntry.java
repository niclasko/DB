package org.nicsoft.DB.Query.Aggregation;

public class ReducerEntry {

    private Reducer reducer;
    private int entryCount;
    private int reducerEntryIndex;
    private Object initialValue;

    public ReducerEntry(Reducer reducer, int entryCount, int reducerEntryIndex, Object initialValue) {
        this.reducer = reducer;
        this.entryCount = entryCount;
        this.reducerEntryIndex = reducerEntryIndex;
        this.initialValue = initialValue;
    }

    public int entryCount() {
        return this.entryCount;
    }

    public int reducerEntryIndex() {
        return this.reducerEntryIndex;
    }

    public Object initialValue() {
        return this.initialValue;
    }

    public void setInitialValue(Object initialValue) {
        this.initialValue = initialValue;
        for(int i=this.reducerEntryIndex; i<(this.reducerEntryIndex+this.entryCount); i++) {
            this.reducer.set(i, this.initialValue);
        }
    }

}
