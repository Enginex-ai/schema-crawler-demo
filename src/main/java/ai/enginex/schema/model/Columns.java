package ai.enginex.schema.model;

public class Columns {
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Columns(String columnName, String columnType) {
		super();
		this.columnName = columnName;
		this.columnType = columnType;
	}

	private String columnName;
	private String columnType;
}
