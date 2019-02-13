package org.nicsoft.DB.Query;

import org.nicsoft.DB.Data.*;
import org.nicsoft.DB.Data.SubQuery.SelectDataSetColumn;
import org.nicsoft.DB.Output.CSVWriter;
import org.nicsoft.DB.Query.Expression.Expression;
import org.nicsoft.DB.Data.JDBC.data.DataSetFactory;

import java.util.Vector;

import java.io.PrintWriter;
import java.io.File;

/*

	select
		*
	from
		table1 t1
		inner join
		table2 t2
		on (t1.id = t2.id)
		inner join
		table3 t3
		on (t1.id = t3.id and
			t2.id = t3.id)

 */

public class Select extends DataSet implements IDataSetReader {

	private ColumnProjection columnProjection;
	private From from;
	private Where where;
	private GroupBy groupBy;
	private OrderBy orderBy;

	private Vector<ColumnReference> columnReferences;

	private boolean isBound;

	private int recordCount;
	private int rowLimit;

	private StatementBuilder parentClause;

	private IDataSetReader dataSetReader;

	private PrintWriter output;
	
	public Select(String alias) {

		super(alias);
		this.columnProjection = new ColumnProjection(this);
		this.from = new From(this);
		this.where = null;
		this.orderBy = null;
		this.groupBy = null;

		this.columnReferences = new Vector<ColumnReference>();

		this.isBound = false;

		this.recordCount = 0;
		this.rowLimit = -1;

		this.dataSetReader = new SelectDataSetReader(this);

		this.output = new PrintWriter(System.out);

	}

