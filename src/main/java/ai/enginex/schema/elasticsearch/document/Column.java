package ai.enginex.schema.elasticsearch.document;

import lombok.Data;

@Data
public class Column {

	  private String defaultValue;
	  private String referencedColumn;
	  boolean isAutoIncremented;
	  boolean isPartOfPrimaryKey;
	  boolean isPartOfUniqueIndex;

}
