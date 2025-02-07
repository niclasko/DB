package org.nicsoft.DB.Parser.SQL;

public class FieldReference {

    private String dataSetAlias;
    private String columnName;

    public FieldReference(String fieldReferenceToken) throws Exception {

        if(fieldReferenceToken == null) {
            throw new Exception("Field reference not specified");
        }

        String fieldReferenceTokenParts[] = fieldReferenceToken.split("\\.");
        if(fieldReferenceTokenParts.length == 2) {
            this.dataSetAlias = fieldReferenceTokenParts[0];
            this.columnName = fieldReferenceTokenParts[1];
        } else if(fieldReferenceTokenParts.length == 1) {
            this.dataSetAlias = null;
            this.columnName = fieldReferenceTokenParts[0];
        } else if(fieldReferenceTokenParts.length > 2) {
            throw new Exception("Field reference \"" + fieldReferenceToken + "\" is not valid.");
        }
    }
    public String dataSetAlias() {
        return this.dataSetAlias;
    }

    public String columnName() {
        return this.columnName;
    }

    public boolean star() {
        return this.columnName.equals("*");
    }

}
