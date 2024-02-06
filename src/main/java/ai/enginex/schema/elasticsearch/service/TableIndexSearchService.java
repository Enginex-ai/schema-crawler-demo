package ai.enginex.schema.elasticsearch.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import ai.enginex.schema.elasticsearch.document.DataSources;
import ai.enginex.schema.elasticsearch.helper.TableIndexElasticSearchHelper;
import ai.enginex.schema.model.SearchRequest;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TableIndexSearchService extends GlobalSearchService<DataSources> {
	public static final String LOG_SEARCH_SEARCH_REQUEST = "{}.search() searchRequest :: {}";
	@Autowired
	private ElasticsearchOperations operations;

	@Autowired
	private ElasticsearchClient client;

	@Autowired
	TableIndexElasticSearchHelper helper;

	public Optional<List<DataSources>> searchAndFilter(SearchRequest searchRequest) {
		log.info(LOG_SEARCH_SEARCH_REQUEST, this.getClass().getName(), searchRequest);
		final var query = helper.getSearchAndFilterQuery(searchRequest);
		return getResults(query, DataSources.class, operations);
	}

	public void insertDocuments(String jsonData, String schemaName, String dbType) {
		final var query = helper.insertDocuments(jsonData, schemaName, dbType);
		bulkInsert(query, operations);
	}

	public void insertDocuments(File jsonData, String schemaName, String dbType) {
		final var query = helper.insertDocuments(jsonData, schemaName, dbType);
		bulkInsert(query, client);
	}
}