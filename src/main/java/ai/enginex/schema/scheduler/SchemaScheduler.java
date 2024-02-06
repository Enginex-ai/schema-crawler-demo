package ai.enginex.schema.scheduler;

import java.util.Calendar;

//import org.bson.BsonDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ai.enginex.schema.mongodb.document.Schema;
import ai.enginex.schema.mongodb.service.SchemaService;
import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@Component
@Slf4j
public class SchemaScheduler {

	@Autowired
	private SchemaService schemaService;

	@Scheduled(cron = "${constants.scheduler-cron}")
	public void findSchemaDifference() throws JsonMappingException, JsonProcessingException {
		log.info("Inside Scheduler! Time - {}", Calendar.getInstance());

		// TODO: Calculate difference
		Schema schema = Schema.builder() //
				.databaseName("schema") //
				.databaseType("PGS") //
//				.generatedTime(new BsonDateTime(System.currentTimeMillis())) //
				.build();
//		log.info("Inside Scheduler! schema - {}", schemaService.saveSchema(schema).getGeneratedTime());

	}
}
