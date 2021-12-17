package net.atos.api.orcamento.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class OrcamentoEntityTest {
	
	@Test
	@DisplayName("Testa o metodo que soma o valor do orcamento")
	public void testSomaValorOrcamento() {
		
		OrcamentoEntity orcEntity = new OrcamentoEntity();
		
		ItemEntity item = new ItemEntity();
		item.setCodigoItem(45);
		item.setPrecoUnitario(20.0);
		item.setDescricao("Coca-cola");
		item.setQuantidade(2);
		item.setValorItens(40.0);
		orcEntity.add(item);
		
		ItemEntity item2 = new ItemEntity();
		item2.setCodigoItem(45);
		item2.setPrecoUnitario(20.0);
		item2.setDescricao("Coca-cola");
		item2.setQuantidade(3);
		item2.setValorItens(60.0);
		orcEntity.add(item2);
		
		orcEntity.somaValorOrcamento();
	}
	
	@Test
	@DisplayName("Testa equals e hash code")
	public void testEqualsHashCode() {
		OrcamentoEntity orc1 = new OrcamentoEntity();
		orc1.setDataEmissao(LocalDate.now());		
		orc1.setId(1L);
		orc1.setValor(10.0);
		
		ItemEntity item1 = new ItemEntity();
		item1.setCodigoItem(1);
		item1.setPrecoUnitario(10.0);
		item1.setDescricao("Coca-cola");
		item1.setQuantidade(4);
		item1.setValorItens(40.0);
		orc1.add(item1);
		
		OrcamentoEntity orc2 = new OrcamentoEntity();
		orc2.setDataEmissao(LocalDate.now());		
		orc2.setId(1L);
		orc2.setValor(10.0);
			
		ItemEntity item2 = new ItemEntity();
		item2.setCodigoItem(1);
		item2.setPrecoUnitario(10.0);
		item2.setDescricao("Coca-cola");
		item2.setQuantidade(4);
		item2.setValorItens(40.0);
		orc2.add(item2);

	    boolean equal = orc1.equals(orc2);
	    boolean hashcode = orc1.hashCode() == orc2.hashCode();
	    assertTrue(equal);
	    assertTrue(hashcode);
	}

}
