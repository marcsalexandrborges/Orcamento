package net.atos.api.orcamento.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.orcamento.domain.StatusEnum;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ItemPkTest {
	
	@Test
	@DisplayName("Testa equals e hash code")
	public void testEqualsHashCode() {
		
		OrcamentoEntity orc1 = new OrcamentoEntity();
		orc1.setDataEmissao(LocalDate.now());		
		orc1.setId(1L);
		orc1.setValor(10.0);
		orc1.setStatus(StatusEnum.VALIDO.getDescricao());
		
		ItemEntity item1 = new ItemEntity();
		item1.setCodigoItem(1);
		item1.setPrecoUnitario(10.0);
		item1.setDescricao("Coca-cola");
		item1.setQuantidade(4);
		item1.setValorItens(40.0);
		orc1.add(item1);
		
		ItemPK itemPk1 = new ItemPK();
		itemPk1.setItem(1);
		itemPk1.setOrcamento(orc1);
		
		OrcamentoEntity orc2 = new OrcamentoEntity();
		orc2.setDataEmissao(LocalDate.now());		
		orc2.setId(1L);
		orc2.setValor(10.0);
		orc2.setStatus(StatusEnum.VALIDO.getDescricao());
			
		ItemEntity item2 = new ItemEntity();
		item2.setCodigoItem(1);
		item2.setPrecoUnitario(10.0);
		item2.setDescricao("Coca-cola");
		item2.setQuantidade(4);
		item2.setValorItens(40.0);
		orc2.add(item2);
		
		ItemPK itemPk2 = new ItemPK();
		itemPk2.setItem(1);
		itemPk2.setOrcamento(orc2);

	    boolean equal = itemPk1.equals(itemPk2);
	    boolean hashcode = itemPk1.hashCode() == itemPk2.hashCode();
	    assertTrue(equal);
	    assertTrue(hashcode);
	}
}
