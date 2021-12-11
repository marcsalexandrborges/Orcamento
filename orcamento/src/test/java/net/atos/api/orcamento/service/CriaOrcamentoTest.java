package net.atos.api.orcamento.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.BadRequestException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.orcamento.domain.ItemVO;
import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.repository.IOrcamentoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CriaOrcamentoTest {

	private CriaOrcamento criaOrcamento;

	private Validator validator;
	
	private IOrcamentoRepository orcamentoRepository;

	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();

		this.validator = validatorFactor.getValidator();
	}

	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.orcamentoRepository = Mockito.mock(IOrcamentoRepository.class);
		
		criaOrcamento = new CriaOrcamento(validator, orcamentoRepository);	
	}
	
	@Test
	@DisplayName("Testa quando o orçamento é nulo")
	void test_quando_notaFiscal_Eh_Null_LancarExcecao() {

		assertNotNull(criaOrcamento);

		OrcamentoVO orcamento =  null;

		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
							criaOrcamento.criar(orcamento));
		
		assertNotNull(assertThrows);
		
	}

	@Test
	@DisplayName("Testa os campos obrigatorios do orçamento.")
	void testCamposObrigatorios() {

		assertNotNull(criaOrcamento);

		OrcamentoVO orcamento =  new OrcamentoVO();

		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
							criaOrcamento.criar(orcamento));
		
		assertEquals(4, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
		     .stream()
		     .map(ConstraintViolation::getMessage)
		     .collect(Collectors.toList());


		assertThat(mensagens, hasItems("Campo item não pode ser nulo",
				"Campo quantidade não pode ser nulo",
				"Campo valor do orçamento não pode ser nulo",
				"Campo data de emissão não pode ser nula"
				));
		
	}
	
	@Test	
	@DisplayName("Testa obrigatoriedade do campo dos itens.")
	public void testCamposObrigatoriosItens() {
		assertNotNull(criaOrcamento);

		OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setDataEmissao(LocalDate.now());
		orcamento.setId(1L);
		orcamento.setQuantidade(1);
		orcamento.setValor(BigDecimal.ONE);
		
		ItemVO item = new ItemVO();
		orcamento.add(item);
		
		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
			criaOrcamento.criar(orcamento));

		assertEquals(2, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
		     .stream()
		     .map(ConstraintViolation::getMessage)
		     .collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("Codigo do item não pode ser nulo",
				"Campo preço unitario não pode ser nulo"
				));
		
	}
	
	@Test	
	@DisplayName("Testa data de emissão orcamento não diferente do dia atual.")
	public void testDTDiferenteAtual() {
		assertNotNull(criaOrcamento);

		OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setDataEmissao(LocalDate.now().minusDays(1l));		
		orcamento.setId(1L);
		orcamento.setQuantidade(1);
		orcamento.setValor(BigDecimal.ONE);
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		orcamento.add(item);
		
		var assertThrows = assertThrows(BadRequestException.class, ()->
			criaOrcamento.criar(orcamento));

		
		assertEquals(assertThrows.getMessage(),"A data de emissão do orcamento deve ser atual.");
		
			
	}
	
	@Test	
	@DisplayName("Testa a criação do orcamento.")
	public void testCriaOrcamento() {
		assertNotNull(criaOrcamento);	
		
		OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setDataEmissao(LocalDate.now());		
		orcamento.setId(1L);
		orcamento.setQuantidade(1);
		orcamento.setValor(BigDecimal.ONE);
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		orcamento.add(item);
		
		criaOrcamento.criar(orcamento);
		
		then(orcamentoRepository).should(times(1)).save(any());
		
	}


}
