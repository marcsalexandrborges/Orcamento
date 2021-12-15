package net.atos.api.orcamento.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

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
		orcamento.setValor(10.0);
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		item.setDescricao("Coca-cola");
		item.setQuantidade(4);
		item.setValorItens(40.0);
		orcamento.add(item);

		OrcamentoEntity orcEntity = 
				new OrcamentoFactory(orcamento).toEntity();
		assertNotNull(orcEntity.getValor());
		assertEquals(orcamento.getValor(),orcEntity.getValor());
		
		assertNotNull(orcEntity.getItens());
		assertEquals(orcamento.getItens().size(),orcEntity.getItens().size());
		assertEquals(orcamento.getItens().get(0).getCodigoItem(),orcEntity.getItens().get(0).getCodigoItem());
		assertEquals(orcamento.getItens().get(0).getPrecoUnitario(),orcEntity.getItens().get(0).getPrecoUnitario());
		assertEquals(orcamento.getItens().get(0).getDescricao(), orcEntity.getItens().get(0).getDescricao());
		assertEquals(orcamento.getItens().get(0).getQuantidade(), orcEntity.getItens().get(0).getQuantidade());
		assertEquals(orcamento.getItens().get(0).getValorItens(), orcEntity.getItens().get(0).getValorItens());
		
		OrcamentoVO voCriado = 
				new OrcamentoFactory(orcEntity).toVO();
		assertNotNull(voCriado.getValor());
		assertEquals(orcamento.getValor(),voCriado.getValor());
		
		assertNotNull(voCriado.getItens());
		assertEquals(orcamento.getItens().size(),voCriado.getItens().size());
		assertEquals(orcamento.getItens().get(0).getCodigoItem(),voCriado.getItens().get(0).getCodigoItem());
		assertEquals(orcamento.getItens().get(0).getPrecoUnitario(),voCriado.getItens().get(0).getPrecoUnitario());
		assertEquals(orcamento.getItens().get(0).getDescricao(), voCriado.getItens().get(0).getDescricao());
		assertEquals(orcamento.getItens().get(0).getQuantidade(), voCriado.getItens().get(0).getQuantidade());
		assertEquals(orcamento.getItens().get(0).getValorItens(), voCriado.getItens().get(0).getValorItens());
		
	}

}
