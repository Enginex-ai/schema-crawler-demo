package ai.enginex.schema.mongodb.document;

//import org.bson.BsonDateTime;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document("schema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schema {
//	@Id
	private String id;
//	private BsonDateTime generatedTime;
	private String databaseName;
	private String databaseType;
	// TODO: for now this is object.
	private Object databaseSchema;
	private Object databaseSchemaDiff;
}