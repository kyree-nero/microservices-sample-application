package com.example.discovery;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class DiscoveryApplicationIT extends AbstractIT{

	private TestRestTemplate testRestTemplateWithAuth = new TestRestTemplate(TEST_AUTH_USER, TEST_AUTH_PASS, HttpClientOption.ENABLE_COOKIES);
	
	@Test
	public void catalogLoads() {
		ResponseEntity<Map> entity = testRestTemplateWithAuth.getForEntity("http://localhost:" + randomServerPort+"/eureka/apps", Map.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
		
		
		
	}

}
