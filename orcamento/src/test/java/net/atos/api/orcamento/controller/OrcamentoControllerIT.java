package net.atos.api.orcamento.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.api.orcamento.domain.ItemVO;
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

	 @Test    
	    @DisplayName("Cria Orcamento")
	    public void testOrcamentoCriado() throws Exception {
	    	OrcamentoVO orcamento =  new OrcamentoVO();
			orcamento.setValor(BigDecimal.ONE);
			orcamento.setDataEmissao(LocalDate.now());
			orcamento.setNumeroOrcamento(1);
			orcamento.setQuantidade(3);
			
			
			
			ItemVO item = new ItemVO();
			item.setCodigoItem(45);
			item.setPrecoUnitario(3.5);
			orcamento.add(item);
			
			
	    	
	    	ResultActions resultCreated = this.mockMvc.perform(
	    			MockMvcRequestBuilders.post(URI_ORCAMENTO)
	                .accept(MediaType.APPLICATION_JSON)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(mapper.writeValueAsString(orcamento))
	    			).andDo(print())
	    			.andExpect(status().isCreated());
	    	
	    	OrcamentoVO createOrcamento = mapper.readValue(resultCreated
	    						.andReturn()
	    						.getResponse()
	    						.getContentAsString(),
	    						OrcamentoVO.class);
	    	
	    	ResultActions resultConsulted = this.mockMvc.perform(
	    			MockMvcRequestBuilders.get(URI_ORCAMENTO.concat("/{id}"),
	    					createOrcamento.getId()))
	    					.andDo(print())
	    					.andExpect(status().isOk());
	    	
	    	OrcamentoVO searchOrcamento = mapper.readValue(resultConsulted
					.andReturn()
					.getResponse()
					.getContentAsString(),
					OrcamentoVO.class);
	    	
	    	assertEquals(2,searchOrcamento.getItens().size());
	    	
	    }
	    
	 @Test    
	    @DisplayName("Consulta orcamento por item")
	    public void testBuscaOrcamentoItem() throws Exception {
	    	ResultActions resultConsulted = this.mockMvc.perform(
	    			MockMvcRequestBuilders.get(URI_ORCAMENTO.concat("/itens/{itens.codigoItem}"),
	    					"1"))
	    					.andDo(print())
	    					.andExpect(status().isOk());	
	    	
	    	
	    	List<OrcamentoVO> searchOrcamento = mapper.readValue(resultConsulted
					.andReturn()
					.getResponse()
					.getContentAsString(),
					new TypeReference<List<OrcamentoVO>>() { });
	    	
	    	System.out.println("(Consulta por item) Quantidade de orcamentos com o item 1 = "+searchOrcamento.size());
	    	assertNotNull(searchOrcamento);
	    }

}