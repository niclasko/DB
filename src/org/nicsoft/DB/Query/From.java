package org.nicsoft.DB.Query;

import org.nicsoft.DB.Data.DataSet;
import org.nicsoft.DB.Data.DataSetColumn;

import java.util.HashMap;
import java.util.Vector;

public class From extends StatementBuilder {

    private Select select;
	private HashMap<String, DataSet> dataSets;
    private Vector<DataSet> dataSetCollection;
    private Vector<Join> joins;

    private boolean addAllColumnsFromAllDataSetsFlag;
    private Vector<String> addAllColumnsDataSetAliasesFlags;

	
	public From(Select select) {
        this.select = select;
        this.dataSets = new HashMap<String, DataSet>();
        this.dataSetCollection = new Vector<DataSet>();
        this.joins = new Vector<Join>();
        this.addAllColumnsFromAllDataSetsFlag = false;
        this.addAllColumnsDataSetAliasesFlags = new Vector<String>();
	}

	public Select getSelect() {
        return this.select;
    }

	public From $(DataSet dataSet) throws Exception {
        this.dataSetExistsCheck(dataSet.alias());
        this.dataSets.put(
            dataSet.alias(),
            dataSet
        );
        this.dataSetCollection.add(dataSet);
		return this;
	}

	public From $(String alias) throws Exception {
        return this.$(this.select.getDataSet(alias));
    }

    public StatementBuilder select() throws Exception {
        this.$(new Select(null, this.select));
	    return this.dataSets().lastElement();
    }

    public StatementBuilder with() throws Exception {
        this.$(new With(null, this.select));
        return this.dataSets().lastElement();
    }

    public StatementBuilder endSubQuery() throws Exception {
        this.bind();
        return this.select.getParentClause();
    }

	private void dataSetExistsCheck(String dataSetAlias) throws Exception {
        if(dataSetAlias != null && this.dataSets.containsKey(dataSetAlias)) {
            throw new Exception("Dataset \"" + dataSetAlias + "\" is already defined.");
        }
    }

	public From as(String dataSetAlias) throws Exception {
	    this.dataSetExistsCheck(dataSetAlias);
	    this.dataSets.remove(this.dataSetCollection.lastElement().alias());
	    this.dataSetCollection.lastElement().setAlias(dataSetAlias);
	    this.dataSets.put(
            this.dataSetCollection.lastElement().alias(),
            this.dataSetCollection.lastElement()
        );
	    return this;
    }

    public From innerJoin() {
        InnerJoin innerJoin = new InnerJoin(this);
        this.joins.add(innerJoin);
        return this;
    }

    public On on() {
        return this.joins.get(this.joins.size()-1).on();
    }

    public GroupBy groupBy() {
        return this.getSelect().groupBy();
    }

    public StatementBuilder bind() throws Exception {
	    return this.select.bind();
    }

    public void debugPrint() throws Exception {
	    this.select.debugPrint();
    }

    public void toFile(String fileName) throws Exception {
	    this.select.toFile(fileName);
    }

    public void buildExpressions() {
        for(Join join : this.joins) {
            join.on().buildExpression();
        }
    }
	
	public DataSet getDataSet(String dataSetAlias) throws Exception {
        if(!this.dataSets.containsKey(dataSetAlias)) {
            throw new Exception("Dataset \"" + dataSetAlias + "\" is not defined.");
        }
		return this.dataSets.get(dataSetAlias);
	}

	public Vector<DataSet> dataSets() {
	    return this.dataSetCollection;
    }

	public void flagAddAllColumnsFromAllDatasets() {
	    this.addAllColumnsFromAllDataSetsFlag = true;
    }

    public void flagAddAllColumnsFromDataSet(String dataSetAlias) {
        this.addAllColumnsDataSetAliasesFlags.add(dataSetAlias);
    }

    public void addAllColumnsFromAllDataSetsIfFlagged() throws Exception {
	    if(this.addAllColumnsFromAllDataSetsFlag) {
            for(DataSet dataSet : this.dataSets()) {
                dataSet.addAllColumns();
                for(DataSetColumn column : dataSet.columns()) {
                    this.getSelect().$().$(dataSet.alias(), column.name()).as(column.name());
                }
            }
        }
    }

	public void addAllColumnsFromDataSetAliasesThatAreFlagged() throws Exception {
	    for(String dataSetAlias : this.addAllColumnsDataSetAliasesFlags) {
            this.getDataSet(dataSetAlias).addAllColumns();
            for(DataSetColumn column : this.getDataSet(dataSetAlias).columns()) {
                this.getSelect().$().$(dataSetAlias, column.name()).as(column.name());
            }
        }
    }

	private boolean joinConditions() {

        for(Join join : this.joins) {
            if(!(Boolean)join.on().value()) {
                return false;
            }
        }

        return true;

    }

	public boolean next() throws Exception {
        if(this.next(dataSetCollection.size()-1)) {
            if(!this.joinConditions()) {
                return this.next();
            }
            return true;
        };
        return false;
    }

    private boolean next(int dataSetIndex) throws Exception {
        if(!dataSetCollection.get(dataSetIndex).nextRecord()) {
            if(dataSetIndex > 0) {
                dataSetCollection.get(dataSetIndex).reset();
                return this.next(dataSetIndex-1);
            } else if(dataSetIndex == 0) {
                return false;
            }
        }
        return true;
    }

    public void closeDataSets() throws Exception {
	    for(DataSet dataSet : this.dataSetCollection) {
	        dataSet.close();
        }
    }

    public Vector<Join> joins() {
	    return this.joins;
    }

}