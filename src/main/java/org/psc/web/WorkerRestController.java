package org.psc.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.psc.web.domain.Recommendation;
import org.psc.web.json.RecommendationDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import lombok.extern.slf4j.Slf4j;
import tcall.Processor;

@Slf4j
@RestController
@RequestMapping("/workerapp")
public class WorkerRestController {

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Recommendation.class, new RecommendationDeserializer());
		objectMapper.registerModule(module);
	}

	@RequestMapping(path = "/worker", method = RequestMethod.GET)
	public String getWorker() throws IOException {
		Path path = Paths.get("src/main/resources/test.json");
		String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
		Recommendation rec = objectMapper.readValue(content, Recommendation.class);

		log.info(objectMapper.writeValueAsString(rec));
		
		return objectMapper.writeValueAsString(rec);
	}
	
	@RequestMapping(path =  "/tresource", method = RequestMethod.GET)
	public String getTResource() {
		Processor processor = new Processor();
		String result = processor.invoke2();
		log.info(result);
		return result;
	}
	
	@RequestMapping(path = "/lazyworker", method = RequestMethod.GET)
	public String getLazyWorker() throws IOException {
		return "OK";
	}

}
