package ai.enginex.schema.mongodb.repository;

import java.util.List;

//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ai.enginex.schema.mongodb.document.Schema;

//@Repository
public interface SchemaRepository {// extends MongoRepository<Schema, String> {

//	@Query(sort = "{_id : -1}")
	List<Schema> getSchemasSortById();

}
