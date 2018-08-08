package com.example.gateway;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GatewayApplicationLiveTest {
	 @Test
	    public void testAccess() {
			TestRestTemplate testRestTemplate = new TestRestTemplate();
			String testUrl = "http://localhost:8080";
			 
			ResponseEntity<String> response = testRestTemplate
			  .getForEntity(testUrl + "/book-service/books", String.class);
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNotNull(response.getBody());
	    }
	 
	 @Test
	    public void testDownstream403RedirectToLogin() {
			TestRestTemplate testRestTemplate = new TestRestTemplate();
			String testUrl = "http://localhost:8080";
			 
			ResponseEntity<String> response = testRestTemplate
			  .getForEntity(testUrl + "/book-service/books", String.class);
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNotNull(response.getBody());
			response = testRestTemplate
					  .getForEntity(testUrl + "/book-service/books/1", String.class);
					Assert.assertEquals(HttpStatus.FOUND, response.getStatusCode());
					Assert.assertEquals("http://localhost:8080/login", response.getHeaders()
					  .get("Location").get(0));
	    }
	   
	 @Test
	    public void testProtectedNonAdmin() {
			TestRestTemplate testRestTemplate = new TestRestTemplate();
			String testUrl = "http://localhost:8080";
			 
			
			MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
					form.add("username", "user");
					form.add("password", "password");
			ResponseEntity<String> response = testRestTemplate
					  .postForEntity(testUrl + "/login", form, String.class);
			String sessionCookie = response.getHeaders().get("Set-Cookie")
					  .get(0).split(";")[0];
					HttpHeaders headers = new HttpHeaders();
					headers.add("Cookie", sessionCookie);
					HttpEntity<String> httpEntity = new HttpEntity<>(headers);
					
			response = testRestTemplate.exchange(testUrl + "/book-service/books/1",
					  HttpMethod.GET, httpEntity, String.class);
					Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
					Assert.assertNotNull(response.getBody());
	    }
	 
	 
	 @Test
	    public void testProtectedAdminResourceWithoutAdmin() {
			TestRestTemplate testRestTemplate = new TestRestTemplate();
			String testUrl = "http://localhost:8080";
			 
			
			MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
					form.add("username", "user");
					form.add("password", "password");
			ResponseEntity<String> response = testRestTemplate
					  .postForEntity(testUrl + "/login", form, String.class);
			String sessionCookie = response.getHeaders().get("Set-Cookie")
					  .get(0).split(";")[0];
					HttpHeaders headers = new HttpHeaders();
					headers.add("Cookie", sessionCookie);
					HttpEntity<String> httpEntity = new HttpEntity<>(headers);
					
			response = testRestTemplate.exchange(testUrl + "/rating-service/ratings/all",
							  HttpMethod.GET, httpEntity, String.class);
							Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	    }
	 
	 
	 @Test
	    public void testProtectedAdminResource() {
			TestRestTemplate testRestTemplate = new TestRestTemplate();
			String testUrl = "http://localhost:8080";
			 
			
			MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
			form.clear();
			form.add("username", "admin");
			form.add("password", "admin");
			ResponseEntity<String> response = testRestTemplate
			  .postForEntity(testUrl + "/login", form, String.class);
			 
			String sessionCookie = response.getHeaders().get("Set-Cookie")
					  .get(0).split(";")[0];
					HttpHeaders headers = new HttpHeaders();
					headers.add("Cookie", sessionCookie);
					HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			 
			response = testRestTemplate.exchange(testUrl + "/rating-service/ratings/all",
			  HttpMethod.GET, httpEntity, String.class);
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNotNull(response.getBody());
	    }
	
	 
	 @Test
	    public void testDiscovery() {
			TestRestTemplate testRestTemplate = new TestRestTemplate();
			String testUrl = "http://localhost:8080";
			 
			
			MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
			form.clear();
			form.add("username", "admin");
			form.add("password", "admin");
			ResponseEntity<String> response = testRestTemplate
			  .postForEntity(testUrl + "/login", form, String.class);
			 
			String sessionCookie = response.getHeaders().get("Set-Cookie")
					  .get(0).split(";")[0];
					HttpHeaders headers = new HttpHeaders();
					headers.add("Cookie", sessionCookie);
					HttpEntity<String> httpEntity = new HttpEntity<>(headers);
			 
					response = testRestTemplate.exchange(testUrl + "/discovery",
							  HttpMethod.GET, httpEntity, String.class);
							Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	    }
}
