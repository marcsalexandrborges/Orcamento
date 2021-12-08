package net.atos.api.orcamento.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.api.orcamento.domain.OrcamentoVO;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")

class OrcamentoControllerIT {

	private static final String URI_ORCAMENTO = "/v1/orcamento";

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Autowired
	private EntityManager entityManager;

	@BeforeAll
	public void setup() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		assertNotNull(this.entityManager);

	}

	@Test
	@DisplayName("Envio do orcamento sem os campos obrigatórios")
	public void testEnvioSemDados() throws Exception {
		OrcamentoVO orcamento = new OrcamentoVO();

		this.mockMvc
				.perform(MockMvcRequestBuilders.post(URI_ORCAMENTO).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(orcamento)))
				.andDo(print()).andExpect(status().isBadRequest());

	}

}