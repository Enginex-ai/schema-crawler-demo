package ai.enginex.schema.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.enginex.schema.elasticsearch.service.TableIndexSearchService;
import ai.enginex.schema.model.Columns;
import ai.enginex.schema.model.Tables;
import lombok.extern.slf4j.Slf4j;
import schemacrawler.inclusionrule.IncludeAll;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.LimitOptionsBuilder;
import schemacrawler.schemacrawler.LoadOptionsBuilder;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.tools.executable.SchemaCrawlerExecutable;
import schemacrawler.tools.options.OutputOptionsBuilder;
import schemacrawler.tools.utility.SchemaCrawlerUtility;
import us.fatehi.utility.LoggingConfig;
import us.fatehi.utility.datasource.DatabaseConnectionSource;
import us.fatehi.utility.datasource.DatabaseConnectionSources;
import us.fatehi.utility.datasource.MultiUseUserCredentials;

@Service
@Slf4j
public final class SchemaCrawlerService {

	@Autowired
	private TableIndexSearchService tableIndexSearchService;

	private static String username = "impacteers";
	private static String password = "ouUEWFA18zgGhbX";
	private static String connectionUrl = "jdbc:postgresql://10.0.141.191:5432/impacteers?currentSchema=impacteers-dev";

	private static DatabaseConnectionSource getDataSource() {

		return DatabaseConnectionSources.newDatabaseConnectionSource(connectionUrl,
				new MultiUseUserCredentials(username, password));
	}

	public Map<String, List<Tables>> getTables() {

		Map<String, List<Tables>> tableResult = new HashMap<>();
		// Set log level
		new LoggingConfig(Level.OFF);

		// Create the options
		final LimitOptionsBuilder limitOptionsBuilder = LimitOptionsBuilder.builder()
				.includeTables(tableFullName -> !tableFullName.contains("_PK"));
		final LoadOptionsBuilder loadOptionsBuilder = LoadOptionsBuilder.builder()
				// Set what details are required in the schema - this affects the
				// time taken to crawl the schema
				.withSchemaInfoLevel(SchemaInfoLevelBuilder.standard());
		final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions()
				.withLimitOptions(limitOptionsBuilder.toOptions()).withLoadOptions(loadOptionsBuilder.toOptions());

		// Get the schema definition
		final DatabaseConnectionSource dataSource = getDataSource();
		final Catalog catalog = SchemaCrawlerUtility.getCatalog(dataSource, options);

		for (final Schema schema : catalog.getSchemas()) {
			List<Tables> listTables = new ArrayList<>();
			for (final Table table : catalog.getTables(schema)) {
				List<Columns> listColumns = new ArrayList<>();
				for (final Column column : table.getColumns()) {
					Columns columns = new Columns(column.getFullName(), column.getType().toString());
					listColumns.add(columns);
				}
				Tables tables = new Tables(table.getFullName(), listColumns);
				listTables.add(tables);
			}
			tableResult.put(schema.getFullName(), listTables);
		}

		return tableResult;
	}

	public void getSchemaAsJSON() {
		// Set log level
		new LoggingConfig(Level.ALL);

		// Create the options
		final LimitOptionsBuilder limitOptionsBuilder = LimitOptionsBuilder.builder().includeTables(new IncludeAll());
		final LoadOptionsBuilder loadOptionsBuilder = LoadOptionsBuilder.builder()
				.withSchemaInfoLevel(SchemaInfoLevelBuilder.minimum());
		final OutputOptionsBuilder outputOptions = OutputOptionsBuilder.builder().withOutputFormatValue("json")
				.withOutputFile(Paths.get("output.json"));
		final SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions()
				.withLimitOptions(limitOptionsBuilder.toOptions()).withLoadOptions(loadOptionsBuilder.toOptions());

		// Get the schema definition
		final DatabaseConnectionSource dataSource = getDataSource();

		final SchemaCrawlerExecutable command = new SchemaCrawlerExecutable("serialize");
		command.setSchemaCrawlerOptions(options);
		command.setOutputOptions(outputOptions.toOptions());
		command.setDataSource(dataSource);
		command.execute();

		uploadToElastic();

	}

	public void uploadToElastic() {
		log.info("uploadToElastic - starts");
		Path file = Paths.get("output.json");
		try {
			String jsonData = new String(Files.readAllBytes(file));
			log.info("uploadToElastic - jsonData :: {} ", jsonData);
			tableIndexSearchService.insertDocuments(jsonData, "enginex", "PostGreSql");
//			tableIndexSearchService.insertDocuments(file.toFile(), "enginex", "PostGreSql");
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("uploadToElastic - ends");
	}
}