package com.example.sampleservice;

import javax.ws.rs.core.MediaType;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class SampleServiceApplicationIT extends AbstractIT{
	
	
	@WithMockUser(value=TEST_AUTH_USER, authorities=TEST_AUTH_ROLE)
	@Test public void findContent() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sample", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.startsWith("Hi")))
			.andReturn();
			
	}

}
