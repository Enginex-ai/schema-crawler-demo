package ai.enginex.schemacrawler.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import schemacrawler.crawl.ConnectionInfoBuilder;
import us.fatehi.utility.datasource.DatabaseConnectionSource;
import us.fatehi.utility.datasource.DatabaseConnectionSources;
import us.fatehi.utility.datasource.MultiUseUserCredentials;

public class ConnectionCheck {

	public static void main(final String[] args) throws SQLException {
		final Connection connection = getDataSource().get();
		System.out.println(ConnectionInfoBuilder.builder(connection).buildDatabaseInfo());
		System.out.println(ConnectionInfoBuilder.builder(connection).buildJdbcDriverInfo());

		final DatabaseMetaData dbMetaData = connection.getMetaData();
		final ResultSet results = dbMetaData.getTables(null, null, "%", null);
		while (results.next()) {
			final String catalogName = results.getString("TABLE_CAT");
			final String schemaName = results.getString("TABLE_SCHEM");
			final String tableName = results.getString("TABLE_NAME");
			final String tableType = results.getString("TABLE_TYPE");
			System.out.printf("o--> %s//%s//%s (%s)%n", catalogName, schemaName, tableName, tableType);
		}
	}

	private static DatabaseConnectionSource getDataSource() {
		String connectionUrl = "jdbc:postgresql://127.0.0.1:5432/enginex";
		return DatabaseConnectionSources.newDatabaseConnectionSource(connectionUrl,
				new MultiUseUserCredentials("postgres", "admin"));
	}
}