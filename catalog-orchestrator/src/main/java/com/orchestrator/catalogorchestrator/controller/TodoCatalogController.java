package com.orchestrator.catalogorchestrator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.orchestrator.catalogorchestrator.model.Task;

@Controller
public class TodoCatalogController {
	
	@Autowired(required = true)
	private RestTemplate restTemplate;
	
	@Value("${spring.services.userApi}")
	private String userValidatorUri;
	
	@Value("${spring.services.catalogApi.get}")
	private String tasksUri;
	
	@Value("${spring.services.catalogApi.save}")
	private String saveTaskUri;
	
	@GetMapping("/user")
	public ResponseEntity<String> valitedUser(@RequestParam String username, 
			@RequestParam String password){

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(userValidatorUri)
		        .queryParam("username", "{username}")
		        .queryParam("password", "{password}")
		        .encode()
		        .toUriString();

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);

		HttpEntity<String> response = restTemplate.exchange(
		        urlTemplate,
		        HttpMethod.GET,
		        entity,
		        String.class,
		        params
		);
		 
		return (ResponseEntity<String>) response;
	}
	
	@GetMapping("/tasks")
	public ResponseEntity<?> getTasks(@RequestParam String username){
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(tasksUri)
		        .queryParam("username", "{username}")
		        .encode()
		        .toUriString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		return restTemplate.exchange(
		        urlTemplate,
		        HttpMethod.GET,
		        entity,
		        List.class,
		        params
		);
	}
	
	@PutMapping("/save/{username}")
	public ResponseEntity<String> save(@PathVariable String username, @RequestBody Task task){
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<Object>(task, headers);

		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		return restTemplate.exchange(
				saveTaskUri,
		        HttpMethod.PUT,
		        entity,
		        String.class,
		        params
		);
	}

}
