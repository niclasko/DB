package org.nicsoft.DB.Query.Aggregation;

import java.util.HashMap;

public class Mapper {

    private Mapper parentMapper;
    private Object value;
    private HashMap<Integer, Mapper> map;
    private int reducerIndex;

    public Mapper(Mapper parentMapper, Object value) {
        this.parentMapper = parentMapper;
        this.value = value;
        this.map = null;
        this.reducerIndex = -1;
    }

    public Mapper parentMapper() {
        return this.parentMapper;
    }

    private void initMap() {
        if(this.map != null) {
            return;
        }
        this.map = new HashMap<Integer, Mapper>();
    }

    public Mapper get(Object value) {
        this.initMap();
        if(!this.map.containsKey(value.hashCode())) {
            this.map.put(
                value.hashCode(),
                new Mapper(this, value)
            );
        }
        return this.map.get(value.hashCode());
    }

    public Object value() {
        return this.value;
    }

    public boolean hasReducer() {
        return this.reducerIndex > -1;
    }

    public int getReducer() {
        return this.reducerIndex;
    }

    public void setReducer(int reducerIndex) {
        this.reducerIndex = reducerIndex;
    }
}
