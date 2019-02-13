package org.nicsoft.DB.Query;

import org.nicsoft.DB.Data.DataSet;
import org.nicsoft.DB.Data.DataSetColumn;
import org.nicsoft.DB.Data.DataSetColumnReference;
import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;
import org.nicsoft.DB.Query.Expression.Atom;

/**
 * Created by nkjalloh on 14/11/2016.
 */
public class ColumnReference extends Atom {

    private Select select;
    private String dataSetAlias;
    private String columnName;
    private DataSetColumnReference dataSetColumnReference;

    public ColumnReference(Select select, String dataSetAlias, String columnName) {
        super(OperatorType.NONE, KeyWord.LITERAL);
        this.select = select;
        this.dataSetAlias = dataSetAlias;
        this.columnName = columnName;
        this.dataSetColumnReference = null;
    }

    public String dataSetAlias() {
        return this.dataSetAlias;
    }

    public String columnName() {
        return this.columnName;
    }

    public DataSetColumnReference bind() throws Exception {
        this.dataSetColumnReference =
            this.select.from().getDataSet(
                this.getDataSetAliasWithNullCheck()
            ).addColumn(
                this.columnName()
            );
        return this.dataSetColumnReference;
    }

    private String getDataSetAliasWithNullCheck() throws Exception {
        if(this.dataSetAlias() == null) {
            String dataSetAliasToReturn = null;
            for(DataSet dataSet : this.select.from().dataSets()) {
                if(dataSet.hasColumn(this.columnName())) {
                    if(dataSetAliasToReturn != null) {
                        throw new Exception("Column " + this.columnName + " ambiguously defined.");
                    }
                    dataSetAliasToReturn = dataSet.alias();
                }
            }
            if(dataSetAliasToReturn == null) {
                throw new Exception("Column " + this.columnName + " does not exist.");
            }
            return dataSetAliasToReturn;
        }
        return this.dataSetAlias();
    }

    public DataSetColumnReference dataSetColumnReference() {
        return this.dataSetColumnReference;
    }

    public void setDataSetColumnReference(DataSetColumnReference dataSetColumnReference) {
        this.dataSetColumnReference = dataSetColumnReference;
    }

    public Object value() {
        return this.dataSetColumnReference.value();
    }

    public String alias() {
        if(this.dataSetColumnReference == null) {
            return null;
        }
        return this.dataSetColumnReference.alias();
    }

    public String lookupCode() {
        return ((this.dataSetAlias != null ? this.dataSetAlias + "." : "") + this.columnName).intern();
    }

    public String signature() {
        return this.lookupCode();
    }

    public DataSetColumn getDataSetColumn() {
        return this.dataSetColumnReference.getDataSetColumn();
    }
}
