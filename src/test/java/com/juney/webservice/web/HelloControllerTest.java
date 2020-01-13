package com.juney.webservice.web;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.juney.webservice.config.auth.SecurityConfig;

@WebMvcTest(controllers = HelloController.class, 
							excludeFilters = {
									@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
							})
@RunWith(SpringRunner.class)
public class HelloControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@WithMockUser(roles = "USER")
	@Test
	public void HelloTest() throws Exception {
		String hello = "hello";
		
		mvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string(hello));
	}
	
	@WithMockUser(roles = "USER")
	@Test
	public void HelloResponseDtoTest() throws Exception {
		String name = "hello";
		int amount = 1000;
		
		mvc.perform(get("/hello/dto").param("name", name)
									.param("amount", String.valueOf(amount)))
										.andExpect(status().isOk())
										.andExpect(jsonPath("$.name", is(name)))
										.andExpect(jsonPath("$.amount", is(amount)));
											
	}
}
