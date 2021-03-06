package net.atos.api.orcamento.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import net.atos.api.orcamento.domain.ItemVO;
import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.domain.StatusEnum;
import net.atos.api.orcamento.repository.IOrcamentoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CriaOrcamentoTest {

	private CriaOrcamento criaOrcamento;

	private Validator validator;
	
	private IOrcamentoRepository orcamentoRepository;
	
	private ApplicationEventPublisher eventPublisher;

	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactor.getValidator();
	}

	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.orcamentoRepository = Mockito.mock(IOrcamentoRepository.class);
		this.eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
		
		criaOrcamento = new CriaOrcamento(validator, orcamentoRepository, eventPublisher);	
	}
	
	@Test
	@DisplayName("Testa quando o or??amento ?? nulo")
	void testOrcamentoNull() {

		assertNotNull(criaOrcamento);

		OrcamentoVO orcamento =  null;

		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
							criaOrcamento.criar(orcamento));
		
		assertNotNull(assertThrows);
		
	}

	@Test
	@DisplayName("Testa os campos obrigatorios do or??amento.")
	void testCamposObrigatorios() {

		assertNotNull(criaOrcamento);

		OrcamentoVO orcamento =  new OrcamentoVO();

		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
							criaOrcamento.criar(orcamento));
		
		assertEquals(1, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
		     .stream()
		     .map(ConstraintViolation::getMessage)
		     .collect(Collectors.toList());


		assertThat(mensagens, hasItems("Campo item n??o pode ser nulo"));
		
	}
	
	@Test	
	@DisplayName("Testa obrigatoriedade do campo dos itens.")
	public void testCamposObrigatoriosItens() {
		assertNotNull(criaOrcamento);

		OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setDataEmissao(LocalDate.now());
		orcamento.setId(1L);
		orcamento.setValor(10.0);
		
		ItemVO item = new ItemVO();
		orcamento.add(item);
		
		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
			criaOrcamento.criar(orcamento));

		assertEquals(4, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
		     .stream()
		     .map(ConstraintViolation::getMessage)
		     .collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("Codigo do item n??o pode ser nulo",
				"Campo pre??o unitario n??o pode ser nulo",
				"Campo descricao n??o pode ser nulo",
				"Campo quantidade n??o pode ser nulo"
				));
		
	}
	
	@Test	
	@DisplayName("Testa a cria????o do orcamento.")
	public void testCriaOrcamento() {
		assertNotNull(criaOrcamento);	
		
		OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setDataEmissao(LocalDate.now());		
		orcamento.setId(1L);
		orcamento.setValor(10.0);
		orcamento.setStatus(StatusEnum.VALIDO.getDescricao());
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		item.setDescricao("Coca-cola");
		item.setQuantidade(4);
		item.setValorItens(40.0);
		orcamento.add(item);
		
		criaOrcamento.criar(orcamento);
		
		then(orcamentoRepository).should(times(1)).save(any());
		
	}
	
	@Test	
	@DisplayName("Testa a cria????o do orcamento.")
	public void testCriaOrcamentoExpirado() {
		assertNotNull(criaOrcamento);	
		
		OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setDataEmissao(LocalDate.now().minusDays(8L));		
		orcamento.setId(1L);
		orcamento.setValor(10.0);
		orcamento.setStatus(StatusEnum.EXPIRADO.getDescricao());
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		item.setDescricao("Coca-cola");
		item.setQuantidade(4);
		item.setValorItens(40.0);
		orcamento.add(item);
		
		criaOrcamento.criar(orcamento);
		
		then(orcamentoRepository).should(times(1)).save(any());
		
	}

}
