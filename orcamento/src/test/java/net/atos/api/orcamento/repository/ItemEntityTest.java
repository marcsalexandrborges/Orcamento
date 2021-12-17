package net.atos.api.orcamento.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ItemEntityTest {

	@Test
	@DisplayName("Testa o metodo que soma o valor do orcamento")
	public void testValorTotalItens() {
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setCodigoItem(1);
		itemEntity.setPrecoUnitario(20.0);
		itemEntity.setDescricao("Coca-cola");
		itemEntity.setQuantidade(4);
		
		itemEntity.getValorTotalItens();
		itemEntity.valorTotalItem();
	}
	
	@Test
	@DisplayName("Testa equals e hash code")
	public void testEqualsHashCode() {
		
		ItemEntity item1 = new ItemEntity();
		item1.setCodigoItem(1);
		item1.setPrecoUnitario(10.0);
		item1.setDescricao("Coca-cola");
		item1.setQuantidade(4);
		item1.setValorItens(40.0);
			
		ItemEntity item2 = new ItemEntity();
		item2.setCodigoItem(1);
		item2.setPrecoUnitario(10.0);
		item2.setDescricao("Coca-cola");
		item2.setQuantidade(4);
		item2.setValorItens(40.0);

	    boolean equal = item1.equals(item2);
	    boolean hashcode = item1.hashCode() == item2.hashCode();
	    assertTrue(equal);
	    assertTrue(hashcode);
	}


}