	public Select(String alias, StatementBuilder parentClause) {
		this(alias);
		this.parentClause = parentClause;
	}

	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}

	public Select top(int rowLimit) {
		this.setRowLimit(rowLimit);
		return this;
	}

	public void reset() {
		this.recordCount = 0;
		for(DataSet dataSet : this.from.dataSets()) {
			dataSet.reset();
		}
	}

	public Expression $() {
		return this.columnProjection().$();
	}

	public ColumnProjection columnProjection() {
		return this.columnProjection;
	}

	public Select star() {
		this.from().flagAddAllColumnsFromAllDatasets();
		return this;
	}

	public Select star(String dataSetAlias) {
		this.from().flagAddAllColumnsFromDataSet(dataSetAlias);
		return this;
	}

	public From from() { return this.from; }
	
	public Where where() {
		return this.where;
	}
	
	public GroupBy groupBy() {
		if(this.groupBy == null) {
			this.groupBy = new GroupBy(this);
		}
		return this.groupBy;
	}
	
	public OrderBy orderBy() {
		return this.orderBy;
	}

	public Vector<ColumnReference> columnReferences() {
		return this.columnReferences;
	}

	public StatementBuilder bind() throws Exception {
		if(!this.isBound) {

			this.from.addAllColumnsFromAllDataSetsIfFlagged();
			this.from.addAllColumnsFromDataSetAliasesThatAreFlagged();

			for(ColumnReference columnReference : this.columnReferences) {
				columnReference.bind();
			}
			this.columnProjection.buildExpressions();
			this.from.buildExpressions();
			if(this.groupBy != null) {
				this.groupBy.columnProjection().buildExpressions();
			}

			if(this.groupBy != null) {
				this.groupBy.mapReduce();
				this.dataSetReader = new GroupByDataSetReader(this);
			}

			// Todo: setup join lookup tables
			/*for(Join join : this.from.joins()) {
				;
			}*/

			this.isBound = true;
		}
		return this;
	}

	public boolean hasGroupBy() {
		return this.groupBy != null;
	}

	public boolean nextRecord() throws Exception {
		if(!this.hasGroupBy() || (this.hasGroupBy() && this.groupBy.isAggregated())) {
			if(this.rowLimit > 0) {
				if(this.recordCount == this.rowLimit) {
					return false;
				}
			}
			this.recordCount++;
		}
		return this.dataSetReader.nextRecord();
	}

	public SelectDataSetColumn columnFactory(String name) throws Exception {
		return new SelectDataSetColumn(this, name);
	}

	public void debugPrint() throws Exception {

		CSVWriter csvWriter = new CSVWriter(this.output);

		int i = 0;

		for(Expression column : this.columnProjection().columns()) {
			csvWriter.headerEntry(column.alias(), i++);
		}

		while(this.nextRecord()) {
			i = 0;
			csvWriter.newRow();
			for(Expression column : this.columnProjection().columns()) {
				csvWriter.entry(column.value().toString(), i++);
			}
		}

		csvWriter.finish();

		this.from().closeDataSets();

	}

	public void closeDataSets() throws Exception {
		if(this.from() != null) {
			this.from().closeDataSets();
		}
	}

	public static Select _Select() {
		return new Select(null);
	}

	public StatementBuilder getParentClause() {
		return this.parentClause;
	}

	public boolean hasColumn(String name) {
		return true;
	}

	public void addAllColumns() {
		for(Expression column : this.columnProjection().columns()) {
			this.addColumn(
				new SelectDataSetColumn(
					this,
					column
				)
			);
		}
	}

	public DataSet getDataSet(String alias) throws Exception {
		return null;
	}

	protected class GroupByDataSetReader implements IDataSetReader {
		private Select select;
		public GroupByDataSetReader(Select select) {
			this.select = select;
		}
		public boolean nextRecord() throws Exception {
			return this.select.groupBy().nextRecord();
		}
	}

	protected class SelectDataSetReader implements IDataSetReader {
		private Select select;
		public SelectDataSetReader(Select select) {
			this.select = select;
		}
		public boolean nextRecord() throws Exception {
			return this.select.from().next();
		}
	}

	public void toFile(String fileName) throws Exception {
		this.output = new PrintWriter(new File(fileName));
	}

	public static void main(String args[]) {

		try {

			Select select = new Select(null);

			/*select.
				$().$("c", "ID").as("ID").
				$().Cast().$("c", "ID").as(Double.class).Minus().$(100).toPowerOf().$(2).as("Arithmetic").
				$().$("c", "NAME").as("NAME").
				$().$("ci", "Headquarters").as("Headquarters").
				$().$(200).Plus().$(500).as("PlusTest").
				$().$(24534).Minus().$(245245).Plus().$(1000).as("MinusTest").
				$().$(2).toPowerOf().$(-2).as("PowerTest").
				$().$(2).MultiplyBy().$(-2).as("MultiplyTest").
				$().$(2).DivideBy().$(100.0).as("DivideTest").
				$().$("dummy", "testNumber").as("SubSelectTest").
			from().
				$(
					DataSetFactory.getExcelDataSet(
						"./src/org/nicsoft/DB/Data/JDBC/data/companies.xlsx",
						"c"
					)
				).as("c").
				innerJoin().
				$(
					DataSetFactory.getExcelDataSet(
						"./src/org/nicsoft/DB/Data/JDBC/data/companies_extra_info.xlsx",
						"ci"
					)
				).as("ci").
				on().
					OpeningParentheses().
					$("c", "ID").
					Equals().
					$("ci", "ID").
					ClosingParentheses().
				innerJoin().
				$(
					_Select().
						$().$(100).MultiplyBy().$(2.5).as("testNumber").
					from().
						$(
							(new DummyDataSet())
						).as("d").
					bind()
				).as("dummy").
				on().
					OpeningParentheses().
					$(1).
					Equals().
					$(1).
					ClosingParentheses().
			bind();*/

			select.
				top(50).
				$().$("c","BU").
				$().$(200).Plus().$(200).
			from().
				$(
					DataSetFactory.getExcelDataSet(
						"/Users/nkjalloh/Documents/Data/scforecastdb.productHierarchy.xlsx",
						"c"
					)
				).
			bind();

			/*select.
				top(10).
				$().$("c","BU").
				$().$("c","PF").
				$().count().openingFunctionParentheses().closingFunctionParentheses().as("cnt").
			from().
				$(
					DataSetFactory.getExcelDataSet(
						"/Users/nkjalloh/Documents/Data/scforecastdb.productHierarchy.xlsx",
						"c"
					)
				).as("c").
			groupBy().
				$().$("c", "BU").
				$().$("c","PF").
			bind();*/

			select.debugPrint();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}