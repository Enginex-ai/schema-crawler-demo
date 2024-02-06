package ai.enginex.schema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class Constants {
	@Value("${constants.table-index}")
	private String tableIndex;

	@Value("${constants.column-key}")
	private String columnKey;
}
