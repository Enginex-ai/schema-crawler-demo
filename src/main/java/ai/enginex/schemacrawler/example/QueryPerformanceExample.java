package ai.enginex.schemacrawler.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

import schemacrawler.schema.ResultsColumn;
import schemacrawler.schema.ResultsColumns;
import schemacrawler.tools.utility.SchemaCrawlerUtility;
import us.fatehi.utility.LoggingConfig;
import us.fatehi.utility.datasource.DatabaseConnectionSource;
import us.fatehi.utility.datasource.DatabaseConnectionSources;
import us.fatehi.utility.datasource.MultiUseUserCredentials;

public final class QueryPerformanceExample {

	public static void main(final String[] args) throws Exception {

		// Set log level
		new LoggingConfig(Level.OFF);

		final String query = "SELECT T1.*, T2.* FROM TABLE1_PK T1 JOIN TABLE2_PK T2 ON T1.ENTITY_ID = T2.ENTITY_ID";
		try (final Connection connection = getDataSource().get();
				final Statement statement = connection.createStatement();
				final ResultSet results = statement.executeQuery(query)) {
			// Get result set metadata
			final ResultsColumns resultColumns = SchemaCrawlerUtility.getResultsColumns(results);
			for (final ResultsColumn column : resultColumns) {
				System.out.println("o--> " + column);
				System.out.println("     - label:     " + column.getLabel());
				System.out.println("     - data-type: " + column.getColumnDataType());
				System.out.println("     - table:     " + column.getParent());
			}
		}
	}

	private static DatabaseConnectionSource getDataSource() {
		String connectionUrl = "jdbc:postgresql://127.0.0.1:5432/enginex";
		return DatabaseConnectionSources.newDatabaseConnectionSource(connectionUrl,
				new MultiUseUserCredentials("postgres", "admin"));
	}
}