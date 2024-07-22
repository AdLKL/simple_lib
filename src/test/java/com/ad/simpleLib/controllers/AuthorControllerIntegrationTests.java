package com.ad.simpleLib.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ad.simpleLib.TestDataUtil;
import com.ad.simpleLib.domain.dto.AuthorDto;
import com.ad.simpleLib.domain.entities.AuthorEntity;
import com.ad.simpleLib.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {
	
	private AuthorService authorService;

	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	@Autowired
	public AuthorControllerIntegrationTests(AuthorService authorService, MockMvc mockMvc, ObjectMapper objectMapper) {
		super();
		this.authorService = authorService;
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}

	
	@Test
	public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
		AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
		testAuthorA.setId(null);
		String authorJson = objectMapper.writeValueAsString(testAuthorA);
		mockMvc.perform(MockMvcRequestBuilders.post("/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(authorJson)
				).andExpect(
						MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
		AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
		testAuthorA.setId(null);
		String authorJson = objectMapper.writeValueAsString(testAuthorA);
		mockMvc.perform(MockMvcRequestBuilders.post("/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(authorJson)
				).andExpect(
						MockMvcResultMatchers.jsonPath("$.id").isNumber()
				).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Agatha Christie")
				).andExpect(MockMvcResultMatchers.jsonPath("$.age").value(80));
	}
	
	@Test
	public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors")
					.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
		AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
		authorService.save(testAuthorEntityA);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors")
					.contentType(MediaType.APPLICATION_JSON)
				).andExpect(
						MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
				).andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Agatha Christie")
				).andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(80));
	}
	
	@Test
	public void testThatGetAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
		AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
		authorService.save(testAuthorEntityA);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors/1")
					.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
    public void testThatGetAuthorReturnsAuthorWhenAuthorExist() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Agatha Christie")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }
	
	@Test
	public void testThatGetAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
		AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
		authorService.save(testAuthorEntityA);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors/999")
					.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testThatFullUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
		AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
		String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors/999")
					.contentType(MediaType.APPLICATION_JSON)
					.content(authorDtoJson)
				).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
		AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
		AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);
		
		AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
		String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors/" + savedAuthor.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(authorDtoJson)
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
		AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
		AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);
		
		AuthorEntity authorDto = TestDataUtil.createTestAuthorB();
		authorDto.setId(savedAuthor.getId());
		String authorDtoUpdateJson = objectMapper.writeValueAsString(authorDto);
		
		mockMvc.perform(
				MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(authorDtoUpdateJson)
				).andExpect(
		                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
				).andExpect(
				        MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
				).andExpect(
				        MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
				);
	}
	
	
}
