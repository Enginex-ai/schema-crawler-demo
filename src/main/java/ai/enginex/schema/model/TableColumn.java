package ai.enginex.schema.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableColumn {

	@JsonProperty("@uuid")
	private String uuid;

	private String name;

	@JsonProperty("short-name")
	private String shortName;

	@JsonProperty("full-name")
	private String fullName;

	private Object attributes;

	@JsonProperty("auto-incremented")
	private boolean autoIncremented;

	@JsonProperty("column-data-type")
	private Object columnDataType;

	@JsonProperty("column-data-type-known")
	private boolean columnDataTypeKnown;

	@JsonProperty("decimal-digits")
	private int decimalDigits;

	@JsonProperty("default-value")
	private String defaultValue;

	private boolean generated;
	private boolean hidden;
	private boolean nullable;

	@JsonProperty("ordinal-position")
	private int ordinalPosition;

	@JsonProperty("parent-partial")
	private boolean parentPartial;

	@JsonProperty("part-of-foreign-key")
	private boolean partOfForeignKey;

	@JsonProperty("part-of-index")
	private boolean partOfIndex;

	@JsonProperty("part-of-primary-key")
	private boolean partOfPrimaryKey;

	@JsonProperty("part-of-unique-index")
	private boolean partOfUniqueIndex;

	private List<String> privileges;

	private String remarks;

	private Object schema;

	@JsonProperty("size")
	private int size;

	@JsonProperty("type")
	private String type;

	private String width;

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Attributes {
		@JsonProperty("CHAR_OCTET_LENGTH")
		private String charOctetLength;

		@JsonProperty("IS_NULLABLE")
		private String isNullable;

		@JsonProperty("NUM_PREC_RADIX")
		private int numPrecRadix;

		@JsonProperty("REMARKS")
		private String remarks;
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ColumnDataType {
		@JsonProperty("@uuid")
		private String uuid;

		private String name;

		@JsonProperty("full-name")
		private String fullName;

		@JsonProperty("attributes")
		private Object attributes;

		@JsonProperty("auto-incrementable")
		private boolean autoIncrementable;

		@JsonProperty("base-type")
		private String baseType;

		@JsonProperty("case-sensitive")
		private boolean caseSensitive;

		@JsonProperty("create-parameters")
		private String createParameters;

		@JsonProperty("database-specific-type-name")
		private String databaseSpecificTypeName;

		@JsonProperty("enum-values")
		private List<Object> enumValues;

		@JsonProperty("enumerated")
		private boolean enumerated;

		@JsonProperty("fixed-precision-scale")
		private boolean fixedPrecisionScale;

		@JsonProperty("java-sql-type")
		private Object javaSqlType;

		@JsonProperty("literal-prefix")
		private String literalPrefix;

		@JsonProperty("literal-suffix")
		private String literalSuffix;

		@JsonProperty("local-type-name")
		private String localTypeName;

		@JsonProperty("maximum-scale")
		private int maximumScale;

		@JsonProperty("minimum-scale")
		private int minimumScale;

		@JsonProperty("nullable")
		private boolean nullable;

		@JsonProperty("num-precision-radix")
		private int numPrecisionRadix;

		private int precision;
		private String remarks;
		private Object schema;
		private String searchable;
		private String type;

		@JsonProperty("type-mapped-class")
		private String typeMappedClass;

		private boolean unsigned;
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JavaSqlType {
		@JsonProperty("@uuid")
		private String uuid;

		private String name;

		@JsonProperty("default-mapped-class")
		private String defaultMappedClass;

		@JsonProperty("java-sql-type-group")
		private String javaSqlTypeGroup;

		private String vendor;
		@JsonProperty("vendor-type-number")
		private int vendorTypeNumber;
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Schema {
		@JsonProperty("@uuid")
		private String uuid;

		private String name;

		@JsonProperty("full-name")
		private String fullName;

		@JsonProperty("attributes")
		private Object attributes;

		@JsonProperty("catalog-name")
		private String catalogName;
		private String remarks;
	}
}
