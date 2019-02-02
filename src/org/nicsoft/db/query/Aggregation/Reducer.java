package org.nicsoft.DB.Query.Aggregation;

import java.util.Vector;

public class Reducer {

    private int entriesPerReducer;
    private Vector<Vector<Object>> reducers;
    private int reducerIndex;

    private Vector<ReducerEntry> reducerEntries;

    public Reducer() {
        this.entriesPerReducer = 0;
        this.reducers = new Vector<Vector<Object>>();
        this.reducerIndex = -1;
        this.reducerEntries = new Vector<ReducerEntry>();
    }

    public ReducerEntry claimReducerEntries(int reducerEntryCount, Object initialValue) {
        this.reducerEntries.add(
            new ReducerEntry(
                this,
                reducerEntryCount,
                this.entriesPerReducer,
                initialValue
            )
        );
        this.entriesPerReducer += reducerEntryCount;
        return this.reducerEntries.lastElement();
    }

    public int add() {
        this.reducers.add(
            new Vector<Object>(this.entriesPerReducer)
        );
        int reducerEntryIndex = 0;
        for(ReducerEntry reducerEntry : this.reducerEntries) {
            for(int i=0; i<reducerEntry.entryCount(); i++) {
                this.reducers.lastElement().add(
                    reducerEntryIndex++,
                    reducerEntry.initialValue()
                );
            }
        }
        return this.reducers.size()-1;
    }

    public void setReducer(int reducerIndex) {
        this.reducerIndex = reducerIndex;
    }

    public Object get(int reducerEntryIndex) {
        return this.reducers.get(this.reducerIndex).get(reducerEntryIndex);
    }

    public void set(int reducerEntryIndex, Object value) {
        this.reducers.get(this.reducerIndex).set(reducerEntryIndex, value);
    }

}
