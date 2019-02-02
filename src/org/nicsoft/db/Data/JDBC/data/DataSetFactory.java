package org.nicsoft.DB.Data.JDBC.data;

import org.nicsoft.DB.Data.DataSet;
import org.nicsoft.DB.Data.JDBC.JDBCDataSet;
import org.nicsoft.DB.Data.JDBC.JDBCDriver;
import org.nicsoft.DB.Logging.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSetFactory {

	public static DataSet getExcelDataSet(String fileName) {
		return DataSetFactory.getExcelDataSet(
			fileName,
			null
		);
	}

	public static DataSet getExcelDataSet(String fileName, String dataSetName) {
		
		try {
			
			Connection connection =
				JDBCDriver.getConnection(
					"./drivers/ExcelJDBC.jar",
					"org.nicsoft.jdbc.Excel.ExcelDriver",
					"jdbc:excel://",
					"",
					""
				);
		
			Statement statement = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY
			);
	
			ResultSet resultSet =
				statement.executeQuery(fileName);
		
			DataSet dataSet =
				new JDBCDataSet(
					dataSetName,
					resultSet
				);
		
			return dataSet;
			
		} catch (SQLException e) {
			Logger.log(e);
		} catch (Exception e) {
			Logger.log(e);
		}
		
		return null;
		
	}

	/*

		DataSetFactory.getMySqlDataSet(
			"jdbc:mysql://drilldown.mysql.domeneshop.no:3306/drilldown",
			"drilldown",
			"Kuupt999",
			"select country, count(1) as cnt from s_userinfo group by country",
			"md"
		)

	*/
	public static DataSet getMySqlDataSet(String connectionInfo, String userName, String passWord, String query, String dataSetName) {

		try {

			Connection connection =
					JDBCDriver.getConnection(
							"./drivers/mysql-connector-java-5.1.35-bin.jar",
							"com.mysql.jdbc.Driver",
							connectionInfo,
							userName,
							passWord
					);

			Statement statement = connection.createStatement(
					ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY
			);

			ResultSet resultSet =
					statement.executeQuery(query);

			DataSet dataSet =
					new JDBCDataSet(
							dataSetName,
							resultSet
					);

			return dataSet;

		} catch (SQLException e) {
			Logger.log(e);
		} catch (Exception e) {
			Logger.log(e);
		}

		return null;

	}
	
}