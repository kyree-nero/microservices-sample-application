package com.example.gateway;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.GenericFilterBean;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties="debug=true")
@ActiveProfiles("test")
//@EnableJdbcHttpSession
public abstract class AbstractIT {
	@Autowired protected WebApplicationContext context;
	@Autowired protected GenericFilterBean springSecurityFilterChain;
	//@LocalServerPort int randomServerPort;
	
	protected MockMvc mockMvc;
	
	public static final String TEST_AUTH_ROLE = "ROLE_SYSTEM";
	public static final String TEST_AUTH_USER = "discUser";
	public static final String TEST_AUTH_PASS = "discPassword";
	
	@Before
	public void setup() {
		
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
				.build();
	}
}
