package com.example.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import javax.ws.rs.core.MediaType;

import org.eclipse.jgit.api.Git;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class ConfigApplicationIT extends AbstractIT{
	@BeforeClass public static void beforeClass() throws Exception{
		File gitLocation = new File("./target/config");
		if(gitLocation.exists()) {
			gitLocation.delete();
		}
		gitLocation.mkdir();
		
		FileChannel src = new FileInputStream(new File("./src/test/resources/test-service.yml")).getChannel();
		FileChannel dest = new FileOutputStream(new File("./target/config/test-service.yml")).getChannel();
		dest.transferFrom(src, 0, src.size());
		
		Git git = Git.init().setDirectory(new File("./target/config")).call();
		git.add().addFilepattern("test-service.yml").call();
		git.commit().setMessage("x").call();
	}
	
	
	@WithMockUser(value=TEST_AUTH_USER, authorities=TEST_AUTH_ROLE)
	@Test public void findAvailableConfigs() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/test-service/tc", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("test-service")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.propertySources", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.propertySources[0]", Matchers.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.propertySources[0].source['server.port']", Matchers.equalTo(9999)))
			.andReturn();
			
	}

}
