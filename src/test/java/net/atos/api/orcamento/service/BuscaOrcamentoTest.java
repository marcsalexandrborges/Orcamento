package net.atos.api.orcamento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.repository.IOrcamentoRepository;
import net.atos.api.orcamento.repository.OrcamentoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BuscaOrcamentoTest {
	
	private BuscaOrcamento buscaOrcamentoService;
	
	private Validator validator;
	
	private IOrcamentoRepository orcamentoRepository;

	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = 
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();	
	}
	
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.orcamentoRepository = Mockito.mock(IOrcamentoRepository.class);
		buscaOrcamentoService = new BuscaOrcamento(validator, orcamentoRepository);	
	}	
	
	@Test	
	@DisplayName("Testa quando não encontra um orcamento por ID.")
	public void testBuscaOrcamentoIdError(){
		assertNotNull(this.buscaOrcamentoService);
		var assertThrows = assertThrows(NotFoundException.class, ()->
			this.buscaOrcamentoService.porId(2l));
		
		then(orcamentoRepository).should(times(1)).findById(anyLong());	
		assertEquals(assertThrows.getMessage(), "Orçamento não encontrado com o id: = 2");
		
	}
	
	
	@Test	
	@DisplayName("Testa quando encontra um orcamento por ID.")
	public void testBuscaOrcamentoId(){
		assertNotNull(this.buscaOrcamentoService);
		
		OrcamentoEntity orcamentoTreinado = new OrcamentoEntity();
		orcamentoTreinado.setId(2l);
		orcamentoTreinado.setDataEmissao(LocalDate.now());
		
		when(orcamentoRepository.findById(anyLong()))
				.thenReturn(Optional.of(orcamentoTreinado));
		
		OrcamentoVO orcamentoRetornado = this.buscaOrcamentoService.porId(2l);
		
		then(orcamentoRepository).should(times(1)).findById(anyLong());
		
		assertNotNull(orcamentoRetornado);
		assertEquals(2l, orcamentoRetornado.getId());
		
		
	}
	
	@Test	
	@DisplayName("Testa quando encontra um orcamento por ID com data expirada")
	public void testBuscaOrcamentoIdExpirado(){
		assertNotNull(this.buscaOrcamentoService);
		
		OrcamentoEntity orcamentoTreinado = new OrcamentoEntity();
		orcamentoTreinado.setId(2l);
		orcamentoTreinado.setDataEmissao(LocalDate.now().minusDays(8L));
		
		when(orcamentoRepository.findById(anyLong()))
				.thenReturn(Optional.of(orcamentoTreinado));
		
		OrcamentoVO orcamentoRetornado = this.buscaOrcamentoService.porId(2l);
		
		then(orcamentoRepository).should(times(1)).findById(anyLong());
		
		assertNotNull(orcamentoRetornado);
		assertEquals(2l, orcamentoRetornado.getId());
		
		
	}

}
