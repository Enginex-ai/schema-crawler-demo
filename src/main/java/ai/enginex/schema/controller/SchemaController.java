package ai.enginex.schema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.enginex.schema.service.SchemaCrawlerService;

@Controller
@RequestMapping("/api/database-schema")
public class SchemaController {

	@Autowired
	private SchemaCrawlerService resultSet;

	@GetMapping("/tables")
	@ResponseBody
	public void getAllTables() {
		resultSet.getSchemaAsJSON();
	}

	@GetMapping("/")
	public String index() {
		return "index.html";
	}

}
