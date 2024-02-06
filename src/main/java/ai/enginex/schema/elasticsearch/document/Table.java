package ai.enginex.schema.elasticsearch.document;

import java.util.List;

import lombok.Data;

@Data
public class Table {
	
	private List<Column> columns; 

}
