package org.nicsoft.DB.Query;

import org.nicsoft.DB.Data.DataSet;

import java.util.HashMap;

public class With extends Select {

	private HashMap<String, DataSet> subQueries;
	private DataSet latestSubQuery;
	
	public With(String alias, StatementBuilder parentClause) {
		super(alias, parentClause);
		this.subQueries = new HashMap<String, DataSet>();
	}

	public StatementBuilder select() {
		this.latestSubQuery = new Select(null, this);
		return this.latestSubQuery;
	}

	public StatementBuilder as(String alias) throws Exception {
		this.latestSubQuery.setAlias(alias);
		if(this.subQueries.containsKey(alias)) {
			throw new Exception("Subquery \"" + alias + "\" is already defined.");
		}
		this.subQueries.put(alias, this.latestSubQuery);
		return this;
	}

	public DataSet getDataSet(String alias) throws Exception {
		if(!this.subQueries.containsKey(alias)) {
			throw new Exception("Subquery \"" + alias + "\" is not defined.");
		}
		return this.subQueries.get(alias);
	}

}