package ai.enginex.schema.elasticsearch.document;

import java.util.List;
import java.util.UUID;

import org.springframework.data.elasticsearch.annotations.Document;

import ai.enginex.schema.model.TableColumn;
import lombok.Builder;
import lombok.Getter;

@Document(indexName = "datasources")
@Getter
@Builder
public class DataSources {
	private UUID id;
	private String dataSourceName;
	private String dataSourceType;
	private List<Table> table;
	private TableColumn columns;
//	private List<View> view;
//	private List<Trigger> trigger;
//	private List<Procedure> procedure;
//	private List<Sequence> sequence;
//	private List<Function> function;
//	private List<MaterializedView> matView;
//	private List<Index> indexes;
//	private List<UDT> udt;
//	private List<Node> node;
//	private List<NodeRelationship> relationship;
}
