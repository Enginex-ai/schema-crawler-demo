package ai.enginex.schema.elasticsearch.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.enginex.schema.config.Constants;
import ai.enginex.schema.elasticsearch.document.DataSources;
import ai.enginex.schema.model.TableColumn;
import ai.enginex.schema.model.SearchRequest;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TableIndexElasticSearchHelper {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Constants constants;

	private static final int MAX_RESULTS = 50;

	private static final String[] FILTER_NAMES_ONLY = { "name", "shortName", "fullName" };

	private TableIndexElasticSearchHelper() {
	}

	public BoolQuery getSearch(SearchRequest searchRequest) {
		return BoolQuery.of(b -> { //
			b.must(searchTextQuery(searchRequest));
			b.boost(2.0f);
			return b;
		});
	}

	public List<Query> searchTextQuery(SearchRequest searchRequest) {
		final List<String> searchFields = Arrays.asList(FILTER_NAMES_ONLY);
		final var query = Collections.singletonList(NativeQuery.builder() //
				.withQuery( //
						q -> q.multiMatch( //
								mm -> mm.fields(searchFields) //
										.query(searchRequest.getSearchText()) //
										.type(TextQueryType.BoolPrefix)//
										.boost(2.0f))) //
				.build().getQuery());
		log.info("{}.search() getSearchAndFilterQuery :: {}", this.getClass().getName(), query);
		return query;
	}

	public NativeQuery getSearchAndFilterQuery(SearchRequest searchRequest) {
		final var query = NativeQuery.builder() //
				.withQuery(b -> b.bool(getSearch(searchRequest))) //
				.build();
		log.info("{}.search() getSearchAndFilterQuery :: {}", this.getClass().getName(), query.getQuery());
		// Setting pagination based on the page and size from the request
		if (searchRequest.getPage() != null && searchRequest.getSize() != null) {
			final var pageRequest = PageRequest.of(searchRequest.getPage(), searchRequest.getSize());
			query.setPageable(pageRequest);
		} else {
			query.setMaxResults(MAX_RESULTS);
		}
		return query;
	}

	public List<DataSources> insertDocuments(String jsonData, String schemaName, String dbType) {
		log.info("{}.search() insertDocuments :: {}", this.getClass().getName(), jsonData);
		try {
			JsonNode jsonArray = objectMapper.readTree(jsonData);
			JsonNode columnsNodes = jsonArray.get(constants.getColumnKey());
			log.info("{}.search() insertDocuments :: columnsNodes :: {}", this.getClass().getName(), columnsNodes);
			List<DataSources> tablesIndex = new ArrayList<>();
			BulkRequest.Builder br = new BulkRequest.Builder();

			// Iterate over the JSON array and index each document separately
			for (JsonNode column : columnsNodes) {
				log.info("{}.search() insertDocuments :: column :: {}", this.getClass().getName(), column);
				TableColumn bean = objectMapper.treeToValue(column, TableColumn.class);
				DataSources d = DataSources.builder() //
						.columns(bean) //
//						.databaseType(dbType) //
//						.schemaName(schemaName) //
						.build();
				tablesIndex.add(d);
				log.info("{}.search() insertDocuments :: d :: {}", this.getClass().getName(), d);
				br.operations( //
						op -> op.index( //
								idx -> idx.index(constants.getTableIndex()) //
										.id(d.getColumns().getFullName()) //
										.document(d) //
						) //
				);

			}
			return tablesIndex;
		} catch (JsonProcessingException | IllegalArgumentException e) {
			log.error("{}.search() Error while insertDocuments :: e :: {}", this.getClass().getName(), e);
			e.printStackTrace();
		}
		return null;
	}

	public BulkRequest insertDocuments(File file, String schemaName, String dbType) {
		log.info("{}.search() insertDocuments :: file :: {}", this.getClass().getName(), file);
		try {
			BulkRequest.Builder br = new BulkRequest.Builder();

			try (// Iterate over the JSON array and index each document separately
					FileInputStream input = new FileInputStream(file)) {
				byte[] bytes = input.readAllBytes();
				BinaryData data = BinaryData.of(bytes, ContentType.APPLICATION_JSON);

				log.info("{}.search() insertDocuments :: data :: {}", this.getClass().getName(), data);
				br.operations( //
						op -> op.index( //
								idx -> idx.index(constants.getTableIndex()) //
										.document(data) //
						) //
				);
			}
			return br.build();
		} catch (IOException | IllegalArgumentException e) {
			log.error("{}.search() Error while insertDocuments :: e :: {}", this.getClass().getName(), e);
			e.printStackTrace();
		}
		return null;
	}

}
