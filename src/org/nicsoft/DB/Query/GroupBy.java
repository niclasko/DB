package org.nicsoft.DB.Query;

import org.nicsoft.DB.Data.DataSet;
import org.nicsoft.DB.Query.Aggregation.Aggregate;
import org.nicsoft.DB.Query.Aggregation.GroupByDataSetColumn;
import org.nicsoft.DB.Query.Aggregation.Mapper;
import org.nicsoft.DB.Query.Aggregation.Reducer;
import org.nicsoft.DB.Query.Expression.Expression;

import java.util.ListIterator;
import java.util.Vector;
import java.util.LinkedList;

public class GroupBy extends DataSet {

	private Select select;

	private ColumnProjection columnProjection;

	private Mapper mapper;
	private Mapper currentMapper;
	private Reducer reducer;

	private Vector<Aggregate> aggregates;

	private Vector<GroupByDataSetColumn> groupByDataSetColumns;

	private LinkedList<Mapper> leafMappers;
	private ListIterator<Mapper> leafMappersIterator;

	private boolean isAggregated;

	public GroupBy(Select select) {
		this.select = select;
		this.columnProjection = new ColumnProjection(select);
		this.mapper = new Mapper(null, null);
		this.reducer = new Reducer();
		this.aggregates = new Vector<Aggregate>();
		this.groupByDataSetColumns = new Vector<GroupByDataSetColumn>();
		this.leafMappers = new LinkedList<Mapper>();
		this.leafMappersIterator = null;
		this.resetMapper();
		this.isAggregated = false;
	}

	public Reducer getReducer() {
		return this.reducer;
	}

	public void addAggregate(Aggregate aggregate) {
		this.aggregates.add(aggregate);
	}

	public StatementBuilder $() {
		return this.columnProjection().$();
	}

	public ColumnProjection columnProjection() {
		return this.columnProjection;
	}

	private void resetMapper() {
		this.currentMapper = this.mapper;
	}

	private void map() {
		this.resetMapper();
		for(Expression column : this.columnProjection.columns()) {
			this.currentMapper =
				this.currentMapper.get(
					column.value()
				);
		}
	}

	private void reduce() {
		if(!this.currentMapper.hasReducer()) {
			this.currentMapper.setReducer(
				this.reducer.add()
			);
			this.leafMappers.add(this.currentMapper);
		}
		this.reducer.setReducer(
			this.currentMapper.getReducer()
		);
		for(Aggregate aggregate : this.aggregates) {
			aggregate.reduce();
		}
	}

	public boolean nextRecord() {
		if(this.leafMappersIterator.hasNext()) {
			Mapper leafMapper = this.leafMappersIterator.next();
			int i = this.groupByDataSetColumns.size()-1;
			this.reducer.setReducer(leafMapper.getReducer());
			if(i > -1) {
				do {
					this.groupByDataSetColumns.get(i--).setValue(leafMapper.value());
					leafMapper = leafMapper.parentMapper();
				} while(i > -1);
			}
			return true;
		}
		return false;
	}

	public void reset() {
		this.leafMappersIterator = this.leafMappers.listIterator(0);
	}

	public void mapReduce() throws Exception {

		for(Expression selectColumn : this.select.columnProjection().columns()) {
			if(selectColumn.hasColumnReferences()) {
				Expression groupByExpression =
					this.columnProjection().getExpression(selectColumn);
				if(groupByExpression != null) { // Group by contains the select expression
					selectColumn.setExpressionTreeRoot(
						groupByExpression
					);
				} else if(groupByExpression == null) {  // Group by does not contain the select expression
					for(ColumnReference columnReference : selectColumn.columnReferences()) {
						if(this.columnProjection().hasColumnReference(columnReference)) {
							columnReference.setDataSetColumnReference(
								this.columnProjection.getColumnReference(columnReference).dataSetColumnReference()
							);
						} else {
							throw new Exception("The column " + columnReference.signature() + " is not included in the GROUP BY clause.");
						}
					}
				}
			}
		}

		while(this.select.nextRecord()) {
			this.map();
			this.reduce();
		}

		for(Expression groupByColumn : this.columnProjection.columns()) {
			this.groupByDataSetColumns.add(new GroupByDataSetColumn());
			groupByColumn.setExpressionTreeRoot(
				this.groupByDataSetColumns.get(this.groupByDataSetColumns.size()-1)
			);
		}

		this.reset();

		this.isAggregated = true;
	}

	public boolean isAggregated() {
		return this.isAggregated;
	}

}