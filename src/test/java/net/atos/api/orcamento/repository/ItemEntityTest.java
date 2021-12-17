package net.atos.api.orcamento.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}
