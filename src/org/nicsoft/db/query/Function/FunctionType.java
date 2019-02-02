package org.nicsoft.DB.Query.Function;

import java.util.HashMap;

public enum FunctionType {

    PI("PI") {
        @Override
        public BaseFunction create() {
            return new Pi();
        }
    },
    E("E") {
        @Override
        public BaseFunction create() {
            return new E();
        }
    },
    REPLACE("REPLACE") {
        @Override
        public ParametricFunction create() {
            return new Replace();
        }
    };

    private static final HashMap<String, FunctionType> displayNameMap;

    static {

        displayNameMap = new HashMap<String, FunctionType>();

        for (FunctionType ft : FunctionType.values()) {
            displayNameMap.put(ft.displayName(), ft);
        }
    }

    public static FunctionType find(String displayName) throws Exception {

        if(!displayNameMap.containsKey(displayName.toUpperCase())) {
            throw new Exception("Function \"" + displayName + "\" is not implemented.");
        }

        FunctionType functionType =
            displayNameMap.get(
                displayName.toUpperCase()
            );

        return functionType;
    }

    private String displayName;

    FunctionType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return this.displayName;
    }

    public abstract BaseFunction create();

}
