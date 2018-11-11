package com.example.gateway;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//@NotThreadSafe
public class GatewayApplicationIT extends AbstractIT{

	TestRestTemplate testRestTemplateWithAuth = null;
	@Autowired private TestRestTemplate testRestTemplate;
	
	@Before public void before() throws Exception{
		HttpClientBuilder hcb = HttpClientBuilder.create();
		BasicCredentialsProvider cp = new BasicCredentialsProvider();
		cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("username", "password"));
		hcb.setDefaultCredentialsProvider(cp);
		HttpClient httpClient = hcb.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		
		testRestTemplateWithAuth = 
				new TestRestTemplate(
						new RestTemplateBuilder() {

							@Override
							public RestTemplate build() {
								return restTemplate;
							}
							
							
						}
				);
		
	}
	
	@Test public void testLogout() throws Exception  {
		String sessionId = login();
		logout(sessionId);
		
	}
	
	@Test public void testGatewayRoute() throws Exception{
		
		String sessionId = login();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.COOKIE, "SESSION=" + sessionId);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + 59999 + "/test-service/test", HttpMethod.GET, request, String.class);
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("success", response.getBody());
		
		logout(sessionId);
		
	}
	
	

	@Test public void testAccessResourceWithoutSession() throws Exception{
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + 59999 + "/index.html", request, String.class);
		
		System.out.println(response);
		
		Assert.assertEquals(HttpStatus.FOUND, response.getStatusCode());
		Assert.assertEquals("http://localhost:" + 59999 + "/login", response.getHeaders().get("location").get(0));
		
		
	}
	
	
	
	private String login() throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", "user");
		map.add("password", "password");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + 59999 + "/login", request, String.class);
		
		Assert.assertNotNull(response.getHeaders().get(HttpHeaders.SET_COOKIE));
		String cookie = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0);
		String sessionId = cookie.split(";")[0].trim().split("=")[1].trim();
		Assert.assertNotNull(sessionId);
		return sessionId;
	}
	
	
	private void logout(String sessionId) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.COOKIE, "SESSION=" + sessionId);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + 59999 + "/logout", request, String.class);
		
		System.out.println(response);
		
		Assert.assertEquals(HttpStatus.FOUND, response.getStatusCode());
		Assert.assertEquals("http://localhost:" + 59999 + "/login?logout", response.getHeaders().get("location").get(0));
		
		
		
	}
	

	
}
