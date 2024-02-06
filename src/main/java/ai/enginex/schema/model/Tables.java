package ai.enginex.schema.model;

import java.util.List;

public class Tables {
	public Tables(String tableName, List<Columns> columns) {
		super();
		this.tableName = tableName;
		this.columns = columns;
	}

	private String tableName;
	private List<Columns> columns;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Columns> getColumns() {
		return columns;
	}

	public void setColumns(List<Columns> columns) {
		this.columns = columns;
	}
}
