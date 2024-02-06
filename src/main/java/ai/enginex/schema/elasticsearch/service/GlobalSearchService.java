/**
 * This package contains the classes related to the global search functionality using Elasticsearch.
 */
package ai.enginex.schema.elasticsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import lombok.extern.slf4j.Slf4j;

/**
 * This abstract class provides a base implementation for the global search
 * service.
 * 
 * @param <T> the type of the search results
 */
@Slf4j
public abstract class GlobalSearchService<T> {

	/**
	 * Retrieves the search results based on the provided native query, class, and
	 * Elasticsearch operations.
	 * 
	 * @param q          the native query to be executed
	 * @param clazz      the class of the search results
	 * @param operations the Elasticsearch operations object
	 * @return an optional list of search results
	 */
	protected Optional<List<T>> getResults(NativeQuery q, Class<T> clazz, ElasticsearchOperations operations) {
		final SearchHits<T> searchHits = operations.search(q, clazz);
		log.info("{}.getResults() - count :: {}", this.getClass().getName());
		final var result = searchHits.get().map(SearchHit::getContent).toList();
		return Optional.of(result);
	}

	protected void bulkInsert(BulkRequest request, ElasticsearchClient client) {
		BulkResponse result;
		try {
			log.info("bulkInsert request :: {} ", request);
			result = client.bulk(request);
			if (result.errors()) {
				log.error("{} :: bulkInsert had errors", request);
				for (BulkResponseItem item : result.items()) {
					if (item.error() != null) {
						log.error(item.error().reason());
					}
				}
			}
		} catch (ElasticsearchException | IOException e) {
			log.error("Error while processing bulk index request :: {}", e);
		}
	}

	protected void bulkInsert(List<T> query, ElasticsearchOperations operations) {
		for (T table : query) {
			operations.save(table);
		}
	}

}
