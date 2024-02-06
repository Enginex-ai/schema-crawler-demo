package ai.enginex.schema.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.enginex.schema.mongodb.document.Schema;
import ai.enginex.schema.mongodb.repository.SchemaRepository;

@Service
public class SchemaService {
//
//	@Autowired
//	private SchemaRepository schemaRepository;

	public List<Schema> getSchemasReversed() {
//		return schemaRepository.getSchemasSortById();
		return null;
	}

	public Schema saveSchema(Schema schema) {
//		return schemaRepository.save(schema);
		return null;
	}

}
