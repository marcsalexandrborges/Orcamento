package net.atos.api.orcamento.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.orcamento.domain.ItemVO;
import net.atos.api.orcamento.domain.OrcamentoVO;
import net.atos.api.orcamento.repository.OrcamentoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class OrcamentoFactoryTest {

	@Test
	@DisplayName("Testa o factory de VO para entity")
	public void testCriarVoToEntity() {
		OrcamentoVO orcamento =  new OrcamentoVO();
		orcamento.setDataEmissao(LocalDate.now());	
		orcamento.setNumeroOrcamento(1);
		orcamento.setQuantidade(1);
		orcamento.setValor(BigDecimal.ONE);
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		orcamento.add(item);

		OrcamentoEntity orcEntity = 
				new OrcamentoFactory(orcamento).toEntity();
		
		assertNotNull(orcEntity);
		assertNotNull(orcEntity.getDataEmissao());
		assertEquals(orcamento.getDataEmissao(),orcEntity.getDataEmissao());
		
		assertNotNull(orcEntity.getNumeroOrcamento());
		assertEquals(orcamento.getNumeroOrcamento(),orcEntity.getNumeroOrcamento());
		
		assertNotNull(orcEntity.getQuantidade());
		assertEquals(orcamento.getQuantidade(),orcEntity.getQuantidade());
		
		assertNotNull(orcEntity.getValor());
		assertEquals(orcamento.getValor(),orcEntity.getValor());
		
		assertNotNull(orcEntity.getItens());
		assertEquals(orcamento.getItens().size(),orcEntity.getItens().size());
		assertEquals(orcamento.getItens().get(0).getCodigoItem(),orcEntity.getItens().get(0).getCodigoItem());
		assertEquals(orcamento.getItens().get(0).getPrecoUnitario(),orcEntity.getItens().get(0).getPrecoUnitario());
		
		OrcamentoVO voCriado = 
				new OrcamentoFactory(orcEntity).toVO();

		assertNotNull(voCriado);
		assertNotNull(voCriado.getDataEmissao());
		assertEquals(orcamento.getDataEmissao(),voCriado.getDataEmissao());
		
		assertNotNull(voCriado.getNumeroOrcamento());
		assertEquals(orcamento.getNumeroOrcamento(),voCriado.getNumeroOrcamento());
		
		assertNotNull(voCriado.getQuantidade());
		assertEquals(orcamento.getQuantidade(),voCriado.getQuantidade());
		
		assertNotNull(voCriado.getValor());
		assertEquals(orcamento.getValor(),voCriado.getValor());
		
		assertNotNull(voCriado.getItens());
		assertEquals(orcamento.getItens().size(),voCriado.getItens().size());
		assertEquals(orcamento.getItens().get(0).getCodigoItem(),voCriado.getItens().get(0).getCodigoItem());
		assertEquals(orcamento.getItens().get(0).getPrecoUnitario(),voCriado.getItens().get(0).getPrecoUnitario());
		
	}

}
