package net.atos.api.orcamento.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.api.orcamento.domain.ItemVO;
import net.atos.api.orcamento.domain.OrcamentoVO;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class OrcamentoControllerIT {
	
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
	@DisplayName("Envio do orcamento sem os campos obrigatorios")
	public void testEnvioSemDados() throws Exception {
		OrcamentoVO orcamento = new OrcamentoVO();

		this.mockMvc
				.perform(MockMvcRequestBuilders.post(URI_ORCAMENTO)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(orcamento)))
				.andDo(print())
				.andExpect(status().isBadRequest());

	}
	
	@Test    
    @DisplayName("Cria Orcamento")
    public void testOrcamentoCriado() throws Exception {
    	OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setValor(10.0);
		orcamento.setDataEmissao(LocalDate.now());
		
		ItemVO item = new ItemVO();
		item.setCodigoItem(45);
		item.setPrecoUnitario(3.5);
		item.setDescricao("Coca");
		item.setQuantidade(4);
		item.setValorItens(40.0);
		orcamento.add(item);
    	
    	ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_ORCAMENTO)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orcamento))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
    	OrcamentoVO orcamentoCriado = mapper.readValue(resultCreated
    						.andReturn()
    						.getResponse()
    						.getContentAsString(),
    						OrcamentoVO.class);
    	
    	ResultActions resultConsulted = this.mockMvc.perform(
			MockMvcRequestBuilders.get(URI_ORCAMENTO.concat("/{id}"),
					orcamentoCriado.getId()))
					.andDo(print())
					.andExpect(status().isOk());
    	
    	OrcamentoVO orcamentoConsultado = mapper.readValue(resultConsulted
				.andReturn()
				.getResponse()
				.getContentAsString(),
				OrcamentoVO.class);
    	
    	assertEquals(1,orcamentoConsultado.getItens().size());
    	
    }
	
	@Test    
    @DisplayName("Cria Orcamento com dois itens")
    public void testOrcamentoCriadoComDoisItens() throws Exception {
    	OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setValor(10.0);
		orcamento.setDataEmissao(LocalDate.now());
		
		ItemVO item1 = new ItemVO();
		item1.setCodigoItem(45);
		item1.setPrecoUnitario(3.5);
		item1.setDescricao("Coca");
		item1.setQuantidade(4);
		item1.setValorItens(40.0);
		orcamento.add(item1);
		
		ItemVO item2 = new ItemVO();
		item2.setCodigoItem(1009);
		item2.setPrecoUnitario(4.0);
		item2.setDescricao("Cola");
		item2.setQuantidade(4);
		item2.setValorItens(40.0);
		orcamento.add(item2);
    	
    	ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_ORCAMENTO)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orcamento))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
    	OrcamentoVO orcamentoCriado = mapper.readValue(resultCreated
    						.andReturn()
    						.getResponse()
    						.getContentAsString(),
    						OrcamentoVO.class);
    	
    	ResultActions resultConsulted = this.mockMvc.perform(
			MockMvcRequestBuilders.get(URI_ORCAMENTO.concat("/{id}"),
					orcamentoCriado.getId()))
					.andDo(print())
					.andExpect(status().isOk());
    	
    	OrcamentoVO orcamentoConsultado = mapper.readValue(resultConsulted
				.andReturn()
				.getResponse()
				.getContentAsString(),
				OrcamentoVO.class);
    	
    	assertEquals(2,orcamentoConsultado.getItens().size());
    	
    }
}
