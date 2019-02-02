package org.nicsoft.DB.Query.Expression;

import java.util.HashMap;

public enum ConvertType {

    NUMBER("NUMBER") {
        @Override
        public Class<?> getType() {
            return Double.class;
        }
    },
    INTEGER("INTEGER") {
        @Override
        public Class<?> getType() {
            return Integer.class;
        }
    };

    private static final HashMap<String, ConvertType> displayNameMap;

    static {

        displayNameMap = new HashMap<String, ConvertType>();

        for (ConvertType ct : ConvertType.values()) {
            displayNameMap.put(ct.displayName(), ct);
        }
    }

    public static ConvertType find(String displayName) throws Exception {

        if(!displayNameMap.containsKey(displayName.toUpperCase())) {
            throw new Exception("Cannot cast to \"" + displayName + "\".");
        }

        ConvertType convertType =
            displayNameMap.get(
                displayName.toUpperCase()
            );

        return convertType;
    }

    private String displayName;

    ConvertType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return this.displayName;
    }

    public abstract Class<?> getType();

}
