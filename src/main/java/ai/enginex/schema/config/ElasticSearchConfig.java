package ai.enginex.schema.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "ai.enginex.schema")
@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

	@Value("${app.elastic.hosts}")
	private String hosts;
//	@Value("${app.elastic.username}")
//	private String username;
//	@Value("${app.elastic.password}")
//	private String password;

	@Override
	public ClientConfiguration clientConfiguration() {

		final var hostsArr = hosts.split(",");

		return ClientConfiguration.builder() //
				.connectedTo(hostsArr) //
//				.usingSsl() //
				.withConnectTimeout(Duration.ofSeconds(30)) //
				.withSocketTimeout(Duration.ofSeconds(30)) //
//				.withBasicAuth(username, password) //
				.build();

	}

}