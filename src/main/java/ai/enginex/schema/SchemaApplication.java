package ai.enginex.schema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class SchemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchemaApplication.class, args);
	}

}
